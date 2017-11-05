package com.vint.timeapp.presenter;

import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.view.AlarmClockView;

import java.util.List;
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

    public void addAlarmClock(long time, String message){

        Number currentId = realm.where(AlarmClock.class).max("id");
        int nextId;
        if(currentId == null) {
            nextId = 1;
        } else {
            nextId = currentId.intValue() + 1;
        }

        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setId(nextId);
        alarmClock.setTime(time);
        alarmClock.setMessage(message);
        alarmClock.setEnable(true);
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
