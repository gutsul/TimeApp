package com.vint.timeapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.vint.timeapp.R;
import com.vint.timeapp.models.AlarmClock;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vint.timeapp.utils.TimeUtils.timeIn24HourFormat;

/**
 * Created by ygrigortsevich on 06.07.16.
 */


public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.ViewHolder>{

    private List<AlarmClock> alarmClocks;

    public AlarmClockAdapter(List<AlarmClock> alarmClocks) {
        this.alarmClocks = alarmClocks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AlarmClock alarmClock = alarmClocks.get(position);

        long time = alarmClock.getTime();
        String message = alarmClock.getMessage();
        boolean isEnabled = alarmClock.isEnable();

        holder.setAlarmTime(time);
        holder.setAlarmMessage(message);
        holder.enableAlarm(isEnabled);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return alarmClocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.alarm_time)
        TextView alarmTime;
        @BindView(R.id.alarm_message)
        TextView alarmMessage;
        @BindView(R.id.alarm_switch)
        Switch alarmSwitch;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setAlarmTime(long millis){
            String timeIn24HourFormat = timeIn24HourFormat(millis);
            alarmTime.setText(timeIn24HourFormat);
        }

        public void setAlarmMessage(String message){
            alarmMessage.setText(message);
        }

        public void enableAlarm(boolean enable){
            alarmSwitch.setChecked(enable);
        }

    }
}
