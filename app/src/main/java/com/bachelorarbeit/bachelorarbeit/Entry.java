package com.bachelorarbeit.bachelorarbeit;

public class Entry {
    private String sensitivies;
    private String activities;
    private String places;
    private String date;
    private String daytime;
    private String time;
    private long id;
    private boolean manual;


    public Entry(String sensitivies, String activities, String places, String date, String time){
        this.sensitivies = sensitivies;
        this.activities = activities;
        this.places = places;
        this.date = date;
        this.daytime = time;
    }

    public Entry(long id){
        this.id = id;
    }

    public Entry(){
        Entry entry = new Entry ("", "", "", "", "");
    }

    /*public Entry(ArrayList sensitivies, ArrayList activities, Date date, String time, boolean manual){
        this.sensitivies = sensitivies;
        this.activities = activities;
        this.date = date;
        this.time = time;
        this.manual = manual;
    }*/

    public void setId(long id)
    {this.id = id;}

    public long getId(){
        return id;
    }

    public String getSensitivies(){
        return sensitivies;
    }

    public void setSensitivities(String sensitivies){
        this.sensitivies = sensitivies;
    }

    public String getActivities(){
        return activities;
    }

    public void setActivities(String activities){
        this.activities = activities;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
