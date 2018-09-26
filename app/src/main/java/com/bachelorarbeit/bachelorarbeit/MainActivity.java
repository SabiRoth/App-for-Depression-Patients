package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String time;
    private String daytime;
    private dataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new dataSource(this);
        dataSource.open();
        String trackingSetting = dataSource.getSettingViaName(getResources().getString(R.string.key_tracking_settings));
        dataSource.close();
        if(trackingSetting!=null){
            if(trackingSetting.equals(getResources().getString(R.string.key_activated))) {
                GPSTracker gpsTracker = new GPSTracker();
                Intent i = new Intent(this, GPSTracker.class);
                startActivityForResult(i, 1);
            }
            else{
                proofPopUp();
            }
        }
        else{
            GPSTracker gpsTracker = new GPSTracker();
            Intent i = new Intent(this, GPSTracker.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        proofPopUp();
    }

    /*
       Checking if there is an entry of the current daytime in the database
     */
    private void proofPopUp(){
        DateTimePicker dateTimePicker = DateTimePicker.getInstance();
        time = dateTimePicker.getCurrentTime();
        String date = dateTimePicker.getCurrentDate();
        daytime = dateTimePicker.getDaytime();
        dataSource.open();
        Entry lastEntry = dataSource.getLastEntry();
        if(lastEntry != null) {
            if ((lastEntry.getDate().equals(date) && lastEntry.getDaytime().equals(daytime))) {
                goToHomescreen();
                return;
            }
        }
        ArrayList<String[]> lastScoreEntry = dataSource.getMainSymptomScoresViaNameAndDate(getResources().getString(R.string.key_score), date);
        dataSource.close();
        if(lastScoreEntry.size()!=0){
            if(dateTimePicker.getDaytime(lastScoreEntry.get(lastScoreEntry.size()-1)[2]).equals(daytime)){
                goToHomescreen();
                return;
            }
        }
        showDialog();
    }

    /*
      If there isn't an entry of the current daytime in the database the user gets asked to create an entry for now
    */
    private void showDialog(){
        FragmentManager fm = getSupportFragmentManager();
        PopUp_Start popUpStart = PopUp_Start.newInstance(time, daytime);
        popUpStart.show(fm, getResources().getString(R.string.key_popUpStart));
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