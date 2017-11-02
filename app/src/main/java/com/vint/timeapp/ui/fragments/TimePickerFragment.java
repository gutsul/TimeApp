package com.vint.timeapp.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by gutsul on 01.11.17.
 */

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener callback;

    public TimePickerFragment() {

    }

    @SuppressLint("ValidFragment")
    public TimePickerFragment(TimePickerDialog.OnTimeSetListener callBack) {
        this.callback = callBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), callback, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener callBack){
        TimePickerFragment fragment = new TimePickerFragment(callBack);
        return fragment;
    }

}