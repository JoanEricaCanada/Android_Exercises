package com.example.joanericacanada.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment {
    private Crime crime;
    private EditText edtTxtTitle;
    private Button btnDate;
    private Button btnTime;
    private CheckBox chkSolved;

    public static final String EXTRA_CRIME_ID = "com.example.joanericacanada.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        //TITLE EDIT TEXT
        edtTxtTitle = (EditText)v.findViewById(R.id.crime_title);
        edtTxtTitle.setText(crime.getTitle());
        edtTxtTitle.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                crime.setTitle(c.toString());
            }

            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // This one too
            }
        });

        //DATE BUTTON
        btnDate = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        //TIME BUTTON
        btnTime = (Button)v.findViewById(R.id.crime_time);
        updateTime();
        btnTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(crime.getTime());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        //SOLVED? CHECKBOX
        chkSolved = (CheckBox) v.findViewById(R.id.crime_solved);
        chkSolved.setChecked(crime.isSolved());
        chkSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        return v;
    }

    public void updateDate(){
        btnDate.setText(new SimpleDateFormat("EEEE, MMM dd,yyyy").format(crime.getDate()));
    }

    public void updateTime(){
        btnTime.setText(new SimpleDateFormat("hh:mm aa").format(crime.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK)
            return;
        if(requestCode == REQUEST_DATE){
            Date dateResult = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(dateResult);
            updateDate();
        }else if(requestCode == REQUEST_TIME){
            Date timeResult = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            crime.setTime(timeResult);
            updateTime();
        }
    }
}
