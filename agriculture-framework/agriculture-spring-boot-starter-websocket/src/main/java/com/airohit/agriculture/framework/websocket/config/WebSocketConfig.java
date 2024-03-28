package com.airohit.agriculture.framework.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 11:21
 */

@Configuration
public class WebSocketConfig {
    /**
     * 注入一个ServerEndpointExporter,该bean会自动舌侧使用 @ServerEndpoint注解声明websocket endpoind
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
