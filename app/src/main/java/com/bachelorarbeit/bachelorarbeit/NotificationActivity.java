package com.bachelorarbeit.bachelorarbeit;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;

public class NotificationActivity extends Activity {



    private NotificationManager notificationManager;
    private NotificationCompat.Builder b;
    private Context context;

    public NotificationActivity(){
    }

    public NotificationActivity(Context context){
        this.context = context;
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        b = new NotificationCompat.Builder(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }

    public void createNotification(String lastEntryDate, int notificationID){

        //calls the HomeActivity if the notification is clicked
        Intent notificationIntent = new Intent(context, HomeActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


        //the builder creates the notification/-layout
        b.setSmallIcon(R.mipmap.sensitivities_icon)
                .setContentText(getApplicationContext().getResources().getString(R.string.notification_part1) + lastEntryDate + getApplicationContext().getResources().getString(R.string.notification_part2)) //TODO: strings
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setAutoCancel(true) // hide the notification after its selected
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)
                //.setColor(Color.BLUE)
                .setTicker(getApplicationContext().getResources().getString(R.string.app_name));
        notificationManager.notify(notificationID, b.build());
    }


}
