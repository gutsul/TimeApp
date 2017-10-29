package com.vint.timeapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.vint.timeapp.R;
import com.vint.timeapp.presenter.StopWatchPresenter;
import com.vint.timeapp.view.StopWatchView;

import butterknife.BindView;
import butterknife.OnClick;

public class StopWatchFragment extends BaseFragment implements StopWatchView{
    @BindView(R.id.startOrPause)
    FloatingActionButton startOrPause;

    private StopWatchPresenter presenter;

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
    }

    @Override
    public void pause() {
        startOrPause.setImageResource(R.drawable.ic_play_24dp);
    }

    @Override
    public void reset() {

    }

    @Override
    public void update(long timeInMillis) {

    }

    @OnClick(R.id.startOrPause)
    void trigger(){
        presenter.trigger();
    }
}
