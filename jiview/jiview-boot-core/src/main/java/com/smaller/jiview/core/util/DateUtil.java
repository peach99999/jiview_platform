package com.smaller.jiview.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private DateUtil() {
    }

    private static final String YYYY_HYPHEN_MM_HYPHEN_DD = "yyyy-MM-dd";

    public static Date string2date(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(strDate, dateTimeFormatter);
        return Date.from(zonedDateTime.toInstant());
    }

    public static LocalDate string2localDate(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(strDate, dateTimeFormatter);
    }

    public static LocalDateTime string2localDateTime(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(strDate, dateTimeFormatter);
    }

    public static String date2string(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = date2localDateTime(date);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime date2localDateTime(Date date) {
        if (date == null) {
            return null;
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static String localDateTime2string(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    public static Date localDateTime2date(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        ZoneId zoneId = ZoneId.systemDefault();
        // Combines this date-time with a time-zone to create a  ZonedDateTime.
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date localDate2date(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStartTime(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        today = getStartTime(cal).getTime();
        return today;
    }

    public static Date getEndTime(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        today = getEndTime(cal).getTime();
        return today;
    }

    public static Calendar getStartTime(Calendar today) {
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    /**
     * 获取当天截止时间
     *
     * @return
     */
    public static Calendar getEndTime(Calendar endToday) {
        endToday.set(Calendar.HOUR_OF_DAY, 23);
        endToday.set(Calendar.MINUTE, 59);
        endToday.set(Calendar.SECOND, 59);
        endToday.set(Calendar.MILLISECOND, 59);
        return endToday;
    }

    /**
     * 获取过期时间
     *
     * @return
     */
    public static Date getExpireDate(int field, int amount, Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(field, amount);

        return cal.getTime();
    }

    public static Date getExpireDate(int field, int amount) {
        Date startDate = new Date();
        return getExpireDate(field, amount, startDate);
    }

    public static String getUnifyOrderExpireDateString(Integer amount, String format) {
        Date expireDate = getExpireDate(Calendar.MINUTE, amount, new Date());
        return date2string(expireDate, format);
    }

    public static Boolean isVaild(String strYear, String strMonth) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(strYear));
            cal.set(Calendar.MONTH, Integer.parseInt(strMonth));
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public static Boolean isVaild(Object objYear, Object objMonth) {
        if (objYear == null || objMonth == null) {
            return Boolean.FALSE;
        }

        return isVaild(String.valueOf(objYear), String.valueOf(objMonth));
    }

    public static Date addDay(Date date, Integer day) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.DATE, day);

        return cal.getTime();
    }

    public static Date obj2dateTime(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(obj.toString());
    }

    public static Date obj2date(Object obj) throws ParseException {
        if (obj == null || StringUtils.isBlank(String.valueOf(obj))) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_HYPHEN_MM_HYPHEN_DD);
        return sdf.parse(obj.toString());
    }

    /**
     * 导入用获取开始日期
     *
     * @param obj
     * @return Date: yyyy-MM-dd 23:59:59
     * @throws ParseException
     */
    public static Date obj2dateTimeStart(Object obj) throws ParseException {
        if (obj == null || StringUtils.isBlank(String.valueOf(obj))) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_HYPHEN_MM_HYPHEN_DD);
        return getStartTime(sdf.parse(obj.toString().trim()));
    }

    /**
     * 导入用获取结束日期
     *
     * @param obj
     * @return Date: yyyy-MM-dd 23:59:59
     * @throws ParseException
     */
    public static Date obj2dateTimeEnd(Object obj) throws ParseException {
        if (obj == null || StringUtils.isBlank(String.valueOf(obj))) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_HYPHEN_MM_HYPHEN_DD);
        return getEndTime(sdf.parse(obj.toString().trim()));
    }

    /**
     * 相同日期比较(年月日)
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDateCompare(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        //fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
        if (d1 == null || d2 == null) {
            return false;
        }
        return fmt.format(d1).equals(fmt.format(d2));
    }

    /**
     * 根据Date获取Mills
     *
     * @return
     */
    public static Long date2millis(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    /**
     * 两个时间之间相差的分钟
     *
     * @return Long
     */
    public static BigDecimal calcDiffMinute(Date startTime, Date endTime) {
        Long time = endTime.getTime() - startTime.getTime();
        return BigDecimal.valueOf(time / 1000 / 60);
    }
}
