package com.example.joanericacanada.remotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by joanericacanada on 10/12/15.
 */
public class RemoteControlFragment extends Fragment {
    private TextView txtVwSelected;
    private TextView txtVwWorking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remote_control, parent, false);
        txtVwSelected = (TextView)v
                .findViewById(R.id.fragment_remote_control_selectedTextView);
        txtVwWorking = (TextView)v
                .findViewById(R.id.fragment_remote_control_workingTextView);
        View.OnClickListener numberButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView = (TextView)v;
                String working = txtVwWorking.getText().toString();
                String text = textView.getText().toString();
                if (working.equals("0")) {
                    txtVwWorking.setText(text);
                } else {
                    txtVwWorking.setText(working + text);
                }
            }
        };

        TableLayout tableLayout = (TableLayout)v
                .findViewById(R.id.fragment_remote_control_tableLayout);
        int number = 1;
        for (int i = 2; i < tableLayout.getChildCount() - 1; i++) {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button btn = (Button)row.getChildAt(j);
                btn.setText("" + number);
                btn.setOnClickListener(numberButtonListener);
                number++;
            }
        }

        TableRow bottomRow = (TableRow)tableLayout
                .getChildAt(tableLayout.getChildCount() - 1);
        
        Button btnDelete = (Button)bottomRow.getChildAt(0);
        btnDelete.setText("Delete");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txtVwWorking.setText("0");
            }
        });
        
        Button btnZero = (Button)bottomRow.getChildAt(1);
        btnZero.setText("0");
        btnZero.setOnClickListener(numberButtonListener);


        Button btnEnter = (Button)bottomRow.getChildAt(2);
        btnEnter.setText("Enter");

        btnEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CharSequence working = txtVwWorking.getText();
                if (working.length() > 0)
                    txtVwSelected.setText(working);
                txtVwWorking.setText("0");
            }
        });
        return v;
    }
}
