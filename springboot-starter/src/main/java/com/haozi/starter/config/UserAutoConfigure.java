package com.haozi.starter.config;

import com.haozi.starter.client.UserClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springboot默认跟启动类平级的包
 * @author hao.yang
 * @date 2019/9/11
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "spring.user", value = "enabled", havingValue = "true")
    public UserClient userClient(UserProperties userProperties) {
        return new UserClient(userProperties);
    }

}
