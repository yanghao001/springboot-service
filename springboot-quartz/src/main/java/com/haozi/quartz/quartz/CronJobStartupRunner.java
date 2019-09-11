package com.haozi.quartz.quartz;

import com.haozi.quartz.quartz.config.CronSchedulerJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动时触发定时任务
 *
 * @auther PhantoGui
 * @date 2018/6/14 16:13
 */
@Component
public class CronJobStartupRunner implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CronJobStartupRunner.class);

    @Autowired
    private CronSchedulerJobConfig config;

    @Override
    public void run(String... args) throws Exception {
        config.scheduleJobs();
        LOG.info("Cron定时任务触发...");
    }
}
