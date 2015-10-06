package com.example.joanericacanada.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class CrimeFragment extends Fragment {
    private Crime crime;
    private EditText edtTxtTitle;
    private Button btnDate;
    private CheckBox chkSolved;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        edtTxtTitle = (EditText)v.findViewById(R.id.crime_title);
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

        //DateFormat df = DateFormat.;

        btnDate = (Button) v.findViewById(R.id.crime_date);
        btnDate.setText(new SimpleDateFormat("EEEE MMM dd,yyyy").format(crime.getDate()));
        btnDate.setEnabled(false);

        chkSolved = (CheckBox) v.findViewById(R.id.crime_solved);
        chkSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });
        return v;
    }
}
