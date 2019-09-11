package com.haozi.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hao.yang
 * @date 2019/9/11
 */
@Data
@ConfigurationProperties("spring.user")
public class UserProperties {
    private String name;
}
