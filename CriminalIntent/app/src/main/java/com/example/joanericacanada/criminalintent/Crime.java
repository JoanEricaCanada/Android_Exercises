package com.example.joanericacanada.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by joanericacanada on 10/6/15.
 */
public class Crime {
    private UUID id;
    private String title;
    private Date date, time;
    private boolean solved;

    public Crime() {
        id = UUID.randomUUID();
        date = new Date();
        time = Calendar.getInstance().getTime();
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

    public Date getTime(){
        return time;
    }
    public void setTime(Date time) {
        this.time.setTime(time.getTime());
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

}
