package com.haozi.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author hao.yang
 * @date 2019/7/12
 */
@Slf4j
@Component
public class EventHandler {
    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        log.info("The application is ready to service requests..");
    }
}
