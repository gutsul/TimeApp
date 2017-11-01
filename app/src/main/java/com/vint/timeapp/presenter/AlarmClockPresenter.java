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


    public void initList(){
        List<AlarmClock> alarmClocks = realm.where(AlarmClock.class).findAll();

        if (alarmClocks.isEmpty()){
            getView().showEmptyResult();
        } else {
            getView().initList(alarmClocks);
        }
    }

    @Override
    public void unbind() {
        super.unbind();
        realm.close();
    }
}
