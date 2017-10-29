package com.vint.timeapp.ui.fragments;

import com.vint.timeapp.R;

public class AlarmClockFragment extends BaseFragment {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_alarm_clock;
    }

    public static AlarmClockFragment newInstance(){
        AlarmClockFragment fragment = new AlarmClockFragment();
        return fragment;
    }

}
