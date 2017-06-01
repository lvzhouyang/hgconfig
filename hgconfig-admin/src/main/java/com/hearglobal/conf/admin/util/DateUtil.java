package com.hearglobal.conf.admin.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
public class DateUtil {

    /**
     * The constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * The constant DefaultShortFormat.
     */
    public final static String DefaultShortFormat = "yyyy-MM-dd";
    /**
     * The constant DefaultLongFormat.
     */
    public final static String DefaultLongFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant DefaultMinuteFormat.
     */
    public final static String DefaultMinuteFormat = "yyyy-MM-dd HH:mm";


    /**
     * 格式化为yyyy-MM-dd
     *
     * @param date the date
     * @return string
     * @since 2017.03.21
     */
    public static String Date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultShortFormat);
        return formatter.format(date);
    }

    /**
     * 格式化为 yyyyMMdd
     *
     * @param date the date
     * @return integer
     * @since 2017.03.21
     */
    public static Integer Date2IntDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 秒转日期
     *
     * @param seconds the seconds
     * @return string
     * @since 2017.03.21
     */
    public static String secondsToString(Integer seconds) {
        return Date2String(fromUnixTime(seconds));
    }

    /**
     * 格式化为 yyyy-MM-dd HH:mm
     *
     * @param date the date
     * @return yyyy -MM-dd HH:mm的字符串
     * @since 2017.03.21
     */
    public static String Date2StringMin(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultMinuteFormat);
        return formatter.format(date);
    }

    /**
     * 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date the date
     * @return yyyy -MM-dd HH:mm:ss的字符串
     * @since 2017.03.21
     */
    public static String Date2StringSec(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultLongFormat);
        return formatter.format(date);
    }

    /**
     * Date 2 string sec string.
     *
     * @param seconds the seconds
     * @return 距当前时间的秒数 string
     * @since 2017.03.21
     */
    public static String Date2StringSec(Integer seconds) {
        return Date2StringSec(fromUnixTime(seconds));
    }

    /**
     * 按照指定格式格式化
     *
     * @param date         the date
     * @param formatString the format string
     * @return string
     * @since 2017.03.21
     */
    public static String Date2String(Date date, String formatString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.format(date);
        } catch (IllegalArgumentException e) {
            logger.warn("format string Illegal: {},{}", formatString, e);
            return "";
        }
    }

    /**
     * 返回当前时间的秒数
     *
     * @return int
     * @since 2017.03.21
     */
    public static int unixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 把秒转换为Date
     *
     * @param seconds the seconds
     * @return date
     * @since 2017.03.21
     */
    public static Date fromUnixTime(Integer seconds) {
        return new Date(seconds * 1000L);
    }

    /**
     * 今天的日期
     *
     * @return date
     * @since 2017.03.21
     */
    public static Date today() {
        return toDay(new Date());
    }

    /**
     * 转为日期
     *
     * @param date the date
     * @return the date
     * @since 2017.03.21
     */
    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期当天的最后一秒
     *
     * @param date the date
     * @return date
     * @since 2017.03.21
     */
    public static Date toNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 将时间转换成昨天
     *
     * @param date the date
     * @return date
     * @since 2017.03.21
     */
    public static Date toYesterday(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 将时间转换成明天
     *
     * @param date the date
     * @return date
     * @since 2017.03.21
     */
    public static Date toTommorow(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, 1);
    }

    /**
     * Add date.
     *
     * @param date  the date
     * @param field the field
     * @param value the value
     * @return the date
     * @since 2017.03.21
     */
    private static Date add(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, value);
        return cal.getTime();
    }

    /**
     * 得到某年某月有多少天
     *
     * @param year  the year
     * @param month the month
     * @return month days
     */
    public static int getMonthDays(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到年份
     *
     * @param date the date
     * @return year
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到月份
     *
     * @param date the date
     * @return month
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到某天的星期.1~~7
     *
     * @param date the date
     * @return day of week
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int rel = 0;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                rel = 1;
                break;
            case Calendar.TUESDAY:
                rel = 2;
                break;
            case Calendar.WEDNESDAY:
                rel = 3;
                break;
            case Calendar.THURSDAY:
                rel = 4;
                break;
            case Calendar.FRIDAY:
                rel = 5;
                break;
            case Calendar.SATURDAY:
                rel = 6;
                break;
            default:
                rel = 7;
                break;
        }
        return rel;
    }

    /**
     * date转unixtime
     *
     * @param date the date
     * @return int
     * @since 2017.03.21
     */
    public static int date2Unixtime(Date date) {
        return (int) (date.getTime() / 1000L);
    }

    /**
     * 输入Date 日期得到 时间
     *
     * @param date the date
     * @return date
     * @since 2017.03.21
     */
    public static Date toTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 输入Date 日期和天数 判断今天是否超期
     *
     * @param date     the date
     * @param dayCount the day count
     * @return the boolean
     * @since 2017.03.21
     */
    public static Boolean isExceedSomeDays(Date date, Integer dayCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayCount);
        if (calendar.getTime().before(new Date())) {
            return true;
        }
        return false;

    }


    /**
     * 判断输入的两个时间是否相同
     *
     * @param firtDate   the firt date
     * @param secondDate the second date
     * @return the boolean
     * @since 2017.03.21
     */
    public static boolean isSameDate(Date firtDate, Date secondDate) {
        return toDay(firtDate).equals(toDay(secondDate));
    }

    /**
     * 将integer类型的秒转换称时间字符串输出
     *
     * @param second the second
     * @return the string
     * @since 2017.03.21
     */
    public static String intSecond2Str(Integer second) {
        Long time = second * 1000l;
        Date date = new Date(time);
        return Date2StringSec(date);
    }

    /**
     * yyyyMMdd转yyyy-MM-dd
     *
     * @param dateStr the date str
     * @return the string
     * @since 2017.03.21
     */
    public static String transDateStr(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = formatter.parse(dateStr);
            return Date2String(date, DefaultShortFormat);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到几天前的时间
     *
     * @param d   the d
     * @param day the day
     * @return date before
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }
}
