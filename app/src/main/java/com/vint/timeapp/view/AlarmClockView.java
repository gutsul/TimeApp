package com.vint.timeapp.view;

import com.vint.timeapp.models.AlarmClock;

import java.util.List;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public interface AlarmClockView extends BaseView {

    void initList(List<AlarmClock> alarmClocks);

    void showEmptyResult();

    void hideEmptyResult();

    void addAlarm();

    void enableAlarm(AlarmClock alarm);

    void dilableAlarm(AlarmClock alarm);

    void removeAlarm(AlarmClock alarm);


}
