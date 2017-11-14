package com.vint.timeapp.presenter;

import android.util.Log;

import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.utils.TimeUtils;
import com.vint.timeapp.view.AlarmClockView;
import com.vint.timeapp.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by gutsul on 01.11.17.
 */

public class CalendarPresenter extends BasePresenter<CalendarView> {

    private long timeInMillis = 0;
//    private int year = 0, month = 0, day = 0, hour = 0, minute = 0;

    private String message = null;

    private Realm realm = Realm.getDefaultInstance();
    private AlarmClock reminder;

    public void onSelectedDayChange(int year, int month, int day){
        Calendar calendarPrev = Calendar.getInstance();
        calendarPrev.set(Calendar.YEAR, year);
        calendarPrev.set(Calendar.MONTH, month);
        calendarPrev.set(Calendar.DATE, day);
        calendarPrev.set(Calendar.HOUR_OF_DAY, 0);
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
            Log.d("Calendar", "Reminder null Date:" + calendar.getTime().toString());
            timeInMillis = calendar.getTimeInMillis();
            message = null;

            getView().hideActions();
        } else {
            timeInMillis = reminder.getTime();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeInMillis);
            Log.d("Calendar", "Reminder Not null Date:" + calendar.getTime().toString());

            time = TimeUtils.timeIn24HourFormat(timeInMillis);
            message = reminder.getMessage();
//            getView().showActions();
        }

        Locale locale = Locale.getDefault();
        Log.d("Calendar", "Language: " + locale.getLanguage());
        if (locale.getLanguage().equals("ru")){
//            Вчіть мову :)
            Locale.setDefault(new Locale("uk"));
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

        Log.d("Calendar", "onSelectedTimeChange before" + calendar.getTime().toString());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Log.d("Calendar", "Hour: " + hour + " Minutes: " + minutes);
        Log.d("Calendar", "onSelectedTimeChange after" + calendar.getTime().toString());

        timeInMillis = calendar.getTimeInMillis();

        String timeIn24HourFormat = String.format("%02d:%02d", hour, minutes);
        getView().setTime(timeIn24HourFormat);
        getView().showActions();
    }

    public void setMessage(String message) {
        this.message = message;
        Log.d("Calendar", "Message: " + message);
//        getView().showActions();
    }


    public void save(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Log.d("Calendar", "Save Date:" + calendar.getTime().toString());

        realm.beginTransaction();
        if (reminder == null){
            Number currentId = realm.where(AlarmClock.class).max("id");
            int nextId;
            if(currentId == null) {
                nextId = 1;
            } else {
                nextId = currentId.intValue() + 1;
            }

            AlarmClock alarmClock = new AlarmClock();
            alarmClock.setId(nextId);
            alarmClock.setTime(timeInMillis);
            alarmClock.setMessage(message);
            alarmClock.setEnable(true);
            alarmClock.setRepeat(false);

            realm.copyToRealm(alarmClock);
            reminder = alarmClock;

        } else {
            reminder.setEnable(true);
            reminder.setTime(timeInMillis);
            reminder.setMessage(message);
        }
        realm.commitTransaction();

        getView().hideActions();
        getView().saveReminder(reminder);
    }

    public void clean() {
        message = null;
        timeInMillis = 0;

        if (reminder != null){
            getView().cancelReminder(reminder);
            realm.beginTransaction();
            reminder.deleteFromRealm();
            realm.commitTransaction();
        }

        getView().hideActions();
        getView().setMessage(message);
        getView().setTime(null);
    }

    @Override
    public void unbind() {
        super.unbind();
        realm.close();
    }
}
