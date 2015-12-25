package com.smartalk.gank.utils;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by panl on 15/12/25.
 */
public class DateUtil {
    public static String toDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day;
    }
}
