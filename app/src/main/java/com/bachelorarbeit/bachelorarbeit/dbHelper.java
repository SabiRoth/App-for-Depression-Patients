package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "depTrackAppDB.db";
    private static final int DB_VERSION = 6;
    public static final String TABLE_ALL_ENTRIES = "Einträgetabelle";
    public static final String TABLE_MOVEMENT_DATA = "Bewegungsdatentabelle";
    public static final String TABLE_OWN_SENSITIVITIES_ENTRIES = "EigeneBefindlichkeitentabelle";
    public static final String TABLE_OWN_ACTIVITIES_ENTRIES = "EigeneAktivitätentabelle";
    public static final String TABLE_MAIN_SYMPTOMS ="Hauptsymptometabelle";
    public static final String TABLE_SETTINGS = "Einstellungentabelle";
    public static final String TABLE_SCORE = "Scoretabelle";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SENSIBILITIES = "Befindlichkeiten";
    public static final String COLUMN_ACTIVITIES = "Aktivitäten";
    public static final String COLUMN_PLACES = "Orte";
    public static final String COLUMN_DATE = "Datum";
    public static final String COLUMN_TIME = "Uhrzeit";
    public static final String COLUMN_DAYTIME = "Tageszeit";
    public static final String COLUMN_LONGITUDE = "Längengrad";
    public static final String COLUMN_LATITUDE = "Breitengrad";
    public static final String COLUMN_OWN_SENSITIVITY ="Befindlichkeit";
    public static final String COLUMN_OWN_ACTIVITIES = "Aktivität";
    public static final String COLUMN_SCORE = "Score";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_VALUE = "Value";


    public static final String SQL_CREATE_TABLE_ENTRIES =
            "CREATE TABLE " + TABLE_ALL_ENTRIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SENSIBILITIES + " TEXT,"+ COLUMN_ACTIVITIES + " TEXT,"+
                    COLUMN_PLACES + " TEXT," + COLUMN_DATE + " TEXT NOT NULL," + COLUMN_TIME + " TEXT NOT NULL," +COLUMN_DAYTIME + " TEXT NOT NULL"+ ")";

    public static final String SQL_CREATE_TABLE_MOVEMENT_DATA =
            "CREATE TABLE " + TABLE_MOVEMENT_DATA +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_LONGITUDE + " TEXT,"+
                    COLUMN_LATITUDE + " TEXT" + ")";

    public static final String SQL_CREATE_TABLE_OWN_SENSITIVITIES_ENTRIES =
            "CREATE TABLE " + TABLE_OWN_SENSITIVITIES_ENTRIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_OWN_SENSITIVITY + " TEXT" + ")";

    public static final String SQL_CREATE_TABLE_OWN_ACTIVITIES_ENTRIES =
            "CREATE TABLE " + TABLE_OWN_ACTIVITIES_ENTRIES +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_OWN_ACTIVITIES + " TEXT" + ")";

    public static final String SQL_CREATE_TABLE_MAIN_SYMPTOMS =
            "CREATE TABLE "+ TABLE_MAIN_SYMPTOMS +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SENSIBILITIES + " TEXT NOT NULL," + COLUMN_SCORE +  " TEXT NOT NULL," +
                    COLUMN_DATE + " TEXT NOT NULL," + COLUMN_TIME + " TEXT NOT NULL"+ ")";

    public static final String SQL_CREATE_TABLE_SETTINGS =
            "CREATE TABLE " + TABLE_SETTINGS +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_VALUE + " TEXT NOT NULL"+ ")";

    public static final String SQL_CREATE_TABLE_SCORE =
            "CREATE TABLE " + TABLE_SCORE +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SCORE + " TEXT NOT NULL," + COLUMN_DATE + " TEXT NOT NULL," +
                    COLUMN_TIME + " TEXT NOT NULL"+ ")";




    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE_ENTRIES);
            db.execSQL(SQL_CREATE_TABLE_MOVEMENT_DATA);
            db.execSQL(SQL_CREATE_TABLE_OWN_SENSITIVITIES_ENTRIES);
            db.execSQL(SQL_CREATE_TABLE_OWN_ACTIVITIES_ENTRIES);
            db.execSQL(SQL_CREATE_TABLE_MAIN_SYMPTOMS);
            db.execSQL(SQL_CREATE_TABLE_SETTINGS);
            db.execSQL(SQL_CREATE_TABLE_SCORE);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_ENTRIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEMENT_DATA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWN_SENSITIVITIES_ENTRIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWN_ACTIVITIES_ENTRIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_SYMPTOMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
            onCreate(db);
        }
    }
}
