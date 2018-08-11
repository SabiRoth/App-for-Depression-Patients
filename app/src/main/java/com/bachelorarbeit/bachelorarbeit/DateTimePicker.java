package com.bachelorarbeit.bachelorarbeit;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateTimePicker extends Calendar {

//TODO: Strings auslagern
    Calendar calendar = Calendar.getInstance();

    public DateTimePicker(){
    }

    public static DateTimePicker getInstance(){
        DateTimePicker dateTimePicker = new DateTimePicker();
        return dateTimePicker;
    }


    public String getCurrentTime() {
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
        if(minute.length() == 1){
            minute = "0" + minute;
        }
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        if(hour.length()==1){
            hour = "0" + hour;
        }

        return hour + ":" + minute;
    }

    public String getCurrentDate(){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH)+1) + "." +  calendar.get(Calendar.YEAR);
    }

    public String getDaytime(){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String daytime;
        if(calendar.get(Calendar.HOUR_OF_DAY) < 11 && calendar.get(Calendar.HOUR_OF_DAY) > 2){
            daytime = "Morgen";
        }
        if(calendar.get(Calendar.HOUR_OF_DAY) > 16 || calendar.get(Calendar.HOUR_OF_DAY) < 3){
            daytime = "Abend";
        }
        else{
            daytime = "Mittag";
        }
        return daytime;
    }


    @Override
    protected void computeTime() {

    }

    @Override
    protected void computeFields() {

    }

    @Override
    public long getTimeInMillis() {
        return super.getTimeInMillis();
    }

    @Override
    public void setTimeInMillis(long millis) {
        super.setTimeInMillis(millis);
    }

    @Override
    public int get(int field) {
        return super.get(field);
    }

    @Override
    public void set(int field, int value) {
        super.set(field, value);
    }

    @Override
    public String getDisplayName(int field, int style, Locale locale) {
        return super.getDisplayName(field, style, locale);
    }

    @Override
    public Map<String, Integer> getDisplayNames(int field, int style, Locale locale) {
        return super.getDisplayNames(field, style, locale);
    }

    @Override
    protected void complete() {
        super.complete();
    }

    @Override
    public String getCalendarType() {
        return super.getCalendarType();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean before(Object when) {
        return super.before(when);
    }

    @Override
    public boolean after(Object when) {
        return super.after(when);
    }

    @Override
    public int compareTo(Calendar anotherCalendar) {
        return super.compareTo(anotherCalendar);
    }

    @Override
    public void add(int i, int i1) {

    }

    @Override
    public void roll(int i, boolean b) {

    }

    @Override
    public void roll(int field, int amount) {
        super.roll(field, amount);
    }

    @Override
    public void setTimeZone(TimeZone value) {
        super.setTimeZone(value);
    }

    @Override
    public TimeZone getTimeZone() {
        return super.getTimeZone();
    }

    @Override
    public void setLenient(boolean lenient) {
        super.setLenient(lenient);
    }

    @Override
    public boolean isLenient() {
        return super.isLenient();
    }

    @Override
    public void setFirstDayOfWeek(int value) {
        super.setFirstDayOfWeek(value);
    }

    @Override
    public int getFirstDayOfWeek() {
        return super.getFirstDayOfWeek();
    }

    @Override
    public void setMinimalDaysInFirstWeek(int value) {
        super.setMinimalDaysInFirstWeek(value);
    }

    @Override
    public int getMinimalDaysInFirstWeek() {
        return super.getMinimalDaysInFirstWeek();
    }

    @Override
    public boolean isWeekDateSupported() {
        return super.isWeekDateSupported();
    }

    @Override
    public int getWeekYear() {
        return super.getWeekYear();
    }

    @Override
    public void setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
        super.setWeekDate(weekYear, weekOfYear, dayOfWeek);
    }

    @Override
    public int getWeeksInWeekYear() {
        return super.getWeeksInWeekYear();
    }

    @Override
    public int getMinimum(int i) {
        return 0;
    }

    @Override
    public int getMaximum(int i) {
        return 0;
    }

    @Override
    public int getGreatestMinimum(int i) {
        return 0;
    }

    @Override
    public int getLeastMaximum(int i) {
        return 0;
    }

    @Override
    public int getActualMinimum(int field) {
        return super.getActualMinimum(field);
    }

    @Override
    public int getActualMaximum(int field) {
        return super.getActualMaximum(field);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
