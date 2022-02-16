package com.salton123.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类（邮件时间显示问题修改）
 * Author wenjing.Ren
 * Date 2019/6/17
 */
public class DateUtils {
    public static final int TIME_MILLIS_ONE_HOUR = 60 * 60 * 1000;
    public static final int TIME_MILLIS_ONE_DAY = 24 * TIME_MILLIS_ONE_HOUR;

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_ONLY = "yyyy-MM-dd";
    public static final String FORMAT_TIME_ONLY = "HH:mm:ss";

    /**
     * 返回当前日期
     */
    public static String dateofnow() {
        return format(new Date(), FORMAT_DATE_ONLY);
    }

    /**
     * 返回当前时间
     */
    public static String timeofnow() {
        return format(new Date());
    }

    /**
     * 当前日期
     *
     * @return 当前日期
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 格式化日期（默认格式）
     *
     * @param date 日期对象
     * @return 格式化结果
     */
    public static String format(Date date) {
        DateFormat formatter = new SimpleDateFormat(FORMAT_DEFAULT, Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 格式化日期（指定格式）
     *
     * @param date   日期对象
     * @param format 格式化结果
     * @return
     */
    public static String format(Date date, String format) {
        DateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 从字符串解析日期对象
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return 解析得到的日期对象
     */
    public static Date parse(String dateStr, String format) {
        try {
            DateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 某日期是星期几
     *
     * @param date 日期对象
     * @return 星期几（1-7）
     */
    public static int dayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    /**
     * @param millisecond 单位:毫秒
     * @return 返回格式 _天 _:_:_
     */
    public static String getTimeFromStamp(Long millisecond) {
        if (millisecond <= 0) {
            return "";
        }
        String timeStr = "";

        long d = millisecond / (24 * 60 * 60 * 1000);// 天
        long h2 = millisecond / (60 * 60 * 1000) - d * 24;// 小时
        long h = millisecond / (60 * 60 * 1000);// 小时
        long m = millisecond / (60 * 1000) - h * 60;// 分钟前
        long s = millisecond / 1000 - h * 60 * 60 - m * 60;// 秒前

        String day = "";
        if (d > 0) {
            day = String.valueOf(d) + "天 ";
        }
        String hour;
        if (h2 < 10) {
            hour = "0" + h2;
        } else {
            hour = String.valueOf(h2);
        }
        String min;
        if (m < 10) {
            min = "0" + m;
        } else {
            min = String.valueOf(m);
        }
        String secord;
        if (s < 10) {
            secord = "0" + s;
        } else {
            secord = String.valueOf(s);
        }
        timeStr = day + hour + ":" + min + ":" + secord;
        return timeStr;
    }

    /**
     * @param millisecond 单位:毫秒
     * @return 返回格式 _:_:_
     */
    public static String getTimeFromStamp2(Long millisecond) {
        if (millisecond <= 0) {
            return "";
        }
        String timeStr = "";

        long h2 = millisecond / (60 * 60 * 1000);// 小时
        long h = millisecond / (60 * 60 * 1000);// 小时
        long m = millisecond / (60 * 1000) - h * 60;// 分钟前
        long s = millisecond / 1000 - h * 60 * 60 - m * 60;// 秒前

        String hour;
        if (h2 < 10) {
            hour = "0" + h2;
        } else {
            hour = String.valueOf(h2);
        }
        String min;
        if (m < 10) {
            min = "0" + m;
        } else {
            min = String.valueOf(m);
        }
        String secord;
        if (s < 10) {
            secord = "0" + s;
        } else {
            secord = String.valueOf(s);
        }
        timeStr = hour + ":" + min + ":" + secord;
        return timeStr;
    }

    /**
     * @param millisecond 单位:毫秒
     * @return 返回格式 _:_
     */
    public static String getMinuteSecondFromStamp(Long millisecond) {
        if (millisecond <= 0) {
            return "";
        }
        String timeStr = "";

        long m = millisecond / (60 * 1000);// 分钟前
        long s = millisecond / 1000 - m * 60;// 秒前

        String min = "";
        if (m < 10) {
            min = "0" + m;
        } else {
            min = String.valueOf(m);
        }
        String secord = "";
        if (s < 10) {
            secord = "0" + s;
        } else {
            secord = String.valueOf(s);
        }
        timeStr = min + ":" + secord;
        return timeStr;
    }

    public static String formatTime(String pattern, long milli) {
        int m = (int) (milli / android.text.format.DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / android.text.format.DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }

    /**
     * 获取指定年月的天数
     *
     * @param year  年
     * @param month 月（1-12）
     * @return 该年月的天数
     */
    public static int getDayCountInMonth(int year, int month) {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2) {
            if (year % 400 == 0) {
                // 四百年再闰
                return 29;
            }
            if (year % 100 == 0) {
                // 百年不闰
                return 28;
            }
            if (year % 4 == 0) {
                // 四年一闰
                return 29;
            }
            return 28;
        } else {
            return 31;
        }
    }

    /**
     * 获取小时字符串（H:00）
     *
     * @param hour 0-24
     * @return 小时字符串（H:00）
     */
    public static String getHourStr(int hour) {
        return new StringBuilder().append(hour).append(":00").toString();
    }

    /**
     * 是否已满18周岁
     *
     * @param birthday 生日
     * @return
     */
    public static boolean isAdult(Date birthday) {
        if (birthday == null) {
            return false;
        }

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.setTimeInMillis(birthday.getTime());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
        birthdayCalendar.add(Calendar.YEAR, age);
        if (today.before(birthdayCalendar)) {
            age--;
        }
        return age >= 18;
    }

    /**
     * 通用本地时间格式化
     * 支持时间精度为秒或毫秒
     *
     * @param date 日期
     * @return 时间
     */
    public static String commonTimeFormat(Date date) {
        return mailTimeFormat(date);
    }

    /**
     * 通用本地时间格式化
     * 支持时间精度毫秒
     *
     * @param timeMillis 时间戳
     * @return 时间
     */
    public static String commonTimeFormat(long timeMillis) {
        return commonTimeFormat(new Date(timeMillis));
    }

    /**
     * 通用本地时间格式化
     * 支持时间精度毫秒
     *
     * @return 时间
     */
    public static String commonTimeFormatCurrentTime() {
        return commonTimeFormat(new Date());
    }

    public static String mailTimeFormat(long time) {
        return mailTimeFormat(new Date(time));
    }

    /**
     * 邮件聊天页时间格式化
     * 支持时间精度为秒或毫秒
     *
     * @param date
     * @return
     */
    public static String mailTimeFormat(Date date) {
        Date now = new Date();
        StringBuilder sb = new StringBuilder(100);

        Calendar sendCalender = Calendar.getInstance();
        sendCalender.setTime(date);
        Calendar nowCalender = Calendar.getInstance();
        nowCalender.setTime(now);

        int sendDateYear = sendCalender.get(Calendar.YEAR);
        //如果时间的年份算出来是1970，则可能时间精度是到秒，需要乘于1000以毫秒计算
        if (sendDateYear == 1970) {
            date.setTime(date.getTime() * 1000);
            sendCalender.setTime(date);
            sendDateYear = sendCalender.get(Calendar.YEAR);
        }
        int dateMonth = sendCalender.get(Calendar.MONTH) + 1;
        String sendDateMonth = String.valueOf(dateMonth < 10 ? "0" + dateMonth : dateMonth);
        int dateDay = sendCalender.get(Calendar.DATE);
        String sendDateDay = String.valueOf(dateDay < 10 ? "0" + dateDay : dateDay);
        int dateHour = sendCalender.get(Calendar.HOUR_OF_DAY);
        String sendDateHour = String.valueOf(dateHour < 10 ? "0" + dateHour : dateHour);
        int dateMinute = sendCalender.get(Calendar.MINUTE);
        String sendDateMinute = String.valueOf(dateMinute < 10 ? "0" + dateMinute : dateMinute);

        // 不是今年
        if (nowCalender.get(Calendar.YEAR) != sendDateYear) {
            sb.append(sendDateYear).append("年");
            sb.append(sendDateMonth).append("月");
            sb.append(sendDateDay).append("日 ");
            return sb.toString();
        }

        // 今天
        if (isToday(date)) {
            sb.append(sendDateHour).append(":").append(sendDateMinute);
            return sb.toString();
        }

        // 昨天
        if (isYesterday(date)) {
            sb.append("昨天").append(sendDateHour).append(":").append(sendDateMinute);
            return sb.toString();
        }

        // 同一周
        if (sendCalender.get(Calendar.WEEK_OF_YEAR) == nowCalender.get(Calendar.WEEK_OF_YEAR)) {
            sb.append("星期");
            switch (sendCalender.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    sb.append("一 ");
                    break;
                case Calendar.TUESDAY:
                    sb.append("二 ");
                    break;
                case Calendar.WEDNESDAY:
                    sb.append("三 ");
                    break;
                case Calendar.THURSDAY:
                    sb.append("四 ");
                    break;
                case Calendar.FRIDAY:
                    sb.append("五 ");
                    break;
                case Calendar.SATURDAY:
                    sb.append("六 ");
                    break;
                default:
                    sb.append("日 ");
            }
            return sb.toString();
        }

        // 今年的
        sb.append(sendDateMonth).append("月");
        sb.append(sendDateDay).append("日 ");
        return sb.toString();
    }

    /**
     * 邮件聊天页时间格式化
     * 支持时间精度为秒或毫秒
     *
     * @param date
     * @return
     */
    public static String timeFormatNearby(Date date) {
        Date now = new Date();
        StringBuilder sb = new StringBuilder(100);

        Calendar sendCalender = Calendar.getInstance();
        sendCalender.setTime(date);
        Calendar nowCalender = Calendar.getInstance();
        nowCalender.setTime(now);

        int sendDateYear = sendCalender.get(Calendar.YEAR);
        //如果时间的年份算出来是1970，则可能时间精度是到秒，需要乘于1000以毫秒计算
        if (sendDateYear == 1970) {
            date.setTime(date.getTime() * 1000);
            sendCalender.setTime(date);
            sendDateYear = sendCalender.get(Calendar.YEAR);
        }
        int dateMonth = sendCalender.get(Calendar.MONTH) + 1;
        String sendDateMonth = String.valueOf(dateMonth < 10 ? "0" + dateMonth : dateMonth);
        int dateDay = sendCalender.get(Calendar.DATE);
        String sendDateDay = String.valueOf(dateDay < 10 ? "0" + dateDay : dateDay);
        int dateHour = sendCalender.get(Calendar.HOUR_OF_DAY);
        String sendDateHour = String.valueOf(dateHour < 10 ? "0" + dateHour : dateHour);
        int dateMinute = sendCalender.get(Calendar.MINUTE);
        String sendDateMinute = String.valueOf(dateMinute < 10 ? "0" + dateMinute : dateMinute);

        // 不是今年
        if (nowCalender.get(Calendar.YEAR) != sendDateYear) {
            sb.append(sendDateYear).append("年");
            sb.append(sendDateMonth).append("月");
            sb.append(sendDateDay).append("日 ");
            return sb.toString();
        }

        // 今天
        if (isToday(date)) {
            sb.append("今天");
            return sb.toString();
        }

        // 昨天
        if (isYesterday(date)) {
            sb.append("昨天");
            return sb.toString();
        }

        // 同一周
        if (sendCalender.get(Calendar.WEEK_OF_YEAR) == nowCalender.get(Calendar.WEEK_OF_YEAR)) {
            sb.append("星期");
            switch (sendCalender.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    sb.append("一 ");
                    break;
                case Calendar.TUESDAY:
                    sb.append("二 ");
                    break;
                case Calendar.WEDNESDAY:
                    sb.append("三 ");
                    break;
                case Calendar.THURSDAY:
                    sb.append("四 ");
                    break;
                case Calendar.FRIDAY:
                    sb.append("五 ");
                    break;
                case Calendar.SATURDAY:
                    sb.append("六 ");
                    break;
                default:
                    sb.append("日 ");
            }
            return sb.toString();
        }

        // 今年的
        sb.append(sendDateMonth).append("月");
        sb.append(sendDateDay).append("日 ");
        return sb.toString();
    }

    public static Calendar getDayBefore(final Calendar target) {
        target.add(Calendar.DAY_OF_YEAR, -1);
        return target;
    }

    public static boolean isSameDay(final Calendar firstCal, final Calendar secondCal) {
        return (firstCal.get(Calendar.ERA) == secondCal.get(Calendar.ERA) &&
            firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR) &&
            firstCal.get(Calendar.DAY_OF_YEAR) == secondCal.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isSameDay(final Date firstDate, final Date secondDate) {
        Calendar firstCal = Calendar.getInstance();
        Calendar secondCal = Calendar.getInstance();
        firstCal.setTime(firstDate);
        secondCal.setTime(secondDate);
        return isSameDay(firstCal, secondCal);
    }

    /**
     * 是否是同一天
     *
     * @param time
     * @param anotherTime
     * @return
     */
    public static boolean isSameDay(long time, long anotherTime) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(time);
        calendar2.setTimeInMillis(anotherTime);
        return isSameDay(calendar1, calendar2);
    }

    public static boolean isToday(final long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return isSameDay(Calendar.getInstance(), calendar);
    }

    public static boolean isToday(final Calendar queryCal) {
        return isSameDay(Calendar.getInstance(), queryCal);
    }

    public static boolean isToday(final Date queryDate) {
        final Calendar queryCal = Calendar.getInstance();
        queryCal.setTime(queryDate);
        return isToday(queryCal);
    }

    public static boolean isYesterday(final Calendar queryCal) {
        return isSameDay(getDayBefore(Calendar.getInstance()), queryCal);
    }

    public static boolean isYesterday(final Date queryDate) {
        final Calendar queryCal = Calendar.getInstance();
        queryCal.setTime(queryDate);
        return isYesterday(queryCal);
    }

    /**
     * 是否超过n天
     */
    public static boolean isOverNDays(long timeMillis, int nDays) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > timeMillis) {
            return (currentTimeMillis - timeMillis) > (nDays * TIME_MILLIS_ONE_DAY);
        } else {
            return (timeMillis - currentTimeMillis) > (nDays * TIME_MILLIS_ONE_DAY);
        }
    }

    /**
     * 是否超过n小时
     */
    public static boolean isOverNHours(long timeMillis, int nHours) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > timeMillis) {
            return (currentTimeMillis - timeMillis) > (nHours * TIME_MILLIS_ONE_HOUR);
        } else {
            return (timeMillis - currentTimeMillis) > (nHours * TIME_MILLIS_ONE_HOUR);
        }
    }

    public static String timeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static int getYearByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0, 4);
        return Integer.parseInt(year);
    }

    public static int getMonthByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    public static int getDayByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    /**
     * 获取几天前的时间
     *
     * @param millis 参考时间
     * @param day
     * @return
     */
    public static long getDateBefore(long millis, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        return calendar.getTimeInMillis();
    }
}
