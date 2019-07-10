package com.haozi.nettywebsocket.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author hao.yang
 * @date 2019/7/10
 */
@Data
@Component
public class ServerConfig {
    @Value("${netty.config.host}")
    private String host;

    @Value("${netty.config.port}")
    private int port;
}
