package com.vint.timeapp.presenter;

import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.view.AlarmClockView;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by gutsul on 01.11.17.
 */

public class AlarmClockPresenter extends BasePresenter<AlarmClockView> {

    private Realm realm = Realm.getDefaultInstance();
    private List<AlarmClock> alarmClocks;

    public void initList(){
        alarmClocks = realm.where(AlarmClock.class).findAll().sort("time");

        getView().initList(alarmClocks);

        if (alarmClocks.isEmpty()){
            getView().showEmptyResult();
        }
    }

    public void addAlarmClock(long time, String message, boolean isEnable){
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setId(UUID.randomUUID().toString());
        alarmClock.setTime(time);
        alarmClock.setMessage(message);
        alarmClock.setEnable(isEnable);
        alarmClock.setRepeat(true);

        realm.beginTransaction();
        realm.copyToRealm(alarmClock);
        realm.commitTransaction();

        getView().hideEmptyResult();
        getView().enableAlarm(alarmClock);
    }

    public void enableAlarm(AlarmClock alarm){
        realm.beginTransaction();
        alarm.setEnable(true);
        realm.commitTransaction();

        getView().enableAlarm(alarm);
    }

    public void disableAlarm(AlarmClock alarm){
        realm.beginTransaction();
        alarm.setEnable(false);
        realm.commitTransaction();
    }


    @Override
    public void unbind() {
        super.unbind();
        realm.close();
    }
}
