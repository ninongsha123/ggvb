package com.tiancaijiazu.app.utils;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.view.View;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2019/5/20/020.
 */

public class TimeUtil {
    private static final String TAG ="TimeUtuil" ;
    private static SimpleDateFormat sf = null;


    public static boolean getTokenTime() {
        List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
        if (select.size() != 0) {
            int validtime = Integer.parseInt(select.get(select.size() - 1).getValidtime());
            String time = select.get(select.size() - 1).getTime();
            Log.i("yx==", "getTokenTime: " + time);
            String nowTime = TimeUtil.getNowTime();
            Log.i("yx==", "getTokenTime: " + nowTime);
            String timeExpend = TimeUtil.getTimeExpend(time, nowTime);
            Log.i("yx==", "getTokenTime: " + timeExpend);
            int i = Integer.parseInt(timeExpend);
            Log.i("yx===", validtime + "getTokenTime===: " + i);
            if (validtime - (i/60) <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean getTokenTimeRefresh() {
        List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
        if (select.size() != 0) {
            int validtime = Integer.parseInt(select.get(select.size() - 1).getValidtime());
            String time = select.get(select.size() - 1).getTime();
            String nowTime = TimeUtil.getNowTime();
            String timeExpend = TimeUtil.getTimeExpend(time, nowTime);
            int i = Integer.parseInt(timeExpend);
            Log.i("ceshitoken", validtime+"getTokenTimeRefresh: "+i);
            if (validtime - (i/60) <= 864000&&validtime-(i/60)>0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static String getAges(String date) {
        String[] data = date.split("-");
        if (data.length < 3) return "";
        Integer.valueOf(Log.d(TAG, data[0]));
        Integer.valueOf(Log.d(TAG, data[1]));
        Integer.valueOf(Log.d(TAG, data[2]));
        Calendar birthday = new GregorianCalendar(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) + 1 - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        System.out.println("年龄：" + year + "岁" + month + "月" + day + "天");
        StringBuffer tag = new StringBuffer();
        if (year > 0) {
            tag.append(year + "岁");
        }
        if (month > 0) {
            tag.append(month + "个月");
        }
        if (day > 0) {
            tag.append(day + "天");
        }
        if (year == 0 && month == 0 && day == 0) {
            tag.append("今日出生");
        }
        return String.valueOf(tag);
    }


    /**
     * @param target      防止多次点击的View
     * @param defaultTime 超时时间
     * @return
     */
    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long defaultTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;
        Object o = target.getTag(R.id.invalid_click);
        if (o == null) {
            target.setTag(R.id.invalid_click, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < defaultTime;
        if (!isInvalid) {
            target.setTag(R.id.invalid_click, curTimeStamp);
        }
        return isInvalid;
    }

    public static boolean getTokenTimeSua() {
        List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
        if (select.size() != 0) {
            int validtime = Integer.parseInt(select.get(select.size() - 1).getValidtime());
            String time = select.get(select.size() - 1).getTime();
            Log.i("yx==", "getTokenTime: " + time);
            String nowTime = TimeUtil.getNowTime();
            Log.i("yx===", "getTokenTime: " + nowTime);
            String timeExpend = TimeUtil.getTimeExpend(time, nowTime);
            Log.i("yx===", "getTokenTime: " + timeExpend);
            int i = Integer.parseInt(timeExpend);
            Log.i("yx===", validtime + "getTokenTime: " + i);
            if (validtime - i <= 7020 && validtime - i > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getTimeExpend(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        Log.i("yx==", "getTimeExpend: " + longStart);
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        Log.i("yx==", "getTimeExpend: " + longEnd);
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = longExpend / 1000;   //根据时间差来计算分钟数

        return "" + longMinutes;
    }

    private static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":"
                + minute+":"+second;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }
    public static String getNowThree() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString = year + "-" + month + "-" + monthDay;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }
    public static String getNowFore() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString =hour + ":" + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }
    public static String getNowFive() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString = year + "-" + month + "-" + monthDay+" "+ hour + ":" + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }
    public int calculate(int year, int month) {

        boolean yearleap = judge(year);
        int day;
        if (yearleap && month == 2) {
            day = 29;
        } else if (!yearleap && month == 2) {
            day = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        } else {
            day = 31;
        }
        return day;
    }

    public boolean judge(int year) {
        boolean yearleap = (year % 400 == 0) || (year % 4 == 0)
                && (year % 100 != 0);// 采用布尔数据计算判断是否能整除
        return yearleap;
    }

    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    public static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     *                 返回类型 ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    public static String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            /*long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";*/
            timeString = day+"天"+hour + "小时" + min + "分"+s+"秒";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    public static String getTimeDay(long diff) {
        String timeString = "";

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            timeString = day+"";

        return timeString;
    }
    public static String getTimeHour(long diff) {
        String timeString = "";

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            timeString = hour+"";

        return timeString;
    }
    public static String getTimeMin(long diff) {
        String timeString = "";

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            timeString = min+"";

        return timeString;
    }
    public static String getTimeS(long diff) {
        String timeString = "";

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            timeString = s+"";

        return timeString;
    }
    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = Float.toString(hour1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }
    public static String getDateStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    /**
     * 获取时间中的某一个时间点
     *
     * @param str
     * @param type
     * @return
     */
    public String getJsonParseShiJian(String str, int type) {
        String shijanString = null;
        String nian = str.substring(0, str.indexOf("-"));
        String yue = str.substring(str.indexOf("-") + 1, str.lastIndexOf("-"));
        String tian = str.substring(str.lastIndexOf("-") + 1, str.indexOf(" "));
        String shi = str.substring(str.indexOf(" ") + 1, str.lastIndexOf(":"));
        String fen = str.substring(str.lastIndexOf(":") + 1, str.length());

        switch (type) {
            case 1:
                shijanString = nian;
                break;
            case 2:
                shijanString = yue;
                break;
            case 3:
                shijanString = tian;
                break;
            case 4:
                shijanString = shi;
                break;
            case 5:
                shijanString = fen;
                break;

        }
        return shijanString;
    }

    /**
     * Sring变int
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        int value = 0;
        value = Integer.parseInt(str);
        return value;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            if (diff <= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 把时间戳变yyyy-MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 返回时间戳
     *
     * @param time
     * @return
     */
    public static long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 返回时间戳秒
     *
     * @param time
     * @return
     */
    public static int dataOneMiao(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        int l = 0;
        try {
            date = sdr.parse(time);
            l = (int) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public static boolean compareTwoTime(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff >= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    public boolean compareTwoTime2(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff >= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    /**
     * 获取年
     *
     * @param time
     * @return
     */
    public String getTimeYear(String time) {

        String substring = time.substring(0, time.lastIndexOf(" "));
        return substring;

    }

    /**
     * 换算小时，0.5小时-->0小时30分
     *
     * @param hour
     * @return
     */
    private String convertTime(String hour) {

        String substring = hour.substring(0, hour.lastIndexOf("."));
        String substring2 = hour.substring(hour.lastIndexOf(".") + 1,
                hour.length());
        substring2 = "0." + substring2;
        float f2 = Float.parseFloat(substring2);
        f2 = f2 * 60;
        String string = Float.toString(f2);
        String min = string.substring(0, string.lastIndexOf("."));
        return substring + "小时" + min + "分";

    }

    /**
     * 仿QQ，微信聊天时间格式化
     *
     * @param time 时间戳
     * @return
     */
    public static String QQFormatTime(long time) {
        Date date = new Date();
        date.setTime(time);
        if (isSameYear(date)) {
            //同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            if (isSameDay(date)) { //同一天 显示HH:mm
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内 显示n分钟前
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return "今天 "+simpleDateFormat.format(date);
                }
            } else {
                if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                    return "昨天 " + simpleDateFormat.format(date);
                } else if (isSameWeek(date)) {//本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
                    String format = sdf.format(date);
                    String[] s = format.split(" ");
                    String[] split = s[0].split("-");
                    String substring = split[0].substring(0, 1);
                    String danYue = split[0].substring(1, 2);
                    String substring1 = split[1].substring(0, 1);
                    String danDay = split[1].substring(1, 2);
                    String str = "";
                    if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)!=0){
                        str = danYue+"月"+split[1]+"日  "+s[1];
                    }else if(Integer.parseInt(substring) != 0&&Integer.parseInt(substring1)==0){
                        str = split[0]+"月"+danDay+"日  "+s[1];
                    }else if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)==0){
                        str = danYue+"月"+danDay+"日  "+s[1];
                    }else {
                        str = split[0]+"月"+split[1]+"日  "+s[1];
                    }
                    return str;
                }
            }
        } else {
            //不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            String format = sdf.format(date);
            String[] s = format.split(" ");
            String[] split = s[0].split("-");
            String substring = split[1].substring(0, 1);
            String danYue = split[1].substring(1, 2);
            String substring1 = split[2].substring(0, 1);
            String danDay = split[2].substring(1, 2);
            String str = "";
            if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)!=0){
                str = split[0]+"年"+danYue+"月"+split[2]+"日  "+s[1];
            }else if(Integer.parseInt(substring) != 0&&Integer.parseInt(substring1)==0){
                str = split[0]+"年"+split[1]+"月"+danDay+"日  "+s[1];
            }else if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)==0){
                str = split[0]+"年"+danYue+"月"+danDay+"日  "+s[1];
            }else {
                str = split[0]+"年"+split[1]+"月"+split[2]+"日  "+s[1];
            }
            return str;
        }
    }

    public static String QQFormatTimeTwo(long time) {
        Date date = new Date();
        date.setTime(time);
        if (isSameYear(date)) {
            //同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            if (isSameDay(date)) { //同一天 显示HH:mm
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内 显示n分钟前
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return "今天";
                }
            } else {
                if (isYesterday(date)) {//昨天，显示昨天+HH:mm
                    return "昨天";
                } else if (isSameWeek(date)) {//本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {//同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.CHINA);
                    String format = sdf.format(date);
                    String[] split = format.split("-");
                    String substring = split[0].substring(0, 1);
                    String danYue = split[0].substring(1, 2);
                    String substring1 = split[1].substring(0, 1);
                    String danDay = split[1].substring(1, 2);
                    String str = "";
                    if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)!=0){
                        str = danYue+"月"+split[1]+"日";
                    }else if(Integer.parseInt(substring) != 0&&Integer.parseInt(substring1)==0){
                        str = split[0]+"月"+danDay+"日";
                    }else if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)==0){
                        str = danYue+"月"+danDay+"日";
                    }else {
                        str = split[0]+"月"+split[1]+"日";
                    }
                    return str;
                }
            }
        } else {
            //不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            String format = sdf.format(date);
            String[] split = format.split("-");
            String substring = split[1].substring(0, 1);
            String danYue = split[1].substring(1, 2);
            String substring1 = split[2].substring(0, 1);
            String danDay = split[2].substring(1, 2);
            String str = "";
            if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)!=0){
                str = split[0]+"年"+danYue+"月"+split[2]+"日";
            }else if(Integer.parseInt(substring) != 0&&Integer.parseInt(substring1)==0){
                str = split[0]+"年"+split[1]+"月"+danDay+"日";
            }else if(Integer.parseInt(substring) == 0&&Integer.parseInt(substring1)==0){
                str = split[0]+"年"+danYue+"月"+danDay+"日";
            }else {
                str = split[0]+"年"+split[1]+"月"+split[2]+"日";
            }
            return str;
        }
    }

    /**
     * 是否是当前时间的昨天
     * 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Date yesterday = getNextDay(date, 1);
        return isSameDay(yesterday);
    }

    /**
     * 几分钟前
     *
     * @param time
     * @return
     */
    public static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    /**
     * 与当前时间是否在同一周
     * 先判断是否在同一年，然后根据Calendar.DAY_OF_YEAR判断所得的周数是否一致
     *
     * @param date
     * @return
     */
    public static boolean isSameWeek(Date date) {
        if (isSameYear(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int a = calendar.get(Calendar.DAY_OF_YEAR);
            Date now = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(now);
            int b = calendar1.get(Calendar.DAY_OF_WEEK);
            return a == b;
        } else {
            return false;
        }
    }

    /**
     * 判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        return isEquals(date, "yyyy-MM-dd");
    }

    /**
     * 判断与当前日期是否是同一月
     *
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        return isEquals(date, "yyyy-MM");
    }

    /**
     * 判断与当前日期是否是同一年
     *
     * @param date
     * @return
     */
    public static boolean isSameYear(Date date) {
        return isEquals(date, "yyyy");
    }

    /**
     * 格式化Date，判断是否相等
     *
     * @param date
     * @param format
     * @return 是返回true，不是返回false
     */
    private static boolean isEquals(Date date, String format) { //当前时间
        Date now = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sf = new SimpleDateFormat(format); //获取今天的日期
        String nowDay = sf.format(now); //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 获取某个date第n天的日期
     * n<0 表示前n天
     * n=0 表示当天
     * n>1 表示后n天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        date = calendar.getTime();
        return date;
    }

    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }
    public static String getAge(String birthday, String time) {
        SimpleDateFormat formatBirthday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse(birthday);
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime(dateBirthday);
            yearBirthday = calendarBirthday.get(Calendar.YEAR);
            monthBirthday = calendarBirthday.get(Calendar.MONTH);
            dayBirthday = calendarBirthday.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0;
        try {
            Date dateMoment = formatMoment.parse(time);
            calendarMoment.setTime(dateMoment);
            yearMoment = calendarMoment.get(Calendar.YEAR);
            monthMoment = calendarMoment.get(Calendar.MONTH);
            dayMoment = calendarMoment.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {
            /*tvAgeYear.setText("??年");
            tvAgeMonth.setText("??月");
            tvAgeDay.setText("??天");*/
        } else {
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            if (dayAge < 0) {
                monthAge -= 1;
                calendarMoment.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数
                dayAge = dayAge + calendarMoment.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            if (monthAge < 0) {
                monthAge = (monthAge + 12) % 12;
                yearAge--;
            }
            String year, month, day;
            /*if (yearAge < 10) {
                year = "0" + yearAge + "岁";
            } else {
                year = yearAge + "岁";
            }*/
            year = yearAge + "岁";
            /*if (monthAge < 10) {
                month = "0" + monthAge + "个月";
            } else {
                month = monthAge + "个月";
            }*/
            month = monthAge + "个月";
            /*if (dayAge < 10) {
                day = "0" + dayAge + "天";
            } else {
                day = dayAge + "天";
            }*/
            day = dayAge + "天";
            /*tvAgeYear.setText(year);
            tvAgeMonth.setText(month);
            tvAgeDay.setText(day);*/
            if(yearAge==0&&monthAge!=0&&dayAge!=0){
                return month+day;
            }else if(yearAge==0&&monthAge!=0&&dayAge==0){
                return month;
            }if(yearAge==0&&monthAge==0&&dayAge!=0){
                return day;
            }else if(yearAge!=0&&monthAge==0&&dayAge!=0){
                return year+day;
            }else if(yearAge!=0&&monthAge!=0&&dayAge==0){
                return year+month;
            }else if(yearAge!=0&&monthAge==0&&dayAge==0){
                return year;
            }else if(yearAge==0&&monthAge==0&&dayAge==0){
                return day;
            }else {
                return year+month+day;
            }
        }
        return "";
    }
    public static String getAgeTwo(String birthday, String time) {
        SimpleDateFormat formatBirthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse(birthday);
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime(dateBirthday);
            yearBirthday = calendarBirthday.get(Calendar.YEAR);
            monthBirthday = calendarBirthday.get(Calendar.MONTH);
            dayBirthday = calendarBirthday.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0;
        try {
            Date dateMoment = formatMoment.parse(time);
            calendarMoment.setTime(dateMoment);
            yearMoment = calendarMoment.get(Calendar.YEAR);
            monthMoment = calendarMoment.get(Calendar.MONTH);
            dayMoment = calendarMoment.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {
            /*tvAgeYear.setText("??年");
            tvAgeMonth.setText("??月");
            tvAgeDay.setText("??天");*/
        } else {
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            if (dayAge < 0) {
                monthAge -= 1;
                calendarMoment.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数
                dayAge = dayAge + calendarMoment.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            if (monthAge < 0) {
                monthAge = (monthAge + 12) % 12;
                yearAge--;
            }
            String year, month, day;
            /*if (yearAge < 10) {
                year = "0" + yearAge + "岁";
            } else {
                year = yearAge + "岁";
            }*/
            year = yearAge + "岁";
            /*if (monthAge < 10) {
                month = "0" + monthAge + "个月";
            } else {
                month = monthAge + "个月";
            }*/
            month = monthAge + "个月";
            /*if (dayAge < 10) {
                day = "0" + dayAge + "天";
            } else {
                day = dayAge + "天";
            }*/
            day = dayAge + "天";
            /*tvAgeYear.setText(year);
            tvAgeMonth.setText(month);
            tvAgeDay.setText(day);*/
            if(yearAge==0&&monthAge!=0){
                return month+day;
            }else if(yearAge== 0&&monthAge==0){
                return day;
            }else {
                return year+month+day;
            }
        }
        return "";
    }
    /*获取星期几*/
    public static int getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                return 0;
        }
    }
    public static int getDayAge(String birthday, String time) {
        SimpleDateFormat formatBirthday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse(birthday);
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime(dateBirthday);
            yearBirthday = calendarBirthday.get(Calendar.YEAR);
            monthBirthday = calendarBirthday.get(Calendar.MONTH);
            dayBirthday = calendarBirthday.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0;
        try {
            Date dateMoment = formatMoment.parse(time);
            calendarMoment.setTime(dateMoment);
            yearMoment = calendarMoment.get(Calendar.YEAR);
            monthMoment = calendarMoment.get(Calendar.MONTH);
            dayMoment = calendarMoment.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {
            /*tvAgeYear.setText("??年");
            tvAgeMonth.setText("??月");
            tvAgeDay.setText("??天");*/
        } else {
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            if (dayAge < 0) {
                monthAge -= 1;
                calendarMoment.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数
                dayAge = dayAge + calendarMoment.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            if (monthAge < 0) {
                monthAge = (monthAge + 12) % 12;
                yearAge--;
            }

            return dayAge;
        }
        return 0;
    }
    public static int getMonth(String birthday, String time) {
        SimpleDateFormat formatBirthday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int yearBirthday = 0, monthBirthday = 0, dayBirthday = 0;
        try {
            Date dateBirthday = formatBirthday.parse(birthday);
            Calendar calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTime(dateBirthday);
            yearBirthday = calendarBirthday.get(Calendar.YEAR);
            monthBirthday = calendarBirthday.get(Calendar.MONTH);
            dayBirthday = calendarBirthday.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatMoment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendarMoment = Calendar.getInstance();
        int yearMoment = 0, monthMoment = 0, dayMoment = 0;
        try {
            Date dateMoment = formatMoment.parse(time);
            calendarMoment.setTime(dateMoment);
            yearMoment = calendarMoment.get(Calendar.YEAR);
            monthMoment = calendarMoment.get(Calendar.MONTH);
            dayMoment = calendarMoment.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (yearBirthday > yearMoment || yearBirthday == yearMoment && monthBirthday > monthMoment || yearBirthday == yearMoment && monthBirthday == monthMoment && dayBirthday > dayMoment) {

        } else {
            int yearAge = yearMoment - yearBirthday;
            int monthAge = monthMoment - monthBirthday;
            int dayAge = dayMoment - dayBirthday;
            //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减
            if (dayAge < 0) {
                monthAge -= 1;
                calendarMoment.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数
                dayAge = dayAge + calendarMoment.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            if (monthAge < 0) {
                monthAge = (monthAge + 12) % 12;
                yearAge--;
            }
            if(yearAge>0){
                monthAge = yearAge*12 +monthAge;
            }
            return monthAge;

        }
        return 0;
    }
}
