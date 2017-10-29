package com.vint.timeapp.presenter;


import com.vint.timeapp.R;
import com.vint.timeapp.view.MainView;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public final int DEFAULT_ITEM = R.id.navigation_stopwatch;

    public void navigateTo(int itemId){
        switch (itemId){
            case R.id.navigation_stopwatch:
                getView().toStopWatch();
                break;

            case R.id.navigation_alarm_clock:
                getView().toAlarmClock();
                break;

            case R.id.navigation_calendar:
                getView().toCalendar();
                break;
        }
    }

}
