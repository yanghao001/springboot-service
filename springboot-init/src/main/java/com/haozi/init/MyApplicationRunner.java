package com.haozi.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 默认myApplicationRunner先执行
 * @author hao.yang
 * @date 2019/7/12
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("MyApplicationRunner start");
    }
}
