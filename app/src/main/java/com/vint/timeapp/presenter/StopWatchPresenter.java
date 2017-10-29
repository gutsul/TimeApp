package com.vint.timeapp.presenter;


import android.os.SystemClock;
import android.util.Log;

import com.vint.timeapp.view.StopWatchView;

/**
 * Created by ygrigortsevich on 08.03.17.
 */

public class StopWatchPresenter extends BasePresenter<StopWatchView> {

    private State state = State.RESETED;
    private long startTime, pauseTime, saveTime = 0L;


    public void trigger(){
        if (state == State.RESETED || state == State.PAUSED){
            getView().start();
        } else {
            getView().pause();
        }
    }

    public void start(){
        state = State.STARTED;
        startTime = SystemClock.uptimeMillis();
        Log.d("StopWatch", "state: " + state.toString());
    }

    public void pause() {
        state = State.PAUSED;
        pauseTime = SystemClock.uptimeMillis();
        Log.d("StopWatch", "state: " + state.toString());
    }

    public void reset() {
        state = State.RESETED;
        startTime = 0L;
        pauseTime = 0L;
        saveTime = 0L;
        Log.d("StopWatch", "state: " + state.toString());
    }

    public String getElapsedTime() {
        long time = 0L;

        switch (state){
            case STARTED:
                time = SystemClock.uptimeMillis() - startTime + saveTime;
                break;
            case PAUSED:
                time = pauseTime - startTime;
                saveTime += time;
                time  = saveTime;
                break;

            case RESETED:
                time = 0;
                break;
        }


        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int milliseconds = (int) (time % 100);

        String formated = String.format("%d:%02d:%02d", minutes, seconds, milliseconds);
        return formated;
    }

}

enum State {
    PAUSED, STARTED, RESETED
}