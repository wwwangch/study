package com.wch.study.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ch w
 * @version 1.0
 * @since 2022/9/13 11:11
 */
public class DateSafeUtils {
    private DateSafeUtils(){}
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 锁对象
     */
    private static final Object LOCK_OBJ = new Object();
    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap<>();
    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern 格式化串
     * @return SimpleDateFormat SimpleDateFormat
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = SDF_MAP.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = SDF_MAP.get(pattern);
                if (tl == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    SDF_MAP.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }
    /**
     * 使用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     * 如果新的线程中没有SimpleDateFormat，才会new一个
     * @param date {@link Date} 时间
     * @param pattern 格式化串
     * @return String 时间字符串格式
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }
    /**
     * 使用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     * 如果新的线程中没有SimpleDateFormat，才会new一个
     * @param dateStr 字符串
     * @param pattern 时间字符串格式
     * @throws ParseException 时间转换错误
     * @return Date {@link Date} 时间
     */
    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }
}

