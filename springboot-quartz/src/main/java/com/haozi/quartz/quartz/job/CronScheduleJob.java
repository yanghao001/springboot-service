package com.haozi.quartz.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Cron 表达式定时任务
 */
public class CronScheduleJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(CronScheduleJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm:ss a");
        LOG.info("cron schedule job running, current time: {}", now.format(format));
    }
}
