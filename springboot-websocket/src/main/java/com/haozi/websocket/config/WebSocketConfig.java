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
     *  （1）端节点注入 （2）optional 编程端点
     * */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
