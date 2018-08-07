package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Prüfen wann das letzte mal geöffnet -> Je nachdem Popup oder HomeActivity

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
