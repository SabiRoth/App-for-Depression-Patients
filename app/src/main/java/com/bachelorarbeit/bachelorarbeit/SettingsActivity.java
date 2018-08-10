package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch switchButtonGPS = (Switch)findViewById(R.id.switch_gps);
        Switch switchButtonPush = (Switch)findViewById(R.id.switch_push);
        //TODO: Aus DB letzte Einstellungen holen und je nachdem auf an oder aus schalten
        
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
    }

    private void activateTracking(){
        //TODO: In DB speichern & tracking deaktivieren
    }
    private void deactivateTracking(){

    }

    private void activatePush(){

    }

    private void deactivatePush(){

    }
}
