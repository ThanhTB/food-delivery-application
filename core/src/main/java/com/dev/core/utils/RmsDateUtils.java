package com.dev.core.utils;

import com.dev.core.report.dto.MtdDTO;
import com.dev.core.report.dto.YtdDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RmsDateUtils {
    private static final String VALUE_DEFAULT = "0";

    private static final int LENGTH_FORMAT_DAY_MONTH = 2;

    public static final int HOUR_PER_DAY = 24;

    public static final int MINUTE_PER_HOUR = 60;

    public static final int SECOND_PER_MINUTE = 60;

    public static final int MILI_SECOND_PER_SECOND = 1000;

    public static final int MIN_YEAR = 1900;

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String NS_YYYYMMDD = "dd.MM.yyyy";

    public static final String NS_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String HHMMSS = "HHmmss";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String YYYYMMDD_TIMEZONE_GMT = "yyyy-MM-dd'T'HH:mm:ss+07:00";

    public static final String DATE_PATTERN_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DDMMYYYY = "dd/MM/yyyy";

    public static final String NS_DDMMYYYY = "dd.MM.yyyy";

    public static final int TYPE_FIRST_DAY = 0;

    public static final int TYPE_LAST_DAY = 1;

    private static final int LAST_MONTH_OF_YEAR = 12;

    private static Matcher matcher;

    private static final String TIME24HOURS_REGEX_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    private static final String DATE24HOURS_REGEX_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-9][0-9]";

    private static final Pattern TIME24HOURS_PATTERN = Pattern.compile(TIME24HOURS_REGEX_PATTERN);

    private static final Pattern DATE24HOURS_PATTERN = Pattern.compile(DATE24HOURS_REGEX_PATTERN);

    private static final int MAX_HOUR_INDEX_23 = 23;

    private static final int MAX_SECOND = 59;

    private static final int MAX_MINUTE = 59;

    private static final int SUNDAY = 1;

    private static final int MONDAY = 2;

    private static final int TUESDAY = 3;

    private static final int WEDNESDAY = 4;

    private static final int THURSDAY = 5;

    private static final int FRIDAY = 6;

    private static final int SATURDAY = 7;

    /**
     * DateUtils default constructor
     */
    private RmsDateUtils() {
    }

    /**
     * Convert from String to Date
     *
     * @param sDate   the string
     * @param pattern the pattern
     * @return converted date
     */
    public static Date parseDate(String sDate, String pattern) {
        Date date = null;
        if (StringUtils.isNotBlank(sDate)) {
            try {
                date = DateUtils.parseDate(sDate, new String[]{pattern});
            } catch (ParseException e) {
                date = null;
            }
        }

        return date;
    }

    /**
     * Convert from String to Timestamp
     *
     * @param sDate   the string
     * @param pattern the pattern
     * @return converted date
     */
    public static Timestamp parseTimestamp(String sDate, String pattern) {
        Timestamp date = null;
        if (StringUtils.isNotBlank(sDate)) {
            try {
                date = (Timestamp) DateUtils.parseDate(sDate, new String[]{pattern});
            } catch (ParseException e) {
                date = null;
            }
        }

        return date;
    }

    /**
     * Format Date to String by pattern.
     *
     * @param date    the date to be formatted
     * @param pattern the pattern to format
     * @return the formatted date
     */
    public static String formatDate(Date date, String pattern) {
        Validate.notNull(pattern);
        String formattedDate = "";
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formattedDate = formatter.format(date);
        }

        return formattedDate;
    }

    /**
     * Get month of date. The range value of month is 1->12
     *
     * @param aDate a date
     * @return month (The range value of month is 1->12)
     */
    public static int getMonth(Date aDate) {
        return getField(aDate, Calendar.MONTH) + 1;
    }

    /**
     * Get year of date.
     *
     * @param aDate a date
     * @return year.
     */
    public static int getYear(Date aDate) {
        return getField(aDate, Calendar.YEAR);
    }

    /**
     * Get day of month date
     *
     * @param aDate a date
     * @return day of month
     */
    public static int getDayOfMonth(Date aDate) {
        return getField(aDate, Calendar.DAY_OF_MONTH);
    }

    /**
     * Get number days of month
     *
     * @param year  current year
     * @param month month to get days
     * @return number of days
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar cal = new GregorianCalendar(year, month - 1, 1);
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysOfMonth;
    }

    /**
     * Get hour of day
     *
     * @param aDate a date
     * @return hour of day
     */
    public static int getHourOfDay(Date aDate) {
        return getField(aDate, Calendar.HOUR_OF_DAY);
    }

    /**
     * Get minute of day
     *
     * @param aDate a date
     * @return minute of day
     */
    public static int getMinute(Date aDate) {
        return getField(aDate, Calendar.MINUTE);
    }

    /**
     * Get the first day of calendar and reset HH:mm:ss to 00:00:00
     *
     * @param cal the Calendar
     * @return the first day of calendar
     */
    public static Date getMinDayOfMonth(Calendar cal) {
        cal.set(Calendar.DATE, 1);
        RmsDateUtils.changeDateTime(cal, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * Get field value of date
     *
     * @param aDate a date
     * @param field field
     * @return field value
     */
    public static int getField(Date aDate, int field) {
        int result = -1;
        if (aDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(aDate);
            result = calendar.get(field);
        }
        return result;
    }

    /**
     * Get next month. The range value is 1->12
     *
     * @param nbNextMonth the next number of months
     * @return next month
     */
    public static int getNextMonth(int nbNextMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, nbNextMonth);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Get month in string format (Jan, Feb,...Dec)
     *
     * @param month the month (the range value is 1->12) to be processed
     * @return the month in string format (Jan, Feb,...Dec)
     */
    public static String getMonthInString(int month) {
        if (1 <= month && month <= LAST_MONTH_OF_YEAR) {
            // create an array of months
            String[] strMonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
                    "Nov", "Dec"};
            return strMonths[month - 1];
        } else {
            return "";
        }
    }

    /**
     * Check if the input time is valid time (24 Hours format, HH:mm or H:mm)
     *
     * @param time the time to be checked
     * @return true if the input time is valid time (24 Hours format), otherwise false
     */
    public static boolean isValidTime24Hours(String time) {
        boolean ok = false;
        if (StringUtils.isNotBlank(time)) {
            matcher = TIME24HOURS_PATTERN.matcher(time);
            ok = matcher.matches();
            // validate HH:mm:dd
            if (!ok) {
                matcher = DATE24HOURS_PATTERN.matcher(time);
                ok = matcher.matches();
            }
        }

        return ok;
    }

    /**
     * Check if two date objects are on the same day ignoring time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     */
    public static boolean isSameDayIgnoreTime(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * Add day to date
     *
     * @param date  date before adding
     * @param nbDay number of days to be added
     * @return date with some days added
     */
    public static Date addDay(Date date, int nbDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, nbDay);
        return calendar.getTime();
    }

    public static LocalDateTime addDay(LocalDateTime date, int nbDay) {
        return date.plusDays(nbDay);
    }

    /**
     * Add number minute to date
     *
     * @param date
     * @param number of minute
     * @return date
     */
    public static Date addMinute(Date date, int nbMinute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, nbMinute);
        return calendar.getTime();
    }

    /**
     * Add duration month to date
     *
     * @param date     date before adding
     * @param duration number of months to be added
     * @return date with some month added
     */
    public static Date addMonth(Date date, int duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, duration);
        return calendar.getTime();
    }

    /**
     * Get date with time is 0:0:0
     *
     * @param aDate a date
     * @return date with time is 0:0:0
     */
    public static Date getDateMin(Date aDate) {
        return changeDateTime(aDate, 0, 0, 0);
    }

    public static Date getDateMin(LocalDateTime aDate) {
        Date date = TimeUtil.localDateTimeToDate(aDate);
        return changeDateTime(date, 0, 0, 0);
    }

    /**
     * Get date with time is 23:59:59
     *
     * @param aDate a date
     * @return date with time is 0:0:0
     */
    public static Date getDateMax(Date aDate) {
        return changeDateTime(aDate, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
    }

    public static Date getDateMax(LocalDateTime aDate) {
        Date date = TimeUtil.localDateTimeToDate(aDate);
        return changeDateTime(date, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
    }

    /**
     * Change time of a date
     *
     * @param aDate  a date
     * @param hour   hour
     * @param minute minute
     * @param second second
     * @return date after changing
     */
    public static Date changeDateTime(Date aDate, int hour, int minute, int second) {
        if (aDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(aDate);
            changeDateTime(calendar, hour, minute, second);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * Change time of a date
     *
     * @param calendar the Calendar
     * @param hour     hour
     * @param minute   minute
     * @param second   second
     */
    public static void changeDateTime(Calendar calendar, int hour, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Check date if it year is less than 1900
     *
     * @param date inputed date
     * @return min_date
     */
    public static synchronized Date normalizeMinDate(final Date date) {
        Date result = null;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) < MIN_YEAR) {
                cal.set(Calendar.YEAR, MIN_YEAR);
            }
            result = cal.getTime();
        }
        return result;
    }

    /**
     * Get exactly the Day Duration between 2 dates
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the Day Duration between 2 dates
     */
    public static double getDayDurationExact(Date startDate, Date endDate) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);

        return (double) (calEnd.getTimeInMillis() - calStart.getTimeInMillis())
                / (HOUR_PER_DAY * MINUTE_PER_HOUR * SECOND_PER_MINUTE * MILI_SECOND_PER_SECOND);

    }

    /**
     * Get the Day Duration between 2 dates, then round up (ex: 2.5 ==> 3)
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the rounded Day Duration between 2 dates
     */
    public static long getDayDurationRoundUp(Date startDate, Date endDate) {
        double diffExact = getDayDurationExact(startDate, endDate);
        BigDecimal num = new BigDecimal(diffExact);
        num = num.setScale(0, BigDecimal.ROUND_HALF_UP);
        return num.longValue();
    }

    /**
     * Get the First Day of Week and reset HH:mm:ss to 00:00:00
     *
     * @param date                the date to process
     * @param calculateFromMonday the flag to calculate (Sunday or Monday)
     * @return the first day of week
     */
    public static Date getMinDayOfWeek(Date date, boolean calculateFromMonday) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int nbDays = -1;
        if ((calculateFromMonday && currentDayOfWeek == 1)
                || (!calculateFromMonday && currentDayOfWeek == Calendar.DAY_OF_WEEK)) {
            // (calculate from Monday & current day is Sunday OR calc from
            // Sunday & current day is Saturday)
            nbDays = Calendar.DAY_OF_WEEK - 1;
        } else if (calculateFromMonday) {
            nbDays = currentDayOfWeek - 2; // (calculate from Monday)
        } else {
            nbDays = currentDayOfWeek - 1; // (calculate from Sunday)
        }
        cal.add(Calendar.DATE, -nbDays);
        return changeDateTime(cal.getTime(), 0, 0, 0); // reset H:m:s
    }

    /**
     * Get the Last Day of Week and reset HH:mm:ss to 23:59:59
     *
     * @param date                the date to process
     * @param calculateFromMonday the flag to calculate (Sunday or Monday)
     * @return the first day of week
     */
    public static Date getMaxDayOfWeek(Date date, boolean calculateFromMonday) {
        date = getLastDayOfWeek(date, calculateFromMonday);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        changeDateTime(cal, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
        return cal.getTime();
    }

    /**
     * Get the Last Day of Week. The HH:mm:ss is unchanged
     *
     * @param date                the date to process
     * @param calculateFromMonday the flag to calculate (Sunday or Monday)
     * @return the last day of week
     */
    public static Date getLastDayOfWeek(Date date, boolean calculateFromMonday) {
        Date lastDay;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if ((calculateFromMonday && currentDayOfWeek == 1)
                || (!calculateFromMonday && currentDayOfWeek == Calendar.DAY_OF_WEEK)) {
            // (calculate from Monday & current day is Sunday OR calc from
            // Sunday & current day is Saturday)
            lastDay = date;
        } else {
            int maxDaysInWeek = cal.getActualMaximum(Calendar.DAY_OF_WEEK); // instead
            // of
            // hard-coding
            // 7
            int lastDayOfWeek = 0;
            lastDayOfWeek = maxDaysInWeek - currentDayOfWeek;
            if (calculateFromMonday) {
                lastDayOfWeek++;
            }
            cal.add(Calendar.DATE, lastDayOfWeek);
            lastDay = cal.getTime();
        }
        return lastDay;
    }

    /**
     * Get the next date of a date
     *
     * @param date the current date
     * @return the next date of a date
     */
    public static Date getNextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Get the next month of a date
     *
     * @param date the current date
     * @return the next month of a date
     */
    public static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * Get min day of month (Get 01/MM/yyyy 00:00:00)
     *
     * @param date the current date
     * @return the min day of month
     */
    public static Date getMinDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        changeDateTime(cal, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * Get max day of month.
     *
     * @param date the current date
     * @return the max day of month
     */
    public static Date getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        changeDateTime(cal, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
        return cal.getTime();
    }

    /**
     * Get max day of month.
     *
     * @param date the current date
     * @return the max day of month
     */
    public static Date getMaxDayOfMonthNextYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.YEAR, 1);
        changeDateTime(cal, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
        return cal.getTime();
    }

    /**
     * Get min day of year (Get 01/01/yyyy 00:00:00)
     *
     * @param date the current date
     * @return the min day of year.
     */
    public static Date getMinDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        changeDateTime(cal, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * Get the first day of months (from "fromDate" to "toDate").
     *
     * @param fromDate the start date
     * @param toDate   the end date
     * @return the list of months
     */
    public static List<Date> getMinDayOfMonths(Date fromDate, Date toDate) {
        List<Date> dateList = new ArrayList<Date>();
        fromDate = RmsDateUtils.getMinDayOfMonth(fromDate);
        toDate = RmsDateUtils.getMinDayOfMonth(toDate);
        Date curDate = fromDate;
        while (curDate.compareTo(toDate) <= 0) {
            dateList.add(curDate);
            curDate = RmsDateUtils.getNextMonth(curDate);
        }
        return dateList;
    }

    /**
     * Get the last day of months (from "fromDate" to "toDate").
     *
     * @param fromDate the start date
     * @param toDate   the end date
     * @return the list of months
     */
    public static List<Date> getMaxDayOfMonths(Date fromDate, Date toDate) {
        List<Date> months = new ArrayList<Date>();
        fromDate = RmsDateUtils.getMaxDayOfMonth(fromDate);
        toDate = RmsDateUtils.getMaxDayOfMonth(toDate);
        Date curDate = fromDate;
        while (curDate.compareTo(toDate) <= 0) {
            months.add(curDate);
            curDate = RmsDateUtils.getNextMonth(curDate);
        }
        return months;
    }

    /**
     * Get the min date of next week.
     *
     * @param date the current date
     * @return the next date of a date
     */
    public static Date getMinDayOfNextWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        changeDateTime(cal, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * Get the date of next week.
     *
     * @param date the current date
     * @return the next date of a date
     */
    public static Date getDayOfNextWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * Compare two date without time.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the value 0 if the date1 is equal to date2; a value less than 0 if date1 is before the date2; and a value
     * greater than 0 if date1 is after the date2.
     */
    public static int compareDayIgnoreTime(Date date1, Date date2) {
        return getDateMin(date1).compareTo(getDateMin(date2));
    }

    public static int compareDayIgnoreTime(LocalDateTime date1, LocalDateTime date2) {
        Date local1 = TimeUtil.localDateTimeToDate(date1);
        Date local2 = TimeUtil.localDateTimeToDate(date2);
        return getDateMin(local1).compareTo(getDateMin(local2));
    }

    public static int compareDayIgnoreTime(LocalDateTime date1, Date date2) {
        Date local1 = TimeUtil.localDateTimeToDate(date1);
        return getDateMin(local1).compareTo(getDateMin(date2));
    }

    /**
     * Compare two date without day of month and time.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the value 0 if the date1 is equal to date2; a value less than 0 if date1 is before the date2; and a value
     * greater than 0 if date1 is after the date2.
     */
    public static int compareMonthAndYear(Date date1, Date date2) {
        return getMinDayOfMonth(date1).compareTo(getMinDayOfMonth(date2));
    }

    /**
     * Compare two date
     *
     * @param date1 Date 1
     * @param date2 Date 2
     * @return true If they are the same value
     */
    public static boolean compareDateTime(Date date1, Date date2) {
        boolean result = false;

        if (date1 == null) {
            if (date2 == null) {
                result = true;
            }
        } else {
            if (date2 != null) {
                result = date1.compareTo(date2) == 0 ? true : false;
            }
        }

        return result;
    }

    public static boolean compareDateTime(LocalDateTime localDate1, LocalDateTime localDate2) {
        Date date1 = TimeUtil.localDateTimeToDate(localDate1);
        Date date2 = TimeUtil.localDateTimeToDate(localDate2);
        return compareDateTime(date1, date2);
    }

    /**
     * Explanation of processing
     *
     * @param fromDate
     * @return
     */
    public static Date getDateMin(String fromDate) {

        Date date = parseDate(fromDate, YYYYMMDD);
        Date result = null;

        if (null != date) {
            result = getDateMin(date);
        }
        return result;
    }

    /**
     * Explanation of processing
     *
     * @param fromDate
     * @return
     */
    public static Date getDateMax(String fromDate) {

        Date date = parseDate(fromDate, YYYYMMDD);
        Date result = null;

        if (null != date) {
            result = getDateMax(date);
        }
        return result;
    }

    public static Date setTime(Date date, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static Date setTime(int year, int month, int day, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static int getDistinctDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        long dayByMilli = HOUR_PER_DAY * MINUTE_PER_HOUR * SECOND_PER_MINUTE * MILI_SECOND_PER_SECOND;
        long temp = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        long numberOfDays = temp / dayByMilli;

        return (int) numberOfDays;
    }

    public static int getDistinctByDay(Date d1, Date d2) {
        return getDayOfMonth(d2) - getDayOfMonth(d1);
    }

    /**
     * Get previous date by month offset.
     *
     * <pre>
     *      DateUtils.getPreDateByMonth(20-Oct-2014, 3) --> 20-July-2014
     * </pre>
     *
     * @param fromDate From date
     * @param months   Number of month
     * @return Date
     * @author ninhhp
     */
    public static Date getPreDateByMonth(Date fromDate, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.MONTH, -months);
        return c.getTime();
    }

    /**
     * Get previous date of year-month-day
     *
     * @param year  year to get
     * @param month month to get
     * @param day   day to get
     * @return list contains year-month-previous day
     */
    public static List<Integer> getPreviousDate(int year, int month, int day) {
        List<Integer> result = new ArrayList<Integer>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, day);
        cal.add(Calendar.DATE, -1);
        result.add(cal.get(Calendar.YEAR));
        result.add(cal.get(Calendar.MONTH) + 1);
        result.add(cal.get(Calendar.DATE));
        return result;
    }

    /**
     * Get next date of year-month-day
     *
     * @param year  year to get
     * @param month month to get
     * @param day   day to get
     * @return list contains year-month-next day
     */
    public static List<Integer> getNextDate(int year, int month, int day) {
        List<Integer> result = new ArrayList<Integer>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.add(Calendar.DATE, 1);
        if (cal.get(Calendar.YEAR) != year) {
            cal.add(Calendar.MONTH, 1);
        }
        result.add(cal.get(Calendar.YEAR));
        if (cal.get(Calendar.YEAR) != year) {
            result.add(cal.get(Calendar.MONTH));
        } else {
            result.add(cal.get(Calendar.MONTH) + 1);
        }
        result.add(cal.get(Calendar.DATE));
        return result;
    }

    /**
     * Get short name day of week
     *
     * @param year  year to get
     * @param month month to get
     * @param day   day to get
     * @return short name day of week
     */
    public static String getDayOfWeek(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month - 1, day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String result = "";
        switch (dayOfWeek) {
            case SUNDAY:
                result = "SUN";
                break;
            case MONDAY:
                result = "MON";
                break;
            case TUESDAY:
                result = "TUE";
                break;
            case WEDNESDAY:
                result = "WED";
                break;
            case THURSDAY:
                result = "THU";
                break;
            case FRIDAY:
                result = "FRI";
                break;
            case SATURDAY:
                result = "SAT";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Get first day or last day of year by type
     *
     * @param ytdVO {@link ytdVO} to get
     * @param month month to get
     * @param type  DateUtils.TYPE_FIRST_DAY / DateUtils.TYPE_LAST_DAY
     * @return day as string
     */
    public static String getDayByType(YtdDTO ytdVO, int month, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat(RmsDateUtils.YYYYMMDDHHMMSS);
        String result = null;
        if (null != ytdVO) {
            Calendar cal = Calendar.getInstance();
            switch (type) {
                case TYPE_FIRST_DAY:
                    cal.set(Calendar.DATE, 1);
                    cal.set(Calendar.MONTH, 0);
                    cal.set(Calendar.YEAR, ytdVO.getYear());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    if (ytdVO.getLd() > 0) {
                        cal.add(Calendar.DATE, -ytdVO.getLd());
                    }
                    result = sdf.format(cal.getTime());
                    break;
                case TYPE_LAST_DAY:
                    Date fromDate = RmsDateUtils.setTime(ytdVO.getYear(), month - 1, 1, 0, 0, 0);
                    Date toDate = RmsDateUtils.getMaxDayOfMonth(fromDate);
                    toDate = RmsDateUtils.addDay(toDate, ytdVO.getNd());

                    /*
                     * cal.set(Calendar.MONTH, month - 1); int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                     * cal.set(Calendar.DATE, maxDay); cal.set(Calendar.YEAR, ytdVO.getYear());
                     * cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR_INDEX_23); cal.set(Calendar.MINUTE, MAX_MINUTE);
                     * cal.set(Calendar.SECOND, MAX_SECOND); cal.add(Calendar.DATE, ytdVO.getNd());
                     */
                    result = sdf.format(toDate.getTime());
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * Get first day or last day of month by type
     *
     * @param mtdVO {@link MtdDTO} to get
     * @param month month to get
     * @param type  DateUtils.TYPE_FIRST_DAY / DateUtils.TYPE_LAST_DAY
     * @return day as string
     */
    public static String getDayByType(MtdDTO mtdVO, int month, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat(RmsDateUtils.YYYYMMDDHHMMSS);
        String result = null;
        if (null != mtdVO) {
            Calendar cal = Calendar.getInstance();
            switch (type) {
                case TYPE_FIRST_DAY:

                    cal.set(Calendar.DATE, 1);
                    cal.set(Calendar.MONTH, month - 1);
                    cal.set(Calendar.YEAR, mtdVO.getYear());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    if (mtdVO.getLd() > 0) {
                        cal.add(Calendar.DATE, -mtdVO.getLd());
                    }
                    result = sdf.format(cal.getTime());
                    break;
                case TYPE_LAST_DAY:
                    Date fromDate = RmsDateUtils.setTime(mtdVO.getYear(), month - 1, 1, 0, 0, 0);
                    Date toDate = RmsDateUtils.getMaxDayOfMonth(fromDate);
                    toDate = RmsDateUtils.addDay(toDate, mtdVO.getNd());

                    /*
                     * cal.set(Calendar.MONTH, month - 1); int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                     * cal.set(Calendar.DATE, maxDay); cal.set(Calendar.YEAR, mtdVO.getYear());
                     * cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR_INDEX_23); cal.set(Calendar.MINUTE, MAX_MINUTE);
                     * cal.set(Calendar.SECOND, MAX_SECOND); //trucvl - LOREAL-4038 //if (mtdVO.getNd() > 0) {
                     * cal.add(Calendar.DATE, mtdVO.getNd());
                     */
                    // }
                    // end trucvl
                    result = sdf.format(toDate.getTime());
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static final String changeFormatDateString(String orgFormat, String newFormat, String orgDateString) {
        Date orgDate = RmsDateUtils.parseDate(orgDateString, orgFormat);
        if (orgDate == null) {
            return null;
        }
        String newDateString = new SimpleDateFormat(newFormat).format(orgDate).toString();
        return newDateString;
    }

    /**
     * Convert string to date, if string is invalid return null
     *
     * @param dateString
     * @return
     */
    public static Date convertStringToDate(String dateString) {
        Date date = null;
        if (StringUtils.isNotEmpty(dateString)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
            }
        }
        return date;
    }

    /**
     * Remove mili-second.
     *
     * @param date Input date
     * @return date time value
     */
    public static Date removeMilisecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Validate date of birth.
     *
     * @param date  Day
     * @param month Month
     * @param year  Year
     * @return true if date is valid. Otherwise return false.
     */
    public static boolean validateDateOfBirth(Integer date, Integer month, Integer year) {
        boolean isTrueDate = true;
        StringBuilder str = new StringBuilder();
        str.append(StringUtils.leftPad(date.toString(), LENGTH_FORMAT_DAY_MONTH, VALUE_DEFAULT)).append("/");
        str.append(StringUtils.leftPad(month.toString(), LENGTH_FORMAT_DAY_MONTH, VALUE_DEFAULT)).append("/");
        str.append(year);
        try {
            DateUtils.parseDateStrictly(str.toString(), DDMMYYYY);
        } catch (ParseException e) {
            isTrueDate = false;
        }
        return isTrueDate;
    }

    /**
     * Calculate expiration date from input date with quarter in year.
     *
     * @param date          Input date
     * @param quarterInYear the number of quarter in year
     * @param plusMonth     the number of month will be added
     * @return Expired date
     */
    public static Date getExpireDate(Date date, int quarterInYear, int plusMonth) {
        Date result;
        int monthOfQuarter;
        int expiredMonth;

        Calendar cal = Calendar.getInstance();

        // Calculate the number of month per quarter
        monthOfQuarter = 12 / quarterInYear;

        expiredMonth = (((RmsDateUtils.getMonth(date) - 1) / monthOfQuarter) + 1) * monthOfQuarter;
        // Build Date
        if (expiredMonth == 12) { // get the first day of next year
            cal.set(RmsDateUtils.getYear(date) + 1, 0, 1, 23, 59, 59);
        } else {
            // Get the fist day of next month
            cal.set(RmsDateUtils.getYear(date), expiredMonth, 1, 23, 59, 59);

        }
        cal.add(Calendar.MONTH, plusMonth);
        cal.add(Calendar.DATE, -1);

        // get result
        result = cal.getTime();
        return result;
    }

    public static Date getExpireDateByMonth(Date runDate, int pointExpiredMonth) {
        Date result = addMonth(runDate, pointExpiredMonth);
        result = getMaxDayOfMonth(result);
        return result;
    }

    /**
     * Get the current Day of Week
     *
     * @param date the date to process
     * @return current Day Of Week
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return currentDayOfWeek;
    }
}
