package com.example.joanericacanada.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by joanericacanada on 10/6/15.
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";

    private UUID id;
    private String title ;
    private Date date = new Date();
    private Photo photo;
    private boolean solved;

    public Crime(){
        id = UUID.randomUUID();
    }

    public Crime(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(JSON_ID));
        title = json.getString(JSON_TITLE);
        solved = json.getBoolean(JSON_SOLVED);
        date = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_PHOTO))
            photo = new Photo(json.getJSONObject(JSON_PHOTO));

    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_TITLE, title);
        json.put(JSON_SOLVED, solved);
        json.put(JSON_DATE, date.getTime());

        if (photo != null)
            json.put(JSON_PHOTO, photo.toJSON());

        return json;
    }

    @Override
    public String toString(){
        return title;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Photo getPhoto() {
        return photo;
    }
    public void setPhoto(Photo p) {
        photo = p;
    }


}
