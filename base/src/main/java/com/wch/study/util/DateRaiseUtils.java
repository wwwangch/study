package com.wch.study.util;

import java.util.Date;

/**
 * @author ch w
 * @version 1.0
 * @since 2022/9/13 11:12
 */
public class DateRaiseUtils {

    private DateRaiseUtils() {
    }

    /**
     * 获取当前日期里的年份
     *
     * @param date 日期
     * @return int 年份
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getYear(Date date) {
        assert date != null;
        String x = DateSafeUtils.format(date, "yyyy");
        return Integer.parseInt(x);
    }

    /**
     * 获取当前日期里的月份
     *
     * @param date 日期
     * @return int 月份
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getMonth(Date date) {
        assert date != null;
        String month = DateSafeUtils.format(date, "MM");
        return Integer.parseInt(month);
    }

    /**
     * 获取当前日期里的天
     *
     * @param date 日期
     * @return int 天
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getDay(Date date) {
        assert date != null;
        String x = DateSafeUtils.format(date, "dd");
        return Integer.parseInt(x);
    }

    /**
     * 获取当前日期里的小时
     *
     * @param date 日期
     * @return int 小时
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getHour(Date date) {
        assert date != null;
        String x = DateSafeUtils.format(date, "HH");
        return Integer.parseInt(x);
    }

    /**
     * 获取当前日期里的分钟
     *
     * @param date 日期
     * @return int 分钟
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getMinute(Date date) {
        assert date != null;
        String x = DateSafeUtils.format(date, "HH");
        return Integer.parseInt(x);
    }

    /**
     * 获取当前日期里的秒数
     *
     * @param date 日期
     * @return int 秒数
     * @see DateSafeUtils
     * @since jdk1.8
     */
    public static int getSecond(Date date) {
        assert date != null;
        String x = DateSafeUtils.format(date, "ss");
        return Integer.parseInt(x);
    }

    /**
     * 计算从现在开始偏移毫秒数后的时间,支持负数
     *
     * @param offset 偏移的时候毫秒数
     * @return java.util.Date
     * @see #afterOffsetDate(Date, long)
     * @since jdk1.8
     */
    public static Date afterOffsetDate(long offset) {
        return afterOffsetDate(new Date(), offset);
    }

    /**
     * 计算从某个时间偏移毫秒数后的时间,支持负数
     *
     * @param offset 偏移的时候毫秒数
     * @param date   日期时间
     * @return java.util.Date
     * @since jdk1.8
     */
    public static Date afterOffsetDate(Date date, long offset) {
        assert date != null;
        long time = date.getTime();
        time = time + offset;
        return new Date(time);
    }


    /**
     * 时间偏移一定毫秒数.
     * <p></p>
     *
     * @param time
     * @param offset
     * @return
     */
    @Deprecated
    public static Date timeOffset(Date time, long offset) {
        time = new Date(time.getTime() + offset);
        return time;
    }
}
