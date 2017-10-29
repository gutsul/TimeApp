package com.vint.timeapp.presenter;


import android.os.SystemClock;

import com.vint.timeapp.view.StopWatchView;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public class StopWatchPresenter extends BasePresenter<StopWatchView> {

    State state = State.RESETED;


    private long MillisecondTime, startTime, TimeBuff, UpdateTime = 0L ;


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - startTime;

            UpdateTime = TimeBuff + MillisecondTime;

            int seconds = (int) (UpdateTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            MillisecondTime = (int) (UpdateTime % 1000);

//            textView.setText("" + minutes + ":"
//                    + String.format("%02d", seconds) + ":"
//                    + String.format("%03d", MillisecondTime));

//            handler.postDelayed(this, 0);
        }

    };

    public void trigger(){
        if (state == State.RESETED || state == State.PAUSED){
            getView().start();
            state = State.STARTED;
        } else {
            getView().pause();
            state = State.PAUSED;
        }
    }

    public void start(){
        startTime = SystemClock.uptimeMillis();
    }


}

enum State {
    PAUSED, STARTED, RESETED
}