package com.vint.timeapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gutsul on 03.11.17.
 */

public class TimeUtils {

    public static final String TIME_24_FORMAT = "HH:mm";

    public static long timeInMilliseconds(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_24_FORMAT);
        Date mDate = sdf.parse(time);
        long timeInMilliseconds = mDate.getTime();
        return timeInMilliseconds;
    }

    public static String timeIn24HourFormat(long timeInMilliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_24_FORMAT);
        String timeIn24HourFormat = sdf.format(new Date(timeInMilliseconds));
        return timeIn24HourFormat;
    }

}
