package com.bachelorarbeit.bachelorarbeit;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.util.ArrayList;


public class BackgroundService extends IntentService {


    dataSource dataSource;
    DateTimePicker dateTimePicker;
    ArrayList<String> remindNotificationDates;

    public BackgroundService() {
        super("BackgroundService");
        dataSource = new dataSource(this);
        dateTimePicker = DateTimePicker.getInstance();
        proofLastEntry();
        startGPSTracker();
    }


    public static void startBackgroundAction(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BackgroundService.class);
        context.startService(intent);
    }

    public void startGPSTracker(){
        GPSTracker gpsTracker = new GPSTracker();
        //GPS aufnehmen und in DB speichern
    }

    //send notification if there wasn't sent one before this day
    public void sendNotification(String lastEntryDate){
        if(remindNotificationDates.size()!=0){
           if(remindNotificationDates.get(remindNotificationDates.size()-1) == dateTimePicker.getCurrentDate()){
               return;
            }
        }
        remindNotificationDates.add(dateTimePicker.getCurrentDate());
        NotificationActivity notificationActivity = new NotificationActivity(getApplicationContext());
        notificationActivity.createNotification(lastEntryDate, remindNotificationDates.size());
    }

    //if the last entry war 3 days or longer ago a notification is sent
    public void proofLastEntry(){
        dataSource.open();
        String lastEntryDate = dataSource.getLastEntry().getDate();
        String currentDate = dateTimePicker.getCurrentDate();
        if(dateTimePicker.getMonthFromDate(lastEntryDate) !=  dateTimePicker.getMonthFromDate(currentDate)){
            sendNotification(lastEntryDate);
        }
        else{
            if(((Integer.parseInt(dateTimePicker.getDayFromDate(lastEntryDate)))-(Integer.parseInt(dateTimePicker.getDayFromDate(currentDate))))>=3){
            sendNotification(lastEntryDate);
            }
        }
    }


    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
