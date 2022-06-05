package com.dev.core.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {
    private static final int MILI_SECOND = 1000;

    /**
     * private TimeUtils constructor
     */
    private TimeUtil() {
    }

    /**
     * Get elapsed time in second
     *
     * @param startTime
     *          the start time (in milisecond)
     * @return the elapsed time in second
     */
    public static double getElapsedSecond(long startTime) {
        return (double) (System.currentTimeMillis() - startTime) / MILI_SECOND;
    }

    /**
     * Get the string represented the elapsed time in second (with prefix ",
     * elapsed time (second) = ")
     *
     * @param startTime
     *          the start time (in milisecond)
     * @return the elapsed time in second
     */
    public static String getElapsedSecondStr(long startTime) {
        double elapsedTime = getElapsedSecond(startTime);
        return ", elapsed time (second) = " + elapsedTime;
    }

    public static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate currentLocalDate() {
        return LocalDate.now();
    }

    public static LocalDateTime dateToLocalDataTime(Date date) {
        if (null != date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (null != localDateTime) {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static LocalDate dateToLocalData(Date date) {
        if (null != date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public static Date localDateToDate(LocalDate localDate) {
        if (null != localDate) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    /**
     * Current timestamp.
     *
     * @return the timestamp
     */
    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Convert Date to timestamp.
     *
     * @param createDate
     *          input date.
     * @return Timestamp.
     */
    public static Timestamp dateToTimestamp(Date createDate) {
        Timestamp result = null;

        if (createDate != null) {
            result = new Timestamp(createDate.getTime());
        }
        return result;
    }

    /**
     * To time stamp.
     *
     * @param lastmodified the lastmodified
     * @param partern the partern
     * @return the timestamp
     */
    public static Timestamp toTimeStamp(String lastmodified, String partern) {
        Date lastDateModified =
                RmsDateUtils.parseDate(lastmodified, partern);
        return  new Timestamp(lastDateModified.getTime());
    }
}
