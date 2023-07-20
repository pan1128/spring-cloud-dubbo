package com.pan.common.utils;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 返回当前时间字符串 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentLocalDateTimeStr() {
        return formatLocalDateTime(LocalDateTime.now());
    }

    /**
     * 返回当前时间戳(秒)
     *
     * @return
     */
    public static Long currentLocalDateTimeSecond() {
        return getSecondsByLocalDateTime(LocalDateTime.now());
    }

    /**
     * 将LocalDateTime转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }

    /**
     * 将LocalDateTime转换为时间戳（秒）
     *
     * @param localDateTime
     * @return
     */
    public static Long getSecondsByLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }


    public static void main(String[] args) {
        System.out.println(currentLocalDateTimeStr());
        System.out.println(currentLocalDateTimeSecond());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.withHour(20);

        System.out.println(dateTime);
    }
}
