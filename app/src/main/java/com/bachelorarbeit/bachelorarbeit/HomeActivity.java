package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

   /*
   TODO: Prüfen ob deklaration hier außen notwendig ist
    private TextView overview;
    private Button overview_calendar_button;
    private Button overview_graph_button;
    private TextView entries;
    private Button entries_sensitivies_button;
    private Button entries_activities_button;
    private TextView movementProfile;
    private Button movementProfile_map_button;
    private Button movementProfile_places_button;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher_background);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */

        Button overview_calendar_button = (Button)findViewById(R.id.button_overview_calendar);
        Button overview_graph_button = (Button)findViewById(R.id.button_overview_graph);
        Button movementProfile_map_button = (Button)findViewById(R.id.button_map);
        Button entries_sensitivies_button = (Button)findViewById(R.id.button_sensitivies);
        Button entries_activities_button = (Button)findViewById(R.id.button_activities);
        ImageButton settingsButton = (ImageButton)findViewById(R.id.settings_imageButton);
        ImageButton emergencyButton = (ImageButton)findViewById(R.id.button_emergency);


        //clickListener for all buttons

        overview_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOverviewCalendar();
            }
        });

        overview_graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOverviewGraph();
            }
        });

        movementProfile_map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMovementProfileMap();
            }
        });

        entries_sensitivies_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEntriesSensitivies();
            }
        });

        entries_activities_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEntriesActivities();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSettings();
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEmergency();
            }
        });


    }

    private void clickOverviewCalendar(){
        Intent i = new Intent(this, CalendarActivityDaily.class);
        startActivity(i);
    }

    private void clickOverviewGraph(){
        Intent i = new Intent(this, GraphActivity.class);
        startActivity(i);
    }

    private void clickMovementProfileMap(){
        Intent i = new Intent(this, MapViewActivity.class);
        startActivity(i);
    }

    private void clickEntriesSensitivies(){
        Intent i = new Intent(this, SensitivitiesActivity.class);
        startActivity(i);
    }

    private void clickEntriesActivities(){
        Intent i = new Intent(this, ActivitiesActivity.class);
        startActivity(i);
    }

    private void clickSettings(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void clickEmergency(){
        Intent i = new Intent(this, EmergencyActivity.class);
        startActivity(i);
    }



    @Override
    public void onPause() {
        super.onPause();
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }
}
