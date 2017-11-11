package com.vint.timeapp.view;

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

    void saveReminder();

    void cancelReminder();

}
