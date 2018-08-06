package com.bachelorarbeit.bachelorarbeit;

import java.util.ArrayList;
import java.util.Date;

public class Entry {
    private ArrayList sensitivies;
    private ArrayList activities;
    private Date date;
    private String daytime;
    private String time;
    private boolean manual;


    public Entry(ArrayList sensitivies, ArrayList activities, Date date, String daytime){
        this.sensitivies = sensitivies;
        this.activities = activities;
        this.date = date;
        this.daytime = daytime;
    }

    public Entry(ArrayList sensitivies, ArrayList activities, Date date, String time, boolean manual){
        this.sensitivies = sensitivies;
        this.activities = activities;
        this.date = date;
        this.time = time;
        this.manual = manual;
    }
}
