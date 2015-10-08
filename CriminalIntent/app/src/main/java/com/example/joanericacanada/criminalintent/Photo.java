package com.example.joanericacanada.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joanericacanada on 10/8/15.
 */
public class Photo {
    private static final String JSON_FILENAME = "filename";
    private String fileName;

    public Photo(String filename) {
        this.fileName = filename;
    }

    public Photo(JSONObject json) throws JSONException {
        this.fileName = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, fileName);
        return json;
    }

    public String getFilename() {
        return fileName;
    }
}
