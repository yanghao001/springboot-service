package com.haozi.quartz.quartz.config;

import com.haozi.quartz.quartz.job.CronScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Cron 表达式Job配置
 *
 */
@Configuration
public class CronSchedulerJobConfig {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    // 启动定时任务
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        setCronScheduleJob(scheduler);
    }

    // 配置定时任务
    private void setCronScheduleJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(CronScheduleJob.class) .withIdentity("cronJob", "cronGroup").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "cronGroup") .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
}
