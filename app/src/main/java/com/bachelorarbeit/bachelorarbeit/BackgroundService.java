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


    private DateTimePicker dateTimePicker;
    private ArrayList<String> remindNotificationDates;
    private int notificationId;


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
        dataSource.close();
    }

    @Override
    public int onStartCommand(Intent intent, int flasg, int startId){
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public void startGPSTracker(){
        GPSTracker gpsTracker = new GPSTracker();
    }

    /*
      if the last entry was 3 days or longer ago a notification is sent
    */
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

    /*
       proof if there was already sent a notification this day
    */
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
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Intent notificationIntent = new Intent(this, ScoreActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        getNotificationIcon(notificationBuilder);
        notificationBuilder
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setAutoCancel(true) // hide the notification after its selected
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)
                .setTicker(getApplicationContext().getResources().getString(R.string.app_name))
                .setContentText(getApplicationContext().getResources().getString(R.string.notification_part1) + " " + lastEntryDate + getApplicationContext().getResources().getString(R.string.notification_part2))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getApplicationContext().getResources().getString(R.string.notification_part1) + " " + lastEntryDate + getApplicationContext().getResources().getString(R.string.notification_part2)));
        if(noDbEntry){
            notificationBuilder.setContentText(getApplicationContext().getResources().getString(R.string.notification_no_entry));
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(getApplicationContext().getResources().getString(R.string.notification_no_entry)));
        }

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    /*
       depending on the SDK-Version an other icon has to be used
     */
    private void getNotificationIcon(NotificationCompat.Builder notificationBuilder){
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
            notificationBuilder.setSmallIcon(R.drawable.icon_silhouette);
        }
        else{
            notificationBuilder.setSmallIcon(R.drawable.icon);
        }
    }
}
