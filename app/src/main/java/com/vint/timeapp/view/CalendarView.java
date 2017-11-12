package com.vint.timeapp.view;

import com.vint.timeapp.models.AlarmClock;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public interface CalendarView extends BaseView {

    void setDay(String dayOfMonth, String dayOfWeek);

    void setTime(String time);

    void lock();

    void unlock();

    void setMessage(String message);

    void showActions();

    void hideActions();

    void saveReminder(AlarmClock alarm);

    void cancelReminder(AlarmClock alarm);

}
