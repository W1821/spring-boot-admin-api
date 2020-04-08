package org.humki.baseadmin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
public class LocalDateUtil {

    public static final DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
    public static final DateTimeFormatter LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 解析yyyy-MM-dd格式时间
     */
    public static LocalDate parseDate(@NonNull String date) {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        try {
            return LocalDate.parse(date, LOCAL_DATE);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 解析HH:mm:ss格式时间
     */
    public static LocalTime parseTime(@NonNull String time) {
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return LocalTime.parse(time, LOCAL_TIME);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 解析yyyy-MM-dd HH:mm:ss格式时间
     */
    public static LocalDateTime parseDateTime(@NonNull String dateTime) {
        if (StringUtil.isEmpty(dateTime)) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTime, LOCAL_DATE_TIME);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 格式化yyyy-MM-dd格式时间
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        try {
            return LOCAL_DATE.format(date);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 格式化yyyy-MM-dd格式时间
     */
    public static String formatDate(LocalDate date, DateTimeFormatter dateTimeFormatter) {
        if (date == null || dateTimeFormatter == null) {
            return null;
        }
        try {
            return dateTimeFormatter.format(date);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 格式化HH:mm:ss格式时间
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return null;
        }
        try {
            return LOCAL_TIME.format(time);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 解析yyyy-MM-dd HH:mm:ss格式时间
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        try {
            return LOCAL_DATE_TIME.format(dateTime);
        } catch (Exception e) {
            log.error("时间格式解析错误", e);
        }
        return null;
    }

    /**
     * 判断当前时间是否在startTime和endTime之间
     * HH:mm:ss格式
     */
    public static boolean nowTimeBetween(String startTime, String endTime) {
        return timeBetween(LocalTime.now(), startTime, endTime);
    }

    /**
     * 判断now是否在startTime和endTime之间
     * HH:mm:ss格式
     */
    public static boolean timeBetween(LocalTime now, String startTime, String endTime) {
        if (now == null) {
            return false;
        }
        LocalTime start = parseTime(startTime);
        if (start == null) {
            return false;
        }
        LocalTime end = parseTime(endTime);
        if (end == null) {
            return false;
        }
        return now.isAfter(start) && now.isBefore(end);
    }


    public static long getMillisBetween(LocalDateTime startTime, LocalDateTime now) {
        if (startTime == null || now == null) {
            return 0L;
        }
        return ChronoUnit.MILLIS.between(startTime, now);
    }

    public static long getSecondsBetween(LocalDateTime startTime, LocalDateTime now) {
        if (startTime == null || now == null) {
            return 0L;
        }
        return ChronoUnit.SECONDS.between(startTime, now);
    }

    /**
     * 获取startTime到now的时间分钟数
     */
    public static long getMinutesBetween(String start, LocalTime now) {
        if (now == null) {
            return 0L;
        }
        LocalTime startTime = parseTime(start);
        if (startTime == null) {
            return 0L;
        }
        return ChronoUnit.MINUTES.between(startTime, now);
    }

    public static long getMinutesBetween(LocalDateTime startTime, LocalDateTime now) {
        if (startTime == null || now == null) {
            return 0L;
        }
        return ChronoUnit.MINUTES.between(startTime, now);
    }

    public static long getHoursBetween(LocalDateTime startTime, LocalDateTime now) {
        if (startTime == null || now == null) {
            return 0L;
        }
        return ChronoUnit.HOURS.between(startTime, now);
    }


}
