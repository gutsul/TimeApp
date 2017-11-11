package com.vint.timeapp.view;

import com.vint.timeapp.models.AlarmClock;

import java.util.List;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public interface CalendarView extends BaseView {

    void showEmptyResult();

    void setDay(String dayOfMonth, String dayOfWeek);

    void setTime(String time);

    void lock();

    void unlock();

    void saveReminder();

    void cancelReminder();

}
