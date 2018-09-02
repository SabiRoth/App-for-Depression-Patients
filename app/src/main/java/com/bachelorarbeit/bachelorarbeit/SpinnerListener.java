package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class SpinnerListener extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

        if(parent.getItemAtPosition(pos).toString().equals("Wochenansicht")){
            Intent i  = new Intent(getApplicationContext(), CalendarActivityWeekly.class);
            startActivity(i);
        }

        if(parent.getItemAtPosition(pos).toString().equals("Tagesansicht")){
            Intent i = new Intent(getApplicationContext(), CalendarActivityDaily.class);
            startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
