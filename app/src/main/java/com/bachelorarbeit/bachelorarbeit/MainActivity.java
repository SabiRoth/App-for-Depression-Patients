package com.bachelorarbeit.bachelorarbeit;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    String time;
    String daytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Prüfen wann der letzt Eintrag in DB + welche Uhrzeit es ist -> Je nachdem Popup oder HomeActivity
        //TODO: Calendar auslagern (CalendarActivity) und hier und in Places nutzen (getDate; getTime)

        DateTimePicker dateTimePicker = DateTimePicker.getInstance();
        time = dateTimePicker.getCurrentTime();
        String date = dateTimePicker.getCurrentDate();
        daytime = dateTimePicker.getDaytime();

        showDialog();

    }

    private void showDialog(){
        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment myAlertDialogFragment = MyAlertDialogFragment.newInstance(time, daytime);
        myAlertDialogFragment.show(fm, "fragment_edit_name");
    }
}
