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
        TextView recipientMail = (TextView)findViewById(R.id.TextView_recipientMail);
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


        recipientMail.setClickable(true);
        recipientMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecipientMailInput();
            }
        });
    }

    private void activateTracking(){
        //TODO: In DB speichern & tracking aktivieren
    }
    private void deactivateTracking(){

    }

    private void activatePush(){

    }

    private void deactivatePush(){

    }

    private void getCurrentSettings(){
        //TODO AUS DB HOLEN und Schalter einstellen

    }

    private void openRecipientMailInput(){
        FragmentManager fm = getSupportFragmentManager();
        PopUp_MailRecipient popUp_mailRecipient = PopUp_MailRecipient.newInstance();
        popUp_mailRecipient.show(fm, "popUpMail");
    }
}
