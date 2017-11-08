package com.vint.timeapp.ui.fragments;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.vint.timeapp.R;
import com.vint.timeapp.models.AlarmClock;
import com.vint.timeapp.presenter.AlarmClockPresenter;
import com.vint.timeapp.ui.adapters.AlarmClockAdapter;
import com.vint.timeapp.ui.helpers.RecyclerItemTouchHelper;
import com.vint.timeapp.ui.receivers.AlarmClockReceiver;
import com.vint.timeapp.view.AlarmClockView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.widget.DividerItemDecoration.*;
import static com.vint.timeapp.utils.TimeUtils.timeInMillis;

public class AlarmClockFragment extends BaseFragment implements AlarmClockView, TimePickerDialog.OnTimeSetListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.empty)
    LinearLayout empty;

    private AlarmClockAdapter adapter;
    private AlarmClockPresenter presenter;

    private AlarmClockReceiver alarmReceiver;

    public AlarmClockFragment() {
        this.presenter = new AlarmClockPresenter();
        alarmReceiver = new AlarmClockReceiver();
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
        adapter = new AlarmClockAdapter(alarmClocks, getContext());
        adapter.setCallback(new AlarmClockAdapter.Callback() {
            @Override
            public void onChangeState(AlarmClock alarm) {
                if(alarm.isEnable()){
                    presenter.disableAlarm(alarm);
                } else {
                    presenter.enableAlarm(alarm);
                }
            }

            @Override
            public void onChangeMessage(int position) {
                showChangeMessageDialog(position);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, (RecyclerItemTouchHelper.RecyclerItemTouchHelperListener) this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(list);
    }


    public void showChangeMessageDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit_message);

        dialogBuilder.setMessage(getString(R.string.title_message));
        dialogBuilder.setPositiveButton(getString(R.string.action_done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String message = edt.getText().toString();
                presenter.changeAlarmMessage(message, position);
                adapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
    public void addAlarm(){
        showTimePicker();
    }

    @Override
    public void enableAlarm(AlarmClock alarm) {
        alarmReceiver.setAlarm(getContext(), alarm);
    }

    @Override
    public void dilableAlarm(AlarmClock alarm) {
        alarmReceiver.cancelAlarm(getContext(), alarm);
    }

    @Override
    public void removeAlarm(AlarmClock alarm, int position) {
        adapter.notifyItemRemoved(position);
    }

    private void showTimePicker(){
        DialogFragment timePicker = TimePickerFragment.newInstance(this);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String timeIn24format = String.format("%02d:%02d", hourOfDay, minute);
        long time = timeInMillis(hourOfDay, minute);

        Log.d("AlarmClock", "TimePicker value " + timeIn24format);
        Log.d("AlarmClock", "TimePicker long value " + time);

        presenter.addAlarmClock(time, null);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AlarmClockAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
//            String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
//            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());

//            final int deletedIndex = viewHolder.getAdapterPosition();
            Log.d("AlarmClock", "Swiped index: " + position);
            presenter.removeAlarm(position);

//            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {

            // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();

        }
    }
}
