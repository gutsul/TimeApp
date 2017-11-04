package com.vint.timeapp.ui.receivers;

/**
 * Created by gutsul on 04.11.17.
 */

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vint.timeapp.R;
import com.vint.timeapp.utils.TimeUtils;

public class AlarmClockReceiver extends BroadcastReceiver {
    final public static String REPEAT = "REPEAT";
    final public static String TIME = "TIME";
    final public static String MESSAGE = "MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long time = extras.getLong(TIME);
        String message = extras.getString(MESSAGE);

        String title = context.getString(R.string.title_alarm_clock);
        String content = String.format("%s %s", TimeUtils.timeIn24HourFormat(time), message);

        Log.d("AlarmReceiver", "Alarm RUN: " + time + " | current: " + System.currentTimeMillis());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true).
                setDefaults(Notification.DEFAULT_ALL).
                setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.ic_bell_24dp).
                setContentTitle(title).setContentText(content).
                setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND).
                setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());


        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

    }

    public void setAlarm(Context context, long time, boolean repeat, String message) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmClockReceiver.class);
        intent.putExtra(TIME, time);
        intent.putExtra(REPEAT, repeat);
        intent.putExtra(MESSAGE, message);

        Log.d("AlarmReceiver", "Alarm set at: " + time + " | current: " + System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(repeat){
            manager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmClockReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);//Отменяем будильник, связанный с интентом данного класса
    }

}