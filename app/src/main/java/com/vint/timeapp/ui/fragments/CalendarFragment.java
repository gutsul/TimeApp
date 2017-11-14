package com.vint.timeapp.ui.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.presenter.CalendarPresenter;
import com.vint.timeapp.ui.receivers.AlarmClockReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class CalendarFragment extends BaseFragment implements com.vint.timeapp.view.CalendarView, CalendarView.OnDateChangeListener, TimePickerDialog.OnTimeSetListener , View.OnClickListener, TextWatcher{
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
    private AlarmClockReceiver alarmReceiver;

    public CalendarFragment() {
        presenter = new CalendarPresenter();
        alarmReceiver = new AlarmClockReceiver();
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
        etReminder.addTextChangedListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        presenter.onSelectedDayChange(year, month, day);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
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
//        etReminder.setFocusable(false);
    }

    @Override
    public void unlock() {
        Log.d("AlarmClock", "UnLocked");
        llAlarmClock.setOnClickListener(this);
//        etReminder.setFocusable(true);
    }

    @Override
    public void setMessage(String message) {
        etReminder.setText(message);
    }

    @Override
    public void showActions() {
        btnSave.setEnabled(true);
        btnClean.setEnabled(true);
    }

    @Override
    public void hideActions() {
        btnSave.setEnabled(false);
        btnClean.setEnabled(false);
    }

    @OnClick(R.id.save)
    void save(){
        presenter.save();
    }

    @Override
    public void saveReminder(AlarmClock alarm) {
        alarmReceiver.setAlarm(getContext(), alarm);
    }

    @OnClick(R.id.clean)
    void clean(){
        presenter.clean();
    }

    @Override
    public void cancelReminder(AlarmClock alarm) {
        alarmReceiver.cancelAlarm(getContext(), alarm);
    }

    @Override
    public void onClick(View view) {
        DialogFragment timePicker = TimePickerFragment.newInstance(this);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        presenter.setMessage(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
