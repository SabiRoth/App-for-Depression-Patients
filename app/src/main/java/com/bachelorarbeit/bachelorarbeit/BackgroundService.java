package com.bachelorarbeit.bachelorarbeit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;


public class BackgroundService extends Service {


    DateTimePicker dateTimePicker;
    ArrayList<String> remindNotificationDates;
    int notificationId;


    @Override
    public void onCreate(){
        dateTimePicker = DateTimePicker.getInstance();
        remindNotificationDates = new ArrayList<>();
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        String trackingSetting = dataSource.getSettingViaName(getResources().getString(R.string.key_tracking_settings));
        if(trackingSetting!=null){
            if(trackingSetting.equals(getResources().getString(R.string.key_activated))) {
                startGPSTracker();
            }
        }
        else{
            startGPSTracker();
        }
        String pushSetting = dataSource.getSettingViaName(getResources().getString(R.string.key_push_notification));
        if(pushSetting!=null){
            if(pushSetting.equals(getResources().getString(R.string.key_activated))){
                proofLastEntry();
            }
        }
        else{
            proofLastEntry();
        }
        notificationId = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flasg, int startId){
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public static void startBackgroundAction(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BackgroundService.class);
        context.startService(intent);
    }

    public void startGPSTracker(){
        GPSTracker gpsTracker = new GPSTracker();
    }

    //if the last entry war 3 days or longer ago a notification is sent
    public void proofLastEntry(){
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        Entry lastEntry = dataSource.getLastEntry();
        if(lastEntry!=null) {
            String lastEntryDate = lastEntry.getDate();
            String currentDate = dateTimePicker.getCurrentDate();
            if (!(dateTimePicker.getMonthFromDate(lastEntryDate).equals(dateTimePicker.getMonthFromDate(currentDate)))) {
                proofAlreadyNotified(lastEntryDate, false);
            } else {
                if ((((Integer.parseInt(dateTimePicker.getDayFromDate(currentDate)))) - Integer.parseInt(dateTimePicker.getDayFromDate(lastEntryDate))) >= 3) {
                    proofAlreadyNotified(lastEntryDate, false);
                }
            }
        }
        else{
            proofAlreadyNotified(null, true);
        }
    }


    //proof if there was already sent a notification  this day
    public void proofAlreadyNotified(String lastEntryDate, boolean noDbEntry){
        if(remindNotificationDates.size()!=0){
            if(remindNotificationDates.get(remindNotificationDates.size()-1).equals(dateTimePicker.getCurrentDate())){
                return;
            }
        }
        remindNotificationDates.add(dateTimePicker.getCurrentDate());
        createNotification(lastEntryDate, dateTimePicker.getCurrentDate(), noDbEntry);
    }


    public void createNotification(String lastEntryDate, String channelId, Boolean noDbEntry){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, getResources().getString(R.string.key_myNotifications), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setName(getApplication().getResources().getString(R.string.app_name));
            notificationChannel.setDescription(getApplicationContext().getResources().getString(R.string.notification_part1) + lastEntryDate + getApplicationContext().getResources().getString(R.string.notification_part2));
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
            if(noDbEntry){
                notificationChannel.setDescription(getResources().getString(R.string.notification_no_entry));
            }
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);


        //calls the HomeActivity if the notification is clicked
        Intent notificationIntent = new Intent(this, ScoreActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notificationBuilder.setSmallIcon(R.drawable.icon)
                .setContentText(getApplicationContext().getResources().getString(R.string.notification_part1) + " " + lastEntryDate + getApplicationContext().getResources().getString(R.string.notification_part2))
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setAutoCancel(true) // hide the notification after its selected
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)
                .setTicker(getApplicationContext().getResources().getString(R.string.app_name));

        if(noDbEntry){
            notificationBuilder.setContentText(getResources().getString(R.string.notification_no_entry));
        }

        notificationManager.notify(notificationId, notificationBuilder.build());


    }


}
