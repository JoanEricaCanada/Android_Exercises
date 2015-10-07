package com.example.joanericacanada.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by joanericacanada on 10/7/15.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.example.joanericacanada.criminalintent.time";
    private Date time;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        time = (Date)getArguments().getSerializable(EXTRA_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int amPm = calendar.get(Calendar.AM_PM);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        //datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
        //timePicker.setHour(hour);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time.setHours(hourOfDay);
                time.setMinutes(minute);
                getArguments().putSerializable(EXTRA_TIME, time);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, time);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
