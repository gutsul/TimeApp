package com.vint.timeapp.ui.fragments;

import com.vint.timeapp.R;

public class CalendarFragment extends BaseFragment {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_calendar;
    }

    public static CalendarFragment newInstance(){
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

}
