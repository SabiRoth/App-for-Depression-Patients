package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {


    Switch switchButtonGPS;
    Switch switchButtonPush;
    dataSource dataSource;

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
        //tracking aktivieren
    }
    private void deactivateTracking(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_tracking_settings), getResources().getString(R.string.key_deactivated));
        //tracking deaktivieren
    }

    private void activatePush(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_push_notification), getResources().getString(R.string.key_activated));
        //push aktivieren
    }

    private void deactivatePush(){
        dataSource.createSettingsEntry(getResources().getString(R.string.key_push_notification), getResources().getString(R.string.key_deactivated));
        //push deaktivieren
    }

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

    }

    private void openSiteNotice(){
        
    }
}
