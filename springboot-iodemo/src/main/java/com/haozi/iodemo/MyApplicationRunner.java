package com.haozi.iodemo;

import com.haozi.iodemo.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Server());
        log.info("MyApplicationRunner start");
    }
}
