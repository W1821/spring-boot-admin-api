package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 *
 * @author Bill
 * @date 2018/9/20 0020
 */
@Slf4j
public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_CONNECTED = "yyyyMMdd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date stringToDate(String date, String pattern) {
        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(pattern)) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            try {
                return format.parse(date);
            } catch (ParseException e) {
                log.error("解析时间错误", e);
            }
        }
        return null;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        return format.format(date);
    }

    /**
     * yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    /**
     * HH:mm:ss
     */
    public static String formatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static Date parseDateTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("解析时间错误", e);
        }
        return null;
    }

    /**
     * yyyy-MM-dd
     */
    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("解析时间错误", e);
        }
        return null;
    }

    /**
     * HH:mm:ss
     */
    public static Date parseTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("解析时间错误", e);
        }
        return null;
    }

    /**
     * 日期加减分钟
     */
    public static Date addMinuteReturnDate(Date dateTime, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.MINUTE, n);
        return calendar.getTime();
    }

    /**
     * 日期加减分钟
     */
    public static Long addMinuteReturnLong(Date dateTime, int n) {
        if (dateTime == null) {
            return 0L;
        }
        return dateTime.getTime() + n * 60L * 1000;
    }

}
