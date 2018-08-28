package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Policy_Notice_EmergencyActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (getIntent().getStringExtra(getResources().getString(R.string.key_called_activity))){
            case "emergency":
                setContentView(R.layout.activity_emergency);
                break;

            case "siteNotice":
                setContentView(R.layout.activity_site_notice);
                break;

            case "privacyPolicy":
                setContentView(R.layout.activity_privacy_policy);
                break;
        }
    }
}
