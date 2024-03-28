package com.airohit.agriculture.module.system.service.chat;

import com.airohit.agriculture.framework.redis.core.RedisService;
import com.airohit.agriculture.module.system.entity.admin.chat.vo.ChatRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/17 09:44
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    @Resource
    private RedisService redisService;

    @Override
    public String createChat(ChatRecordVo chatRecordVo) {
        List<ChatRecordVo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(chatRecordVo.getPrefix())) {
            list = redisService.getCacheMapValue("chat:" + chatRecordVo.getPlatform(), chatRecordVo.getPrefix() + chatRecordVo.getTopic());
        } else {
            chatRecordVo.setPrefix(String.valueOf(System.currentTimeMillis()));
        }
        list.add(chatRecordVo);
        redisService.setCacheMapValue("chat:" + chatRecordVo.getPlatform(), chatRecordVo.getPrefix() + chatRecordVo.getTopic(), list);
        return chatRecordVo.getPrefix();
    }

    @Override
    public Map<String, List<ChatRecordVo>> getChatRecordList(String platform) {
        Map<String, List<ChatRecordVo>> map = redisService.getCacheMap("chat:" + platform);
        Map<String, List<ChatRecordVo>> result = new LinkedHashMap<>();
        for (String s : map.keySet()) {
            List<ChatRecordVo> chatRecordVos = map.get(s).stream().sorted(Comparator.comparing(ChatRecordVo::getCreateTime)).collect(Collectors.toList());
            result.put(s, chatRecordVos);
        }
        return result;
    }

    @Override
    public void delChatRecord(String platform, String topic) {
        redisService.delCacheMap("chat:" + platform, topic);
    }
}
