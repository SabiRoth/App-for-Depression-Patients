package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateTimePicker extends Calendar {

    private Calendar calendar = Calendar.getInstance();

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
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        if(day.length()==1){
           day = "0" + day;
        }
        String month = Integer.toString(calendar.get(Calendar.MONTH)+1);
        if(month.length()==1){
            month = "0" + month;
        }
        return day + "." + month + "." +  calendar.get(Calendar.YEAR);
    }

    public String getDaytime(){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String daytime ="";
        Integer curerntHour = calendar.get(Calendar.HOUR_OF_DAY);
        if(curerntHour < 11 && curerntHour > 3){
            daytime = "Morgen";
        }
        if(curerntHour > 16 || curerntHour < 4){
            daytime = "Abend";
        }
        if(curerntHour > 10 && curerntHour<17){
            daytime = "Mittag";
        }
        return daytime;
    }

    public String getDaytime(String time){
        int hours = Integer.parseInt(time.substring(0,2));
        String daytime;
        if(hours < 11 && hours > 2){
            daytime = "Morgen";
        }
        if(hours > 16 || hours < 3){
            daytime = "Abend";
        }
        else{
            daytime = "Mittag";
        }
        return daytime;
    }


    public String getMonthFromDate(String date){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String month;
        if(date.length()== 9){
           month = date.substring(3,4);
        }
        else{
            month =date.substring(3,5);
        }
        return month;
    }

    public String getDayFromDate(String date){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return date.substring(0,2);
    }

    public String getTheDayBefore(String latestDate){
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        Integer currentDayInt = Integer.parseInt(latestDate.substring(0,2));
        if(currentDayInt>1) {
            currentDayInt -= 1;
            return currentDayInt.toString() + getCurrentDate().substring(2, getCurrentDate().length());
        }
        else{
            Integer currentMonthInt = Integer.parseInt(getCurrentDate().substring(3,5))-1;
            switch (currentMonthInt){
                case 1: currentDayInt = 31;
                case 2: currentDayInt = 28;
                case 3: currentDayInt = 31;
                case 4: currentDayInt = 30;
                case 5: currentDayInt = 31;
                case 6: currentDayInt = 30;
                case 7: currentDayInt = 31;
                case 8: currentDayInt = 31;
                case 9: currentDayInt = 30;
                case 10: currentDayInt = 31;
                case 11: currentDayInt = 30;
                case 12: currentDayInt = 31;
            }

            String currentDayString = currentDayInt.toString();
            if(currentDayString.length()==1){
                currentDayString = "0" + currentDayString;
            }
            String currentMonthString = currentMonthInt.toString();
            if(currentMonthString.length()==1){
                currentMonthString = "0" + currentMonthString;
            }

            return currentDayString + "." + currentMonthString + getCurrentDate().substring(getCurrentDate().length()-5, getCurrentDate().length());
        }
    }

    public String setDateFormat(int day, int month, int year){
        String dayString = Integer.toString(day);
        if(dayString.length()==1){
            dayString = "0" + dayString;
        }
        String monthString = Integer.toString(month);
        if(monthString.length()==1){
            monthString = "0" + monthString;
        }
        return dayString + "." + monthString + "." + year;
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
