package com.vint.timeapp.ui.fragments;

import com.vint.timeapp.R;

public class StopWatchFragment extends BaseFragment {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_stopwatch;
    }

    public static StopWatchFragment newInstance(){
        StopWatchFragment fragment = new StopWatchFragment();
        return fragment;
    }

}
