package com.vint.timeapp.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.vint.timeapp.R;
import com.vint.timeapp.presenter.StopWatchPresenter;
import com.vint.timeapp.view.StopWatchView;

import butterknife.BindView;
import butterknife.OnClick;

public class StopWatchFragment extends BaseFragment implements StopWatchView{
    @BindView(R.id.startOrPause)
    FloatingActionButton startOrPause;

    @BindView(R.id.stopwatch)
    TextView stopwatch;

    final int MSG_START_TIMER = 0;
    final int MSG_PAUSE_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int MSG_RESET_TIMER = 3;
    final int REFRESH_RATE = 70;

    private StopWatchPresenter presenter;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;
                case MSG_UPDATE_TIMER:
                    stopwatch.setText(presenter.getElapsedTime());
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running
                case MSG_PAUSE_TIMER:
//                    mHandler.removeMessages(MSG_UPDATE_TIMER);
                    stopwatch.setText(presenter.getElapsedTime());
                    break;
                case MSG_RESET_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER);
                    stopwatch.setText(presenter.getElapsedTime());
                    break;

                default:
                    break;
            }
        }
    };

    public StopWatchFragment() {
        this.presenter = new StopWatchPresenter();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_stopwatch;
    }

    public static StopWatchFragment newInstance(){
        StopWatchFragment fragment = new StopWatchFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bind(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
    }

    @Override
    public void start() {
        startOrPause.setImageResource(R.drawable.ic_pause_24dp);
        presenter.start();
        mHandler.sendEmptyMessage(MSG_START_TIMER);
    }

    @Override
    public void pause() {
        startOrPause.setImageResource(R.drawable.ic_play_24dp);
        presenter.pause();
        mHandler.sendEmptyMessage(MSG_PAUSE_TIMER);
    }

    @OnClick(R.id.reset)
    @Override
    public void reset() {
        startOrPause.setImageResource(R.drawable.ic_play_24dp);
        presenter.reset();
        mHandler.sendEmptyMessage(MSG_RESET_TIMER);
    }

    @OnClick(R.id.startOrPause)
    void trigger(){
        presenter.trigger();
    }
}
