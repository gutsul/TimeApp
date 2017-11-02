package com.vint.timeapp.ui.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.vint.timeapp.R;
import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.presenter.AlarmClockPresenter;
import com.vint.timeapp.ui.adapters.AlarmClockAdapter;
import com.vint.timeapp.view.AlarmClockView;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vint.timeapp.utils.TimeUtils.timeInMilliseconds;

public class AlarmClockFragment extends BaseFragment implements AlarmClockView, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.empty)
    LinearLayout empty;

    private AlarmClockAdapter adapter;
    private AlarmClockPresenter presenter;
    private long time;

    public AlarmClockFragment() {
        this.presenter = new AlarmClockPresenter();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_alarm_clock;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.bind(this);
        presenter.initList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
    }

    public static AlarmClockFragment newInstance(){
        AlarmClockFragment fragment = new AlarmClockFragment();
        return fragment;
    }

    @Override
    public void initList(List<AlarmClock> alarmClocks) {
        adapter = new AlarmClockAdapter(alarmClocks);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }

    @Override
    public void showEmptyResult() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyResult() {
        empty.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.add_alarm)
    public void addAlarmClock(){
        showTimePicker();
    }

    private void showTimePicker(){
        DialogFragment timePicker = TimePickerFragment.newInstance(this);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String timeIn24format = String.format("%02d:%02d", hourOfDay, minute);
        Log.d("AlarmClock", "TimePicker value " + timeIn24format);

        long time = 0;
        try {
            time = timeInMilliseconds(timeIn24format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        presenter.addAlarmClock(time, null, true);
        adapter.notifyDataSetChanged();
    }
}
