package com.example.joanericacanada.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joanericacanada on 10/8/15.
 */
public class Photo {
    private static final String JSON_FILENAME = "filename";
    private static final String JSON_ORIENTATION = "orientation";
    private String fileName;
    private int orientation;

    public Photo(String filename, int orientation) {
        this.fileName = filename;
        this.orientation = orientation;
    }

    public Photo(JSONObject json) throws JSONException {
        this.fileName = json.getString(JSON_FILENAME);
        this.orientation = json.getInt(JSON_ORIENTATION);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, fileName);
        json.put(JSON_ORIENTATION, orientation);
        return json;
    }

    public String getFilename() {
        return fileName;
    }

    public int getOrientation(){
        return orientation;
    }
}
