package com.airohit.agriculture.module.system.service.sensitiveword;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.module.system.convert.sensitiveword.SensitiveWordConvert;
import com.airohit.agriculture.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import com.airohit.agriculture.module.system.dal.mysql.sensitiveword.SensitiveWordMapper;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo.SensitiveWordUpdateReqVO;
import com.airohit.agriculture.module.system.util.collection.SimpleTrie;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.SENSITIVE_WORD_EXISTS;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.SENSITIVE_WORD_NOT_EXISTS;

/**
 * 敏感词 Service 实现类
 *
 * @author 永不言败
 */
@Service
@Slf4j
@Validated
public class SensitiveWordServiceImpl implements SensitiveWordService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 敏感词标签缓存
     * key：敏感词编号 {@link SensitiveWordDO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile ImmutableSet<String> sensitiveWordTagsCache = ImmutableSet.<String>builder().build();

    /**
     * 缓存敏感词的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    @Getter
    private volatile LocalDateTime maxUpdateTime;

    @Resource
    private SensitiveWordMapper sensitiveWordMapper;


    /**
     * 默认的敏感词的字典树，包含所有敏感词
     */
    @Getter
    private volatile SimpleTrie defaultSensitiveWordTrie = new SimpleTrie(ImmutableSet.<String>builder().build());
    /**
     * 标签与敏感词的字段数的映射
     */
    @Getter
    private volatile ImmutableMap<String, SimpleTrie> tagSensitiveWordTries = ImmutableMap.<String, SimpleTrie>builder().build();

    /**
     * 初始化缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        initLocalCacheIfUpdate(null);
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCacheIfUpdate(this.maxUpdateTime);
    }

    /**
     * 刷新本地缓存
     *
     * @param maxUpdateTime 最大更新时间
     *                      1. 如果 maxUpdateTime 为 null，则“强制”刷新缓存
     *                      2. 如果 maxUpdateTime 不为 null，判断自 maxUpdateTime 是否有数据发生变化，有的情况下才刷新缓存
     */
    private void initLocalCacheIfUpdate(LocalDateTime maxUpdateTime) {
        // 第一步：基于 maxUpdateTime 判断缓存是否刷新。
        // 如果没有增量的数据变化，则不进行本地缓存的刷新
        if (maxUpdateTime != null
                && sensitiveWordMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
            log.info("[initLocalCacheIfUpdate][数据未发生变化({})，本地缓存不刷新]", maxUpdateTime);
            return;
        }
        List<SensitiveWordDO> sensitiveWords = sensitiveWordMapper.selectList();
        log.info("[initLocalCacheIfUpdate][缓存敏感词，数量为:{}]", sensitiveWords.size());

        // 第二步：构建缓存。
        // 写入 sensitiveWordTagsCache 缓存
        ImmutableSet.Builder<String> tags = ImmutableSet.builder();
        sensitiveWords.forEach(word -> tags.addAll(word.getTags()));
        sensitiveWordTagsCache = tags.build();
        // 写入 defaultSensitiveWordTrie、tagSensitiveWordTries 缓存
        initSensitiveWordTrie(sensitiveWords);

        // 第三步：设置最新的 maxUpdateTime，用于下次的增量判断。
        this.maxUpdateTime = CollectionUtils.getMaxValue(sensitiveWords, SensitiveWordDO::getUpdateTime);
    }

    private void initSensitiveWordTrie(List<SensitiveWordDO> wordDOs) {
        // 过滤禁用的敏感词
        wordDOs = CollectionUtils.filterList(wordDOs, word -> word.getStatus().equals(CommonStatusEnum.ENABLE.getStatus()));

        // 初始化默认的 defaultSensitiveWordTrie
        this.defaultSensitiveWordTrie = new SimpleTrie(CollectionUtils.convertList(wordDOs, SensitiveWordDO::getName));

        // 初始化 tagSensitiveWordTries
        Multimap<String, String> tagWords = HashMultimap.create();
        for (SensitiveWordDO word : wordDOs) {
            if (CollUtil.isEmpty(word.getTags())) {
                continue;
            }
            word.getTags().forEach(tag -> tagWords.put(tag, word.getName()));
        }
        // 添加到 tagSensitiveWordTries 中
        ImmutableMap.Builder<String, SimpleTrie> tagSensitiveWordTries = ImmutableMap.builder();
        tagWords.asMap().forEach((tag, words) -> tagSensitiveWordTries.put(tag, new SimpleTrie(words)));
        this.tagSensitiveWordTries = tagSensitiveWordTries.build();
    }

    @Override
    public Long createSensitiveWord(SensitiveWordCreateReqVO createReqVO) {
        // 校验唯一性
        checkSensitiveWordNameUnique(null, createReqVO.getName());
        // 插入
        SensitiveWordDO sensitiveWord = SensitiveWordConvert.INSTANCE.convert(createReqVO);
        sensitiveWordMapper.insert(sensitiveWord);
        // 发送消息，刷新缓存
        initLocalCache();
        return sensitiveWord.getId();
    }

    @Override
    public void updateSensitiveWord(SensitiveWordUpdateReqVO updateReqVO) {
        // 校验唯一性
        checkSensitiveWordExists(updateReqVO.getId());
        checkSensitiveWordNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 更新
        SensitiveWordDO updateObj = SensitiveWordConvert.INSTANCE.convert(updateReqVO);
        sensitiveWordMapper.updateById(updateObj);
        // 发送消息，刷新缓存
        initLocalCache();
    }

    @Override
    public void deleteSensitiveWord(Long id) {
        // 校验存在
        checkSensitiveWordExists(id);
        // 删除
        sensitiveWordMapper.deleteById(id);
        // 发送消息，刷新缓存
        initLocalCache();
    }

    private void checkSensitiveWordNameUnique(Long id, String name) {
        SensitiveWordDO word = sensitiveWordMapper.selectByName(name);
        if (word == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的敏感词
        if (id == null) {
            throw exception(SENSITIVE_WORD_EXISTS);
        }
        if (!word.getId().equals(id)) {
            throw exception(SENSITIVE_WORD_EXISTS);
        }
    }

    private void checkSensitiveWordExists(Long id) {
        if (sensitiveWordMapper.selectById(id) == null) {
            throw exception(SENSITIVE_WORD_NOT_EXISTS);
        }
    }

    @Override
    public SensitiveWordDO getSensitiveWord(Long id) {
        return sensitiveWordMapper.selectById(id);
    }

    @Override
    public List<SensitiveWordDO> getSensitiveWordList() {
        return sensitiveWordMapper.selectList();
    }

    @Override
    public PageResult<SensitiveWordDO> getSensitiveWordPage(SensitiveWordPageReqVO pageReqVO) {
        return sensitiveWordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SensitiveWordDO> getSensitiveWordList(SensitiveWordExportReqVO exportReqVO) {
        return sensitiveWordMapper.selectList(exportReqVO);
    }

    @Override
    public Set<String> getSensitiveWordTags() {
        return sensitiveWordTagsCache;
    }

    @Override
    public List<String> validateText(String text, List<String> tags) {
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.validate(text);
        }
        // 有标签的情况
        Set<String> result = new HashSet<>();
        tags.forEach(tag -> {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                return;
            }
            result.addAll(trie.validate(text));
        });
        return new ArrayList<>(result);
    }

    @Override
    public boolean isTextValid(String text, List<String> tags) {
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.isValid(text);
        }
        // 有标签的情况
        for (String tag : tags) {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                continue;
            }
            if (!trie.isValid(text)) {
                return false;
            }
        }
        return true;
    }

}
