package com.example.joanericacanada.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by joanericacanada on 10/6/15.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> crimes;
    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab crimeLab;
    private Context contextApp;

    private CrimeLab(Context contextApp) {
        this.contextApp = contextApp;
        mSerializer =new CriminalIntentJSONSerializer(contextApp, FILENAME);

        try {
            int count = 0;
            crimes = mSerializer.loadCrimes();
            count = crimes.size();
            Log.i(TAG, Integer.toString(count));

            if(count == 0) {
                Log.i(TAG, "Created");
                crimes = new ArrayList<Crime>();
                for (int i = 0; i < 100; i++) {
                    Crime c = new Crime();
                    c.setTitle("Crime #" + i);
                    c.setSolved(i % 2 == 0);
                    crimes.add(c);
                }
            }
        } catch (Exception e) {

        }
    }

    public static CrimeLab get(Context c) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(c.getApplicationContext());
        }
        return crimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : crimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(crimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }

    }
}
