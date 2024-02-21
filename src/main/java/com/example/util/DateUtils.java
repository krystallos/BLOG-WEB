package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    public static final String FORMATPATTERN_DATE = "yyyy-MM-dd";
    public static final String FORMATPATTERN_SHORT_DATE = "yyyyMMdd";
    public static final String FORMATPATTERN_SHORT_DATETIME = "yyyyMMddHHmmss";
    public static final String FORMATPATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_short_date = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf_short_datetime = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Object lockObj = new Object();
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap();

    public DateUtils() {
    }

    public static void main(String[] args) {
    }

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = (ThreadLocal)sdfMap.get(pattern);
        if (tl == null) {
            synchronized(lockObj) {
                tl = (ThreadLocal)sdfMap.get(pattern);
                if (tl == null) {
                    System.out.println("put new sdf of pattern " + pattern + " to map");
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return (SimpleDateFormat)tl.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

    public static String getCurrentDate() {
        return sdf_date.format(new Date());
    }

    public static String getCurrentShortDate() {
        return sdf_short_date.format(new Date());
    }

    public static String getCurrentShortDateTime() {
        return sdf_short_datetime.format(new Date());
    }

    public static String getCurrentDateByParam(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    public static Date getTodayDateTime() throws ParseException {
        return sdf_date.parse(sdf_date.format(new Date()));
    }

    public static Date getTodayDate() throws ParseException {
        return sdf_datetime.parse(sdf_datetime.format(new Date()));
    }

    public static String getCurrentDateTime() {
        return sdf_datetime.format(new Date());
    }

    public static String getDesignatedDate(long timeDiff) {
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return sdf_date.format(designTime);
    }

    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        cal.add(5, day);
        Date datNew = cal.getTime();
        return sdf_date.format(datNew);
    }

    public static String dateToString(Date date) {
        return date == null ? null : sdf_date.format(date);
    }

    public static String dateToTimeString(Date date) {
        return date == null ? null : sdf_datetime.format(date);
    }

    public static Date stringToDate(String str) {
        if (!str.equals("") && str != null) {
            try {
                return sdf_date.parse(str);
            } catch (ParseException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }

    public static Date timeStringToDate(String str) {
        if (str != null && !"".equals(str.trim())) {
            try {
                return sdf_datetime.parse(str);
            } catch (ParseException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }

    public void timeSubtract() {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        Date end = null;

        try {
            begin = dfs.parse("2004-01-02 11:30:24");
            end = dfs.parse("2004-03-26 13:31:40");
        } catch (ParseException var14) {
            var14.printStackTrace();
        }

        long between = (end.getTime() - begin.getTime()) / 1000L;
        long day1 = between / 86400L;
        long hour1 = between % 86400L / 3600L;
        long minute1 = between % 3600L / 60L;
        long second1 = between % 60L;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
    }

    public static String getDateTime(String format) {
        String strDateTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        strDateTime = sdf.format(new Date());
        return strDateTime;
    }

    public static int getHour(Date date, int addHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(11, addHour);
        return c.get(11);
    }

    public static Date getDateAndDay(Date date, int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, addDay);
        return c.getTime();
    }

    public static Date getDateAndYear(Date date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, addDay);
        return calendar.getTime();
    }

    public static Date getDateAndHour(Date date, int addHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(10, addHour);
        return c.getTime();
    }

    public static Date getDateAndMin(Date date, int addMin) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(12, addMin);
        return c.getTime();
    }

    public static Date getDateFromStr(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String getDateTime(Date date, String format) {
        String strDateTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        strDateTime = sdf.format(date);
        return strDateTime;
    }

    public static String getNowTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static boolean isFirstDayOfMonth(Date date) {
        boolean flag = false;
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }

        int today = calendar.get(5);
        if (1 == today) {
            flag = true;
        }

        return flag;
    }

    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(5, 1);
        a.roll(5, -1);
        int maxDate = a.get(5);
        return maxDate;
    }

    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(1, year);
        a.set(2, month - 1);
        a.set(5, 1);
        a.roll(5, -1);
        int maxDate = a.get(5);
        return maxDate;
    }

    public static String getMaxMonthDate(Date date) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        calendar.set(5, calendar.getActualMaximum(5));
        return dft.format(calendar.getTime());
    }

    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, 1);
        calendar.set(5, calendar.getActualMinimum(5));
        return dft.format(calendar.getTime());
    }

    public static String getLastMaxMonthDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, -1);
        calendar.set(5, calendar.getActualMaximum(5));
        return dft.format(calendar.getTime());
    }

    public static String getLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        cal.add(2, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static String getNexMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        cal.add(2, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    public static boolean isLastDayOfMonth(Date date) {
        boolean flag = false;
        if (getNowTime().equals(getMaxMonthDate(date))) {
            flag = true;
        }

        return flag;
    }

    public static String getNexMonth(String date) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        int year = Integer.parseInt(date.substring(0, 4));
        String monthsString = date.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }

        cal.set(year, month, 5);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }

        cal.set(year, month - 2, 5);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    public static String getMaxMonthDate(String repeatDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        try {
            if (null != repeatDate && !"".equals(repeatDate)) {
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        calendar.set(5, calendar.getActualMaximum(5));
        return dft.format(calendar.getTime());
    }

    public static String getMinMonthDate(String repeatDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        try {
            if (null != repeatDate && !"".equals(repeatDate)) {
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        calendar.set(5, calendar.getActualMinimum(5));
        return dft.format(calendar.getTime());
    }

    public static String getModify2DaysAgo(String repeatDate) {
        Calendar cal = Calendar.getInstance();
        String daysAgo = "";
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        if (repeatDate != null && !"".equals(repeatDate)) {
            int year = Integer.parseInt(repeatDate.substring(0, 4));
            String monthsString = repeatDate.substring(4, 6);
            int month;
            if ("0".equals(monthsString.substring(0, 1))) {
                month = Integer.parseInt(monthsString.substring(1, 2));
            } else {
                month = Integer.parseInt(monthsString.substring(0, 2));
            }

            String dateString = repeatDate.substring(6, 8);
            int date;
            if ("0".equals(dateString.subSequence(0, 1))) {
                date = Integer.parseInt(dateString.substring(1, 2));
            } else {
                date = Integer.parseInt(dateString.substring(0, 2));
            }

            cal.set(year, month - 1, date - 1);
            System.out.println(dft.format(cal.getTime()));
        } else {
            cal.set(5, cal.get(5) - 2);
        }

        daysAgo = dft.format(cal.getTime());
        return daysAgo;
    }

    public static String getModifyNumDaysAgo(String repeatDate, int param) {
        Calendar cal = Calendar.getInstance();
        String daysAgo = "";
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        if (repeatDate != null && !"".equals(repeatDate)) {
            int year = Integer.parseInt(repeatDate.substring(0, 4));
            String monthsString = repeatDate.substring(4, 6);
            int month;
            if ("0".equals(monthsString.substring(0, 1))) {
                month = Integer.parseInt(monthsString.substring(1, 2));
            } else {
                month = Integer.parseInt(monthsString.substring(0, 2));
            }

            String dateString = repeatDate.substring(6, 8);
            int date;
            if ("0".equals(dateString.subSequence(0, 1))) {
                date = Integer.parseInt(dateString.substring(1, 2));
            } else {
                date = Integer.parseInt(dateString.substring(0, 2));
            }

            cal.set(year, month - 1, date - param + 1);
            System.out.println(dft.format(cal.getTime()));
        } else {
            cal.set(5, cal.get(5) - param);
        }

        daysAgo = dft.format(cal.getTime());
        return daysAgo;
    }

    public static String nowDate2Millisecond() {
        Date newDate = new Date();
        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyyMMddHHmmssFFF");
        return simpleDF.format(newDate);
    }

    public static Date StrToDate(String dateStr, String format) throws Exception {
        new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateStr);
        return date;
    }

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date datet = null;

        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        int w = cal.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static String dateStringFormat(String time) {
        String YY = time.substring(0, 4);
        String MM = time.substring(4, 6);
        String dd = time.substring(6, 8);
        String hh = time.substring(8, 10);
        String mm = time.substring(10, 12);
        String ss = time.substring(12, time.length());
        String result = YY + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss;
        return result;
    }
}
