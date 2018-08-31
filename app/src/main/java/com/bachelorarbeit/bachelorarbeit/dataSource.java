package com.bachelorarbeit.bachelorarbeit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class dataSource {


    private SQLiteDatabase database;
    private dbHelper dbHelper;


    private String[] columnsEntries = {dbHelper.COLUMN_ID, dbHelper.COLUMN_SENSIBILITIES, dbHelper.COLUMN_PLACES, dbHelper.COLUMN_ACTIVITIES,
            dbHelper.COLUMN_DATE, dbHelper.COLUMN_TIME, dbHelper.COLUMN_DAYTIME};
    private String[] columnsMovementProfile = {dbHelper.COLUMN_ID, dbHelper.COLUMN_DATE, dbHelper.COLUMN_LONGITUDE, dbHelper.COLUMN_LATITUDE};
    private String[] columnsOwnSensitivities = {dbHelper.COLUMN_ID, dbHelper.COLUMN_OWN_SENSITIVITY};
    private String[] columnsOwnActivities = {dbHelper.COLUMN_ID, dbHelper.COLUMN_OWN_ACTIVITIES};
    private String[] columnsMainSymptoms = {dbHelper.COLUMN_ID, dbHelper.COLUMN_SENSIBILITIES, dbHelper.COLUMN_SCORE, dbHelper.COLUMN_DATE, dbHelper.COLUMN_TIME};
    private String[] columnsSettings = {dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME, dbHelper.COLUMN_VALUE};

    public dataSource(Context context) {
        dbHelper = new dbHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    //write a new entry in the database
    public void createEntry(String sensitivies, String activities, String places, String date, String time, String daytime) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_SENSIBILITIES, sensitivies);
        values.put(dbHelper.COLUMN_ACTIVITIES, activities);
        values.put(dbHelper.COLUMN_PLACES, places);
        values.put(dbHelper.COLUMN_DATE, date);
        values.put(dbHelper.COLUMN_TIME, time);
        values.put(dbHelper.COLUMN_DAYTIME, daytime);

        database.insert(dbHelper.TABLE_ALL_ENTRIES, null, values);
    }

    public void createMovementEntry(String date, String longitude, String latitude){
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_DATE, date);
        values.put(dbHelper.COLUMN_LONGITUDE, longitude);
        values.put(dbHelper.COLUMN_LATITUDE, latitude);
        database.insert(dbHelper.TABLE_MOVEMENT_DATA, null, values);
    }

    public void createSensitivityEntry(String sensitivity) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_OWN_SENSITIVITY, sensitivity);
        database.insert(dbHelper.TABLE_OWN_SENSITIVITIES_ENTRIES, null, values);
    }

    public void createActivityEntry(String actvitiy) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_OWN_ACTIVITIES, actvitiy);
        database.insert(dbHelper.TABLE_OWN_ACTIVITIES_ENTRIES, null, values);
    }

    public void createMainSymptomsEntry(String sensibility, String score, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_SENSIBILITIES, sensibility);
        values.put(dbHelper.COLUMN_SCORE, score);
        values.put(dbHelper.COLUMN_DATE, date);
        values.put(dbHelper.COLUMN_TIME, time);
        database.insert(dbHelper.TABLE_MAIN_SYMPTOMS, null, values);
    }

    public void createSettingsEntry(String name, String value) {
        deleteSettingsEntry(name);
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, name);
        values.put(dbHelper.COLUMN_VALUE, value);
        database.insert(dbHelper.TABLE_SETTINGS, null, values);

    }

    public void createScoreEntry(String score, String date, String time){
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_SCORE, score);
        values.put(dbHelper.COLUMN_DATE, date);
        values.put(dbHelper.COLUMN_TIME, time);
    }

    public void deleteEntry(Entry entry) {
        long id = entry.getId();
        database.delete(dbHelper.TABLE_ALL_ENTRIES,
                dbHelper.COLUMN_ID + "=" + id,
                null);
    }


    //provide all entries in the database
    public ArrayList<Entry> getAllEntries() {
        Cursor cursor = database.query(dbHelper.TABLE_ALL_ENTRIES, columnsEntries, null, null, null, null, null);
        return cursorToEntry(cursor);
    }

    public ArrayList<Entry> getAllEntries(String date) {
        Cursor cursor = database.query(dbHelper.TABLE_ALL_ENTRIES, columnsEntries, null, null, null, null, null);
        return cursorToEntryDate(cursor, date);
    }


    //provide all entries in the database
    public ArrayList<String[]> getAllMovementEntriesViaDate(String date) {
        Cursor cursor = database.query(dbHelper.TABLE_MOVEMENT_DATA, columnsMovementProfile, null, null, null, null, null);
        return cursorToMovementEntry(cursor, date);
    }

    public ArrayList<String> getAllOwnSensitivities() {
        Cursor cursor = database.query(dbHelper.TABLE_OWN_SENSITIVITIES_ENTRIES, columnsOwnSensitivities, null, null, null, null, null);
        ArrayList<String> ownSensitivities = new ArrayList<>();
        if(cursor!=null){
            if(cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                    String entry = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_OWN_SENSITIVITY));
                    ownSensitivities.add(entry);
                }
            }
        }
        return ownSensitivities;
    }

    public ArrayList<String> getAllOwnActivities() {
        Cursor cursor = database.query(dbHelper.TABLE_OWN_ACTIVITIES_ENTRIES, columnsOwnActivities, null, null, null, null, null);
        ArrayList<String> ownActvities = new ArrayList<>();
        if(cursor!=null){
            if(cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                    String entry = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_OWN_ACTIVITIES));
                    ownActvities.add(entry);
                }
            }
        }
        return ownActvities;
    }

    public Entry getLastEntry(){
        ArrayList<Entry> allEntries = getAllEntries();
        if(allEntries.size()>0) {
            int lastEntryNumber = allEntries.size() - 1;
            return allEntries.get(lastEntryNumber);
        }
        else{
            return null;
        }
    }


    public String[] getLastMovementEntry(String date){
        ArrayList<String[]> allEntry = getAllMovementEntriesViaDate(date);
        if(allEntry.size()>0){
            int lastEntryNumber = allEntry.size() -1;
            return allEntry.get(lastEntryNumber);
        }
        else{
            return null;
        }
    }

    public String getSettingViaName(String settingName){
        Cursor cursor = database.query(dbHelper.TABLE_SETTINGS, columnsSettings, null, null, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    if((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME))).equals(settingName)) {
                        return cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_VALUE));
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<String[]> getMainSymptomScoresViaNameAndDate(String name, String date){
        Cursor cursor = database.query(dbHelper.TABLE_MAIN_SYMPTOMS, columnsMainSymptoms, null, null, null, null, null);
        ArrayList<String[]> result = new ArrayList<>();
        if(cursor!=null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENSIBILITIES))).equals(name)) && ((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE))).equals(date))) {
                        String[] temp = {cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENSIBILITIES)), cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SCORE)), cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_TIME))};
                        result.add(temp);
                    }
                }
            }
        }
        return result;
    }



    private ArrayList<Entry> cursorToEntry(Cursor cursor) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        if(cursor!=null){
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                    Entry entry = new Entry(id);
                    entry.setSensitivities(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENSIBILITIES)));
                    entry.setActivities(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_ACTIVITIES)));
                    entry.setPlaces(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PLACES)));
                    entry.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE)));
                    entry.setTime(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_TIME)));
                    entry.setDaytime(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DAYTIME)));
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private ArrayList<Entry> cursorToEntryDate  (Cursor cursor, String date) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        if(cursor!=null){
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    if((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE))).equals(date)) {
                        long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                        Entry entry = new Entry(id);
                        entry.setSensitivities(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENSIBILITIES)));
                        entry.setActivities(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_ACTIVITIES)));
                        entry.setPlaces(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PLACES)));
                        entry.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE)));
                        entry.setTime(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_TIME)));
                        entry.setDaytime(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DAYTIME)));
                        entries.add(entry);
                    }
                }
            }
        }
        return entries;
    }

    private ArrayList<String[]> cursorToMovementEntry(Cursor cursor, String date){
        ArrayList<String[]> entries = new ArrayList<>();
        if(cursor!=null){
            if(cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    if((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE))).equals(date)){
                        long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                        String[] entry = new String[3];
                        entry[0] = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_DATE));
                        entry[1] = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_LONGITUDE));
                        entry[2] = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_LATITUDE));
                        entries.add(entry);
                    }
                }
            }
        }
        return entries;
    }


    private void deleteSettingsEntry(String name){
        Cursor cursor = database.query(dbHelper.TABLE_SETTINGS, columnsSettings, null, null, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    if((cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME))).equals(name)) {
                        long id = cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_ID));
                        database.delete(dbHelper.TABLE_SETTINGS,
                                dbHelper.COLUMN_ID + "=" + id,
                                null);
                    }
                }
            }
        }
    }
}