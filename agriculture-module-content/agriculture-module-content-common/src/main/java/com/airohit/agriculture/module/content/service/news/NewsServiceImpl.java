package com.airohit.agriculture.module.content.service.news;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.convert.news.NewsConvert;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.dal.mysql.news.NewsMapper;
import com.airohit.agriculture.module.content.vo.news.NewsCreateReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsExportReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsPageReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.content.enums.ErrorCodeConstants.NEWS_NOT_EXISTS;

/**
 * 新闻信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public Integer createNews(NewsCreateReqVO createReqVO) {
        // 插入
        NewsDO news = NewsConvert.INSTANCE.convert(createReqVO);
        newsMapper.insert(news);
        // 返回
        return news.getId();
    }

    @Override
    public void updateNews(NewsUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateNewsExists(updateReqVO.getId());
        // 更新
        NewsDO updateObj = NewsConvert.INSTANCE.convert(updateReqVO);
        newsMapper.updateById(updateObj);
    }

    @Override
    public void deleteNews(Integer id) {
        // 校验存在
        this.validateNewsExists(id);
        // 删除
        newsMapper.deleteById(id);
    }

    private void validateNewsExists(Integer id) {
        if (newsMapper.selectById(id) == null) {
            throw exception(NEWS_NOT_EXISTS);
        }
    }

    @Override
    public NewsDO getNews(Integer id) {
        return newsMapper.selectById(id);
    }

    @Override
    public List<NewsDO> getNewsList(Collection<Integer> ids) {
        return newsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<NewsDO> getNewsPage(NewsPageReqVO pageReqVO) {
        return newsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<NewsDO> getNewsList(NewsExportReqVO exportReqVO) {
        return newsMapper.selectList(exportReqVO);
    }

    @Override
    public PageResult<NewsDO> getNewsAppPage(NewsPageReqVO pageVO) {
        return newsMapper.getNewsAppPage(pageVO);
    }

}
