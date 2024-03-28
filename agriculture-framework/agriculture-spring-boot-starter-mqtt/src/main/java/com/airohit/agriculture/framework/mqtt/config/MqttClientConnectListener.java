package com.airohit.agriculture.framework.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.client.MqttClientCreator;
import net.dreamlu.iot.mqtt.spring.client.event.MqttConnectedEvent;
import net.dreamlu.iot.mqtt.spring.client.event.MqttDisconnectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/18 09:23
 */

/**
 * 客户端上下线监听
 * 文档链接:<a href="https://gitee.com/596392912/mica-mqtt/blob/master/starter/mica-mqtt-client-spring-boot-starter/README.md">文档链接</a>
 */
@Service
@Slf4j
public class MqttClientConnectListener {

    @Autowired
    private MqttClientCreator mqttClientCreator;

    @EventListener
    public void onConnected(MqttConnectedEvent event) {
        //mqtt链接事件
        log.info("MqttConnectedEvent:{}", event);
    }

    @EventListener
    public void onDisconnect(MqttDisconnectEvent event) {
        // 离线时更新重连时的密码，适用于类似阿里云 mqtt clientId 连接带时间戳的方式
        log.info("MqttDisconnectEvent:{}", event);
        // 在断线时更新 clientId、username、password
//        mqttClientCreator.clientId("newClient" + System.currentTimeMillis())
//                .username("newUserName")
//                .password("newPassword");
    }

}