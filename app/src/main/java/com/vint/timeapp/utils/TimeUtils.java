package com.vint.timeapp.utils;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by gutsul on 03.11.17.
 */

public class TimeUtils {

    public static long timeInMillis(int hour, int minutes) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static String timeIn24HourFormat(long timeInMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        String timeIn24HourFormat = String.format("%02d:%02d", hour, minutes);
        return timeIn24HourFormat;
    }

}
