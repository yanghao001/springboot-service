package com.haozi.init.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hao.yang
 * @date 2019/7/12
 */
@Slf4j
@Configuration
public class RunnerConfig {

    @Bean
    public CommandLineRunner runner(){
        return new CommandLineRunner() {
            public void run(String... args){
                log.info("@Bean config start");
            }
        };
    }

}
