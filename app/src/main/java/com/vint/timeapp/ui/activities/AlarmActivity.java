package com.vint.timeapp.ui.activities;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.TextView;

import com.vint.timeapp.R;
import com.vint.timeapp.ui.receivers.AlarmClockReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vint.timeapp.ui.receivers.AlarmClockReceiver.*;
import static com.vint.timeapp.utils.TimeUtils.timeIn24HourFormat;

public class AlarmActivity extends AppCompatActivity {
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.message)
    TextView message;

    private Vibrator vibrator;
//        vibrator.vibrate(500);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        long timeInMillis = intent.getLongExtra(TIME, System.currentTimeMillis());
        String alarmMessage = intent.getStringExtra(MESSAGE);

        time.setText(timeIn24HourFormat(timeInMillis));
        message.setText(alarmMessage);

        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
    }

    @OnClick(R.id.stop)
    void stopAlarm(){
        Log.d("AlarmActivity", "Alarm Stoped");

        finish();
    }
}
