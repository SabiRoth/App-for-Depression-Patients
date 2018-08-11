package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    //TODO: Wörter aus Strings holen

    private static final String DB_NAME = "dbDepressionsApp.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_ENTRIES = "TabelleDerEinträge";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SENSIBILITIES = "Befindlichkeiten";
    public static final String COLUMN_ACTIVITIES = "Aktivitäten";
    public static final String COLUMN_PLACES = "Orte";
    public static final String COLUMN_DATE = "Datum";
    public static final String COLUMN_TIME = "Uhrzeit";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_ENTRIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SENSIBILITIES + " TEXT,"+
                    COLUMN_ACTIVITIES + " TEXT,"+ COLUMN_PLACES + " TEXT," + COLUMN_DATE + " TEXT NOT NULL," + COLUMN_TIME + " TEXT NOT NULL" + ")";


    //constructor
    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE);
            //Restliche Tabellen
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
            //restliche Tabellen
            onCreate(db);
        }

    }

}
