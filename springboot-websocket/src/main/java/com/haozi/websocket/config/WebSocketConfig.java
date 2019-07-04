package com.haozi.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author hao.yang
 * @date 2019/7/4
 */
@Configuration
public class WebSocketConfig {

    /**
     *  服务器节点注入
     * */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
