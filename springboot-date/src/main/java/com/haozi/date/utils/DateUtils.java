package com.haozi.date.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * UTC与本地时间转换
 *
 * @author hao.yang
 * @date 2019/11/7
 */
public class DateUtils {

    /**
     * Description: 本地时间转化为UTC时间
     *
     * @param localTime
     * @return
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * Description:UTC时间转化为本地时间
     * @param flag 夏令时标志位 0：夏令时， 1：正常
     * @param utcTime
     * @return
     */
    public static String utcTimeToLocalTime(String utcTime, int timeZone, int flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        Date locatlDate = null;
        try {
            utcDate = sdf.parse(utcTime);
            long timestamp = utcDate.getTime();
            long timeZoneValue = (long) timeZone * 3600000;
            long localTimestamp;
            if (flag == 0) {
                localTimestamp = timestamp + timeZoneValue + 3600000;
            } else {
                localTimestamp = timestamp + timeZoneValue;
            }
            locatlDate = new Date(localTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(locatlDate);
    }

}
