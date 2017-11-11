package com.vint.timeapp.ui.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vint.timeapp.R;
import com.vint.timeapp.presenter.CalendarPresenter;

import java.util.Calendar;

import butterknife.BindView;

public class CalendarFragment extends BaseFragment implements com.vint.timeapp.view.CalendarView, CalendarView.OnDateChangeListener, TimePickerDialog.OnTimeSetListener , View.OnClickListener{
    @BindView(R.id.calendar)
    CalendarView vCalendar;
    @BindView(R.id.day_of_month)
    TextView tvDayOfMonth;
    @BindView(R.id.day_of_week)
    TextView tvDayOfWeek;
    @BindView(R.id.alarm_clock)
    TextView tvTime;
    @BindView(R.id.reminder)
    EditText etReminder;
    @BindView(R.id.alarm_clock_layout)
    LinearLayout llAlarmClock;
    @BindView(R.id.save)
    Button btnSave;
    @BindView(R.id.clean)
    Button btnClean;

    private CalendarPresenter presenter;

    public CalendarFragment() {
        presenter = new CalendarPresenter();
    }

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
        presenter.bind(this);
        vCalendar.setOnDateChangeListener(this);
        llAlarmClock.setOnClickListener(this);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        presenter.onSelectedDayChange(year, month, day);
        setTime(null);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
        presenter.onSelectedDayChange(year, month, day);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        presenter.onSelectedTimeChange(hour, minute);
    }

    @Override
    public void setDay(String dayOfMonth, String dayOfWeek) {
        tvDayOfMonth.setText(dayOfMonth);
        tvDayOfWeek.setText(dayOfWeek);
    }

    @Override
    public void setTime(String time) {
        if (time == null || time.equals("")){
            time = getContext().getString(R.string.none);
        }
        tvTime.setText(time);
    }

    @Override
    public void lock() {
        Log.d("AlarmClock", "Locked");
        llAlarmClock.setOnClickListener(null);
    }

    @Override
    public void unlock() {
        Log.d("AlarmClock", "UnLocked");
        llAlarmClock.setOnClickListener(this);
    }

    @Override
    public void setMessage(String message) {
        etReminder.setText(message);
    }

    @Override
    public void showActions() {
        btnSave.setVisibility(View.VISIBLE);
        btnClean.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideActions() {
        btnSave.setVisibility(View.GONE);
        btnClean.setVisibility(View.GONE);
    }

    @Override
    public void saveReminder() {

    }

    @Override
    public void cancelReminder() {

    }

    @Override
    public void onClick(View view) {
        DialogFragment timePicker = TimePickerFragment.newInstance(this);
        timePicker.show(getFragmentManager(), "timePicker");
    }
}
