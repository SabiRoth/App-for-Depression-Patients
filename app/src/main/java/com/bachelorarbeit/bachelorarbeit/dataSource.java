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


    private String[] columnsEntries = {
            dbHelper.COLUMN_ID,
            dbHelper.COLUMN_SENSIBILITIES,
            dbHelper.COLUMN_PLACES,
            dbHelper.COLUMN_ACTIVITIES,
            dbHelper.COLUMN_DATE,
            dbHelper.COLUMN_TIME,
            dbHelper.COLUMN_DAYTIME
    };

    private String[] columnsMovementProfile = {
            dbHelper.COLUMN_ID,
            dbHelper.COLUMN_MOVEMENT_PROFILE,
            dbHelper.COLUMN_DATE
    };


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

    public void createMovementProfileEntry(){

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

    public Entry getLastEntry(){
        //TODO: Bessere Lösung mit Cursor
        ArrayList<Entry> allEntrys = getAllEntries();
        if(allEntrys.size()>0) {
            int lastEntryNumber = allEntrys.size() - 1;
            return allEntrys.get(lastEntryNumber);
        }
        else{
            return null;
        }
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
}