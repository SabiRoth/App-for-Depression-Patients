package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {


    private Switch switchButtonGPS, switchButtonPush;
    private dataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchButtonGPS= (Switch)findViewById(R.id.switch_gps);
        switchButtonPush = (Switch)findViewById(R.id.switch_push);
        TextView mainSymptoms = (TextView)findViewById(R.id.TextView_mainSymptoms);
        TextView recipientMail = (TextView)findViewById(R.id.TextView_recipientMail);
        TextView privacyPolicy = (TextView)findViewById(R.id.TextView_privacyPolicy);
        TextView siteNotice = (TextView)findViewById(R.id.TextView_siteNotice);
        dataSource = new dataSource(this);
        dataSource.open();
        getCurrentSettings();
        switchButtonGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    activateTracking();

                } else {
                    deactivateTracking();
                }
            }
        });
        switchButtonPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    activatePush();
                } else {
                    deactivatePush();
                }
            }
        });
        mainSymptoms.setClickable(true);
        mainSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMainSymptoms();
            }
        });

        recipientMail.setClickable(true);
        recipientMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecipientMailInput();
            }
        });

        privacyPolicy.setClickable(true);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrivacyPolicy();
            }
        });

        siteNotice.setClickable(true);
        siteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSiteNotice();
            }
        });
    }

    private void activateTracking(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_tracking_settings), getResources().getString(R.string.key_activated));
        Intent i = new Intent(this, GPSTracker.class);
        startActivityForResult(i, 1);
    }

    private void deactivateTracking(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_tracking_settings), getResources().getString(R.string.key_deactivated));
        FragmentManager fm = getSupportFragmentManager();
        PopUp_Restart popUp_restart = PopUp_Restart.newInstance();
        popUp_restart.show(fm, getResources().getString(R.string.key_popUp_restart));
    }

    private void activatePush(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_push_notification), getResources().getString(R.string.key_activated));
    }

    private void deactivatePush(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_push_notification), getResources().getString(R.string.key_deactivated));
    }

    /*
       Reads the last preferences of the settings from the database and set them as default value of the switch-buttons
     */
    private void getCurrentSettings(){
       String trackingSetting = dataSource.getSettingViaName(getResources().getString(R.string.key_tracking_settings));
       if(trackingSetting==null || trackingSetting.equals(getResources().getString(R.string.key_activated))){
           switchButtonGPS.setChecked(true);
       }
       else{
           switchButtonGPS.setChecked(false);
       }

       String pushSetting = dataSource.getSettingViaName(getResources().getString(R.string.key_push_notification));
       if(pushSetting==null || pushSetting.equals(getResources().getString(R.string.key_activated))){
           switchButtonPush.setChecked(true);
       }
       else{
           switchButtonPush.setChecked(false);
       }
    }

    private void changeMainSymptoms(){
        FragmentManager fm = getSupportFragmentManager();
        PopUp_MainSymptoms popUp_mainSymptoms = PopUp_MainSymptoms.newInstance();
        popUp_mainSymptoms.show(fm, getResources().getString(R.string.key_popUpMainSymptoms));
    }

    private void openRecipientMailInput(){
        FragmentManager fm = getSupportFragmentManager();
        PopUp_MailRecipient popUp_mailRecipient = PopUp_MailRecipient.newInstance();
        popUp_mailRecipient.show(fm, getResources().getString(R.string.key_popUpMail));
    }

    private void openPrivacyPolicy(){
        Intent i = new Intent(this, Policy_Notice_EmergencyActivity.class);
        i.putExtra(getResources().getString(R.string.key_called_activity), getResources().getString(R.string.key_privacy_policy));
        startActivity(i);
    }

    private void openSiteNotice(){
        Intent i = new Intent(this, Policy_Notice_EmergencyActivity.class);
        i.putExtra(getResources().getString(R.string.key_called_activity), getResources().getString(R.string.key_site_notice));
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        dataSource.close();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
