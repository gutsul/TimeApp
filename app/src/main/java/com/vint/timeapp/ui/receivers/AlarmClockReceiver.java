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
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.ui.activities.AlarmActivity;

public class AlarmClockReceiver extends WakefulBroadcastReceiver {
    public static final String REPEAT = "REPEAT";
    public static final String TIME = "TIME";
    public static final String MESSAGE = "MESSAGE";
    public static final String ID = "ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        int id = extras.getInt(ID);
        long time = extras.getLong(TIME);
        String message = extras.getString(MESSAGE);


        Log.d("AlarmReceiver", "Alarm RUN " + "ID: " + id + " " + time + " message: "+ message +" | current: " + System.currentTimeMillis());

//        String title = context.getString(R.string.title_alarm_clock);
//        String content = String.format("%s %s", TimeUtils.timeIn24HourFormat(time), message);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true).
//                setDefaults(Notification.DEFAULT_ALL).
//                setWhen(System.currentTimeMillis()).
//                setSmallIcon(R.drawable.ic_bell_24dp).
//                setContentTitle(title).setContentText(content).
//                setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND).
//                setContentInfo("Info");
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1,builder.build());


//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(500);

        Intent i = new Intent(context, AlarmActivity.class);

        i.putExtras(extras);
//        i.putExtra(TIME, time);
//        i.putExtra(MESSAGE, message);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

    public void setAlarm(Context context, AlarmClock alarm) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmClockReceiver.class);
        intent.putExtra(ID, alarm.getId());
        intent.putExtra(TIME, alarm.getTime());
        intent.putExtra(REPEAT, alarm.isRepeat());
        intent.putExtra(MESSAGE, alarm.getMessage());

        Log.d("AlarmReceiver", "Alarm set " + "ID: " + alarm.getId() + " " + alarm.getTime() + " | current: " + System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarm.isRepeat()){
            manager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), pendingIntent);
        }
    }

    public void cancelAlarm(Context context, AlarmClock alarm) {
        Intent intent = new Intent(context, AlarmClockReceiver.class);


        intent.putExtra(ID, alarm.getId());
        intent.putExtra(TIME, alarm.getTime());
        intent.putExtra(REPEAT, alarm.isRepeat());
        intent.putExtra(MESSAGE, alarm.getMessage());

        Log.d("AlarmReceiver", "Alarm cancel " + "ID: " + alarm.getId() + " " + alarm.getTime() + " | current: " + System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

}