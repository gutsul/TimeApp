package com.vint.timeapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.vint.timeapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;

public class CalendarFragment extends BaseFragment implements CalendarView.OnDateChangeListener {
    @BindView(R.id.calendar)
    CalendarView vCalendar;
    @BindView(R.id.day_of_month)
    TextView tvDayOfMonth;
    @BindView(R.id.day_of_week)
    TextView tvDayOfWeek;

    private long date;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_calendar;
    }

    public static CalendarFragment newInstance(){
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vCalendar.setOnDateChangeListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int day = calendar.get(Calendar.DATE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String dayOfWeek = dateFormat.format(calendar.getTime());

        tvDayOfMonth.setText(""+ day);
        tvDayOfWeek.setText(dayOfWeek);

    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
        String message = String.format("%d-%d-%d", year, month, day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String dayOfWeek = dateFormat.format(calendar.getTime());

        tvDayOfMonth.setText(""+ day);
        tvDayOfWeek.setText(dayOfWeek);

    }
}
