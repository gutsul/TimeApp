package com.vint.timeapp.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.vint.timeapp.R;
import com.vint.timeapp.models.AlarmClock;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vint.timeapp.utils.TimeUtils.timeIn24HourFormat;

/**
 * Created by ygrigortsevich on 06.07.16.
 */


public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.ViewHolder>{

    private List<AlarmClock> alarmClocks;
    private Callback callback;
    private Context context;


    public interface Callback {
        void onChangeState(AlarmClock alarm);
        void onChangeMessage(int position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public AlarmClockAdapter(List<AlarmClock> alarmClocks, Context context) {
        this.alarmClocks = alarmClocks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlarmClock alarmClock = alarmClocks.get(position);

        long time = alarmClock.getTime();
        String message = alarmClock.getMessage();
        final boolean isEnabled = alarmClock.isEnable();

        holder.setAlarmTime(time);
        holder.setAlarmMessage(message);
        holder.enableAlarm(isEnabled);

        holder.alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onChangeState(alarmClock);
                }
            }
        });


        holder.messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onChangeMessage(position);
                }
            }
        });
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
        @BindView(R.id.message)
        LinearLayout messageLayout;

        @BindView(R.id.alarm_switch)
        Switch alarmSwitch;

        @BindView(R.id.view_background)
        public RelativeLayout viewBackground;

        @BindView(R.id.view_foreground)
        public ConstraintLayout viewForeground;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setAlarmTime(long millis){
            String timeIn24HourFormat = timeIn24HourFormat(millis);
            alarmTime.setText(timeIn24HourFormat);
        }

        @SuppressLint("ResourceAsColor")
        public void setAlarmMessage(String message){
            if(message == null){
                message = context.getString(R.string.title_alarm_clock);
            }
            alarmMessage.setText(message);
        }

        public void enableAlarm(boolean enable){
            alarmSwitch.setChecked(enable);
        }

    }
}
