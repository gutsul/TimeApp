package com.vint.timeapp.presenter;

import android.util.Log;

import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.utils.TimeUtils;
import com.vint.timeapp.view.AlarmClockView;
import com.vint.timeapp.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

/**
 * Created by gutsul on 01.11.17.
 */

public class CalendarPresenter extends BasePresenter<CalendarView> {

    private long timeInMillis = 0;
    private String message = null;

    private Realm realm = Realm.getDefaultInstance();
    private AlarmClock reminder;

    public void onSelectedDayChange(int year, int month, int day){
        Calendar calendarPrev = Calendar.getInstance();
        calendarPrev.set(Calendar.YEAR, year);
        calendarPrev.set(Calendar.MONTH, month);
        calendarPrev.set(Calendar.DATE, day);
        calendarPrev.set(Calendar.HOUR, 0);
        calendarPrev.set(Calendar.MINUTE, 0);
        calendarPrev.set(Calendar.SECOND, 0);
        calendarPrev.set(Calendar.MILLISECOND, 0);

        long fromDate = calendarPrev.getTimeInMillis();
        Log.d("Calendar", "fromDate:" + calendarPrev.getTime().toString());

        Calendar calendarNext = Calendar.getInstance();
        calendarNext.setTimeInMillis(fromDate);
        calendarNext.add(Calendar.DATE, 1);

        long toDate = calendarNext.getTimeInMillis();
        Log.d("Calendar", "toDate:" + calendarNext.getTime().toString());

        reminder = realm.where(AlarmClock.class)
                .equalTo("repeat", false)
                .equalTo("isEnable", true)
                .greaterThan("time", fromDate)
                .lessThan("time", toDate)
                .findFirst();

        String time = null;

        if (reminder == null){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, day);
            calendar.add(Calendar.MINUTE, 1);
            timeInMillis = calendar.getTimeInMillis();
        } else {
            timeInMillis = reminder.getTime();
            time = TimeUtils.timeIn24HourFormat(timeInMillis);
            message = reminder.getMessage();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String dayOfMonth = Integer.toString(day);
        String dayOfWeek = dateFormat.format(calendarPrev.getTime());

        getView().setDay(dayOfMonth, dayOfWeek);

        if(TimeUtils.isFuture(timeInMillis)){
            getView().unlock();
        } else {
            getView().lock();
            getView().hideActions();
        }

        getView().setMessage(message);
        getView().setTime(time);

    }

    public void onSelectedTimeChange(int hour, int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timeInMillis = calendar.getTimeInMillis();

        String timeIn24HourFormat = String.format("%02d:%02d", hour, minutes);
        getView().setTime(timeIn24HourFormat);
        getView().showActions();
    }

    public void setMessage(String message) {
        this.message = message;
        getView().setMessage(message);
    }

//    public void addAlarmClock(long time, String message){
//
//        Number currentId = realm.where(AlarmClock.class).max("id");
//        int nextId;
//        if(currentId == null) {
//            nextId = 1;
//        } else {
//            nextId = currentId.intValue() + 1;
//        }
//
//        AlarmClock alarmClock = new AlarmClock();
//        alarmClock.setId(nextId);
//        alarmClock.setTime(time);
//        alarmClock.setMessage(message);
//        alarmClock.setEnable(true);
//        alarmClock.setRepeat(true);
//
//        realm.beginTransaction();
//        realm.copyToRealm(alarmClock);
//        realm.commitTransaction();
//
//        getView().hideEmptyResult();
//        getView().enableAlarm(alarmClock);
//    }
//
//    public void enableAlarm(AlarmClock alarm){
//        long nextTime = TimeUtils.getNextTime(alarm.getTime());
//
//        realm.beginTransaction();
//        alarm.setEnable(true);
//        alarm.setTime(nextTime);
//        realm.commitTransaction();
//
//        getView().enableAlarm(alarm);
//    }
//
//    public void disableAlarm(AlarmClock alarm){
//        realm.beginTransaction();
//        alarm.setEnable(false);
//        realm.commitTransaction();
//
//        getView().dilableAlarm(alarm);
//    }
//
//    public void removeAlarm(int position){
//
//        realm.beginTransaction();
//
//        AlarmClock alarm = reminder.get(position);
//        getView().dilableAlarm(alarm);
//
//        alarm.deleteFromRealm();
//
//        realm.commitTransaction();
//
//        Log.d("Removed", "Size: "+ reminder.size());
//        getView().removeAlarm(alarm, position);
//
//        if (reminder.isEmpty()){
//            getView().showEmptyResult();
//        }
//    }
//
//
//    public void changeAlarmMessage(String message, int position){
//        realm.beginTransaction();
//        AlarmClock alarm = reminder.get(position);
//        alarm.setMessage(message);
//        realm.commitTransaction();
//
//        if (alarm.isEnable()){
//            enableAlarm(alarm);
//        }
//    }

    @Override
    public void unbind() {
        super.unbind();
        realm.close();
    }
}
