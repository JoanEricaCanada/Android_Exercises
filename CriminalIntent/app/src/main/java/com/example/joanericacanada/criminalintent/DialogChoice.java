package com.example.joanericacanada.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by joanericacanada on 10/7/15.
 */
public class DialogChoice extends DialogFragment{
    public static final String EXTRA_CHOICE = "com.example.joanericacanada.criminalintent.choice";

    private int choice;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Select to change")
                .setPositiveButton("Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = 0;
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton("Time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = 1;
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_CHOICE, choice);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
