package com.haozi.mongodb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author hao.yang
 * @date 2019/8/2
 */
public class DateUtil {

    /**
     *  date 时间转换标准时间（2019-08-02T10:22:22.931Z）
     * */
    public static Date getISODate(Date date) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        System.out.println(nowAsISO);
        date = df.parse(nowAsISO);
        return date;
    }

    /**
     * mongo 日期查询isodate
     * <p>
     * <p>
     * 如果相差8小时，加上这句代码
     * format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
     *
     * @param dateStr
     * @return
     */
    public static Date dateToISODate(String dateStr) {
        Date parse = null;

        try {
            // 解析字符串时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            parse = format.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}

