package com.example.joanericacanada.runtracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.example.joanericacanada.runtracker.RunDatabaseHelper.LocationCursor;
import com.example.joanericacanada.runtracker.RunDatabaseHelper.RunCursor;

import java.io.Serializable;

public class RunManager implements Serializable{
    private static final String TAG = "RunManager";

    private static final String PREFS_FILE = "runs";
    private static final String PREF_CURRENT_RUN_ID = "RunManager.currentRunId";

    public static final String ACTION_LOCATION = "com.bignerdranch.android.runtracker.ACTION_LOCATION";
    
    private static final String TEST_PROVIDER = "TEST_PROVIDER";
    
    private static RunManager runManager;
    private Context contextApp;
    private LocationManager locationManager;
    private RunDatabaseHelper helper;
    private SharedPreferences prefs;
    private long currentRunId;


    
    private RunManager(Context appContext) {
        contextApp = appContext;
        locationManager = (LocationManager)contextApp.getSystemService(Context.LOCATION_SERVICE);
        helper = new RunDatabaseHelper(contextApp);
        prefs = contextApp.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        currentRunId = prefs.getLong(PREF_CURRENT_RUN_ID, -1);
    }
    
    public static RunManager get(Context c) {
        if (runManager == null) {
            // we use the application context to avoid leaking activities
            runManager = new RunManager(c.getApplicationContext());
        }
        return runManager;
    }

    private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(contextApp, 0, broadcast, flags);
    }

    public void startLocationUpdates() {
        String provider = LocationManager.GPS_PROVIDER;
        // if we have the test provider and it's enabled, use it
        if (locationManager.getProvider(TEST_PROVIDER) != null && 
                locationManager.isProviderEnabled(TEST_PROVIDER)) {
            provider = TEST_PROVIDER;
        }
        Log.d(TAG, "Using provider " + provider);

        // get the last known location and broadcast it if we have one
        Location lastKnown = locationManager.getLastKnownLocation(provider);
        if (lastKnown != null) {
            // reset the time to now
            lastKnown.setTime(System.currentTimeMillis());
            broadcastLocation(lastKnown);
        }
        // start updates from the location manager
        PendingIntent pi = getLocationPendingIntent(true);
        locationManager.requestLocationUpdates(provider, 0, 0, pi);
    }
    
    public void stopLocationUpdates() {
        PendingIntent pi = getLocationPendingIntent(false);
        if (pi != null) {
            locationManager.removeUpdates(pi);
            pi.cancel();
        }
    }
    
    public boolean isTrackingRun() {
        return getLocationPendingIntent(false) != null;
    }
    
    public boolean isTrackingRun(Run run) {
        return run != null && run.getId() == currentRunId;
    }
    
    private void broadcastLocation(Location location) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED, location);
        contextApp.sendBroadcast(broadcast);
    }
    
    public Run startNewRun() {
        // insert a run into the db
        Run run = insertRun();
        // start tracking the run
        startTrackingRun(run);
        return run;
    }
    
    public void startTrackingRun(Run run) {
        // keep the ID
        currentRunId = run.getId();
        // store it in shared preferences
        prefs.edit().putLong(PREF_CURRENT_RUN_ID, currentRunId).commit();
        // start location updates
        startLocationUpdates();
    }
    
    public void stopRun() {
        stopLocationUpdates();
        currentRunId = -1;
        prefs.edit().remove(PREF_CURRENT_RUN_ID).commit();
    }
    
    private Run insertRun() {
        Run run = new Run();
        run.setId(helper.insertRun(run));
        return run;
    }

    public RunCursor queryRuns() {
        return helper.queryRuns();
    }
    
    public Run getRun(long id) {
        Run run = null;
        RunCursor cursor = helper.queryRun(id);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            run = cursor.getRun();
        cursor.close();
        return run;
    }

    public void insertLocation(Location loc) {
        if (currentRunId != -1) {
            helper.insertLocation(currentRunId, loc);
        } else {
            Log.e(TAG, "Location received with no tracking run; ignoring.");
        }
    }
    
    public Location getLastLocationForRun(long runId) {
        Location location = null;
        LocationCursor cursor = helper.queryLastLocationForRun(runId);
        cursor.moveToFirst();
        // if we got a row, get a location
        if (!cursor.isAfterLast())
            location = cursor.getLocation();
        cursor.close();
        return location;
    }

}
