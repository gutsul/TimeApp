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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AlarmClockFragment extends BaseFragment implements AlarmClockView, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.empty)
    LinearLayout empty;

    private AlarmClockAdapter adapter;
    private AlarmClockPresenter presenter;

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
//        adapter.setCallback(new TransportListAdapter.Callback() {
//            @Override
//            public void onFavoriteClick(Transport transport) {
//                String message = String.format("Click on %s #%s", transport.getType(), transport.getNumber());
//                Log.d("TransportListFragment", message);
//            }
//        });

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

    @OnClick(R.id.add_alarm)
    void addAlarmClock(){
        showTimePicker();
    }


    private void showTimePicker(){
        DialogFragment timePicker = TimePickerFragment.newInstance(this);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Log.d("AlarmClock", "TimePicker value " + hourOfDay + ":" + minute);
    }
}
