package com.bachelorarbeit.bachelorarbeit;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;


public class MainActivity extends AppCompatActivity {

    String time;
    String daytime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, GPSTracker.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        proofPopUp();
    }


    private void proofPopUp(){
        DateTimePicker dateTimePicker = DateTimePicker.getInstance();
        time = dateTimePicker.getCurrentTime();
        String date = dateTimePicker.getCurrentDate();
        daytime = dateTimePicker.getDaytime();

        dataSource dataSource = new dataSource(this);
        dataSource.open();
        Entry lastEntry = dataSource.getLastEntry();
        if(lastEntry != null) {
            if ((lastEntry.getDate().equals(date) && lastEntry.getDaytime().equals(daytime))) {
                goToHomescreen();
                return;
            }
        }
        showDialog();

    }


    private void showDialog(){
        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment myAlertDialogFragment = MyAlertDialogFragment.newInstance(time, daytime);
        myAlertDialogFragment.show(fm, "fragment_edit_name");
    }

    private void goToHomescreen(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }


    @Override
    public void onPause() {
        super.onPause();
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }

    @Override
    public void onStop() {
        super.onStop();
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }
}