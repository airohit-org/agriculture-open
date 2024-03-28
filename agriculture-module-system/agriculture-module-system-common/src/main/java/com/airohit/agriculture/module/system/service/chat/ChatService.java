package com.airohit.agriculture.module.system.service.chat;

import com.airohit.agriculture.module.system.entity.admin.chat.vo.ChatRecordVo;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/17 09:43
 */
public interface ChatService {

    /**
     * 创建对话
     *
     * @param chatRecordVo
     */
    String createChat(ChatRecordVo chatRecordVo);

    /**
     * 查找所有对话
     *
     * @param platform
     * @return
     */
    Map<String, List<ChatRecordVo>> getChatRecordList(String platform);

    /**
     * 删除对话
     */
    void delChatRecord(String platform, String topic);
}
