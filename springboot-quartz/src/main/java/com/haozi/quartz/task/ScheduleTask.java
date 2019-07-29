package com.haozi.quartz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 内置定时任务
 * @author hao.yang
 * @date 2019/7/26
 */
@Component
public class ScheduleTask {

    // @Scheduled(fixedRate = 30000)：上一次开始执行时间点之后 30 秒再执行。
    // @Scheduled(fixedDelay = 30000)：上一次执行完毕时间点之后 30 秒再执行。
    // @Scheduled(initialDelay = 10000, fixedRate = 60000)：第一次延迟 10 秒后执行，之后按 fixedRate 的规则每 60 秒执行一次。
    @Scheduled(fixedDelay = 10000, fixedRate = 60000)
    public void timer1() {
        System.out.println("内置定时任务执行");
    }

    // cron 一共有 7 位，最后一位是年，Spring Boot 定时方案中只需要设置 6 位即可
    // - 第一位，表示秒，取值 0-59；
    // - 第二位，表示分，取值 0-59；
    // - 第三位，表示小时，取值 0-23；
    // - 第四位，日期天/日，取值 1-31；
    // - 第五位，日期月份，取值 1-12；
    // - 第六位，星期，取值 1-7，星期一、星期二…；
    // - 第七位，年份，可以留空，取值 1970-2099。
    @Scheduled(cron = "*/30 * * * * * ")
    public void timer2() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm:ss a");
        System.out.println("内置定时任务执行，当前时间： "+ now.format(format));
    }
}
