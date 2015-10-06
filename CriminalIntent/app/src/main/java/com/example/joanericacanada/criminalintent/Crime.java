package com.example.joanericacanada.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by joanericacanada on 10/6/15.
 */
public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {
        id = UUID.randomUUID();
        date = new Date();
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

}
