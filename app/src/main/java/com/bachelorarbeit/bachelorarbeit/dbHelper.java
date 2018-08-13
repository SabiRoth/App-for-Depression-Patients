package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    //TODO: Wörter aus Strings holen

    private static final String DB_NAME = "depTrackApp.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_ALL_ENTRIES = "Einträgetabelle";
    public static final String TABLE_MOVEMENT_DATA = "Bewegungsdatentabelle";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SENSIBILITIES = "Befindlichkeiten";
    public static final String COLUMN_ACTIVITIES = "Aktivitäten";
    public static final String COLUMN_PLACES = "Orte";
    public static final String COLUMN_DATE = "Datum";
    public static final String COLUMN_TIME = "Uhrzeit";
    public static final String COLUMN_DAYTIME = "Tageszeit";

    public static final String COLUMN_LONGITUDE = "Längengrad";
    public static final String COLUMN_LATITUDE = "Breitengrad";

    public static final String SQL_CREATE_TABLE_ENTRIES =
            "CREATE TABLE " + TABLE_ALL_ENTRIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SENSIBILITIES + " TEXT,"+ COLUMN_ACTIVITIES + " TEXT,"+
                    COLUMN_PLACES + " TEXT," + COLUMN_DATE + " TEXT NOT NULL," + COLUMN_TIME + " TEXT NOT NULL," +COLUMN_DAYTIME + " TEXT NOT NULL"+ ")";

    public static final String SQL_CREATE_TABLE_MOVEMENT_DATA =
            "CREATE TABLE " + TABLE_MOVEMENT_DATA +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_LONGITUDE + " TEXT,"+
                    COLUMN_LATITUDE + " TEXT" + ")";

    //constructor
    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE_ENTRIES);
            db.execSQL(SQL_CREATE_TABLE_MOVEMENT_DATA);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_ENTRIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEMENT_DATA);
            onCreate(db);
        }

    }

}
