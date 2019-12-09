package com.haozi.date;

import com.haozi.date.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class DateApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * @description: utc和本地时间转换测试
     *
     * */
    @Test
    public void transfer() {
        String utcTime = "2019-11-15 10:29:45.123";
        String utcTimePatten = "yyyy-MM-dd HH:mm:ss.SSSXXX";
        String localTimePatten = "yyyy-MM-dd HH:mm:ss.SSS";
        String localTime = "2019-11-07 10:52:43";
//        System.out.println(DateUtils.localToUTC(localTime));
        System.out.println(DateUtils.utcTimeToLocalTime(utcTime, -8, 1));
    }

    @Test
    public void testDate() {
        String timeZone = "-1";
        long value = Long.parseLong(timeZone) * 3600;
        long timestamp = System.currentTimeMillis();
        long time = timestamp + value;
        System.out.println(timestamp + "----" + time);
    }

    @Test
    public void test2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String time = "2019-09-12 13:10:20";
        Date date = sdf.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println("================" + hour);
    }


}
