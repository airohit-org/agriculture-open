package com.airohit.agriculture.module.statistics.websocket;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 13:20
 */

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.redis.core.RedisService;
import com.airohit.agriculture.module.statistics.vo.websocket.Message;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.airohit.agriculture.module.statistics.enums.ApiConstants.WEBSOCKET_CONNECT;
import static com.airohit.agriculture.module.statistics.enums.ApiConstants.WEBSOCKET_CONNECT_NUM;


@ServerEndpoint("/ws/{token}")
@Component
@Slf4j
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。

    @Resource
    private RedisService redisService;

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                log.info("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    //给指定用户发送信息
    public void sendInfo(String userName, String message) {
        Session session = redisService.getCacheMapValue(WEBSOCKET_CONNECT, userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 群发消息
    public void broadcast(String message) {
        Map<String, Session> cacheMap = redisService.getCacheMap(WEBSOCKET_CONNECT);
        for (Session session : cacheMap.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "token") String userName) {
        if (Objects.isNull(redisService.getCacheMapValue(WEBSOCKET_CONNECT, userName))) {
            redisService.setCacheMapValue(WEBSOCKET_CONNECT, userName, session);
            addOnlineCount();
            log.info(userName + "加入webSocket！当前人数为" + getOnlineNumber());
        } else {
            log.info(userName + "已加入webSocket！当前人数为" + getOnlineNumber());
        }
        // 返回上线消息
        sendInfo(userName, JSON.toJSONString(CommonResult.success(true), true));

    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "token") String userName) {
        redisService.delCacheMap(WEBSOCKET_CONNECT, userName);
        subOnlineCount();
        log.info(userName + "断开webSocket连接！当前人数为" + getOnlineNumber());
        // 下线

    }

    //收到客户端信息后，根据接收人的username把消息推下去或者群发
    // to=-1群发消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("server get" + message);
        Message msg = JSON.parseObject(message, Message.class);
        msg.setDate(new Date());
        if (msg.getTo().equals("-1")) {
            broadcast(JSON.toJSONString(msg, true));
        } else {
            sendInfo(msg.getTo(), JSON.toJSONString(msg, true));
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("发生错误");
        throwable.printStackTrace();
    }

    public void addOnlineCount() {
        if (!redisService.hasKey(WEBSOCKET_CONNECT_NUM)) {
            redisService.increment(WEBSOCKET_CONNECT_NUM);
        } else {
            redisService.incrementLong(WEBSOCKET_CONNECT_NUM, 1L);
        }

    }

    public void subOnlineCount() {
        redisService.incrementLong(WEBSOCKET_CONNECT_NUM, -1L);

    }

    public Long getOnlineNumber() {
        return redisService.getCacheObject(WEBSOCKET_CONNECT_NUM);
    }

}

