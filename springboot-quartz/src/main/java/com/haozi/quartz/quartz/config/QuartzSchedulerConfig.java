package com.haozi.quartz.quartz.config;

import com.haozi.quartz.quartz.job.SimpleScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Job配置
 *
 */
@Configuration
public class QuartzSchedulerConfig {

    @Bean
    public JobDetail SimpleScheduleJobDetail() {
        return JobBuilder.newJob(SimpleScheduleJob.class).withIdentity("simpleScheduleJob")
                .usingJobData("param", "springboot").storeDurably().build();
    }

    @Bean
    public Trigger SimpleScheduleJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10).repeatForever();

        return TriggerBuilder.newTrigger().forJob(SimpleScheduleJobDetail())
                .withIdentity("simpleScheduleJobTrigger").withSchedule(scheduleBuilder).build();
    }
}
