package com.salton123.utils;

import com.salton123.eleph.R;

import org.xutils.x;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {
    /**
     * 时间格式化
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
        // 不是今年
        if (nowCalender.get(Calendar.YEAR) != sendDateYear) {
            sb.append(sendDateYear).append(x.app().getString(R.string.year));
            sb.append(sendDateMonth).append(x.app().getString(R.string.month));
            sb.append(sendDateDay).append(x.app().getString(R.string.day));
            return sb.toString();
        }

        // 今天
        if (isToday(date)) {
            sb.append(x.app().getString(R.string.today));
            return sb.toString();
        }

        // 昨天
        if (isYesterday(date)) {
            sb.append(x.app().getString(R.string.yesterday));
            return sb.toString();
        }

        // 同一周
        if (sendCalender.get(Calendar.WEEK_OF_YEAR) == nowCalender.get(Calendar.WEEK_OF_YEAR)) {
            switch (sendCalender.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    sb.append(x.app().getString(R.string.monday));
                    break;
                case Calendar.TUESDAY:
                    sb.append(x.app().getString(R.string.tuesday));
                    break;
                case Calendar.WEDNESDAY:
                    sb.append(x.app().getString(R.string.wednesday));
                    break;
                case Calendar.THURSDAY:
                    sb.append(x.app().getString(R.string.thursday));
                    break;
                case Calendar.FRIDAY:
                    sb.append(x.app().getString(R.string.friday));
                    break;
                case Calendar.SATURDAY:
                    sb.append(x.app().getString(R.string.saturday));
                    break;
                default:
                    sb.append(x.app().getString(R.string.sunday));
            }
            return sb.toString();
        }

        // 今年的
        sb.append(sendDateMonth).append(x.app().getString(R.string.month));
        sb.append(sendDateDay).append(x.app().getString(R.string.day));
        return sb.toString();
    }

    public static boolean isToday(final Calendar queryCal) {
        return isSameDay(Calendar.getInstance(), queryCal);
    }

    public static boolean isToday(final Date queryDate) {
        final Calendar queryCal = Calendar.getInstance();
        queryCal.setTime(queryDate);
        return isToday(queryCal);
    }

    public static boolean isSameDay(final Calendar firstCal, final Calendar secondCal) {
        return (firstCal.get(Calendar.ERA) == secondCal.get(Calendar.ERA) &&
            firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR) &&
            firstCal.get(Calendar.DAY_OF_YEAR) == secondCal.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isYesterday(final Calendar queryCal) {
        return isSameDay(getDayBefore(Calendar.getInstance()), queryCal);
    }

    public static boolean isYesterday(final Date queryDate) {
        final Calendar queryCal = Calendar.getInstance();
        queryCal.setTime(queryDate);
        return isYesterday(queryCal);
    }

    public static Calendar getDayBefore(final Calendar target) {
        target.add(Calendar.DAY_OF_YEAR, -1);
        return target;
    }
}
