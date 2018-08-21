package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivityDaily extends AppCompatActivity{

    ListView listViewMorningSensivities;
    ListView listViewMorningActivities;
    ListView listViewMorningPlaces;
    ListView listViewMiddaySensivities;
    ListView listViewMiddayActivities;
    ListView listViewMiddayPlaces;
    ListView listViewEveningSensivities;
    ListView listViewEveningActivities;
    ListView listViewEveningPlaces;
    Spinner spinner;
    Button datePickerButton;
    dataSource dataSource;
    DateTimePicker dateTimePicker;
    String pickedDate;
    String[] spinnerList = {"Tagesansicht", "Wochenansicht", "Monatsansicht"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_daily);
        dateTimePicker = DateTimePicker.getInstance();
        listViewMorningSensivities = (ListView) findViewById(R.id.listView_calendar_daily_morning_sensivities);
        listViewMorningActivities = (ListView) findViewById(R.id.listView_calendar_daily_morning_activities);
        listViewMorningPlaces = (ListView) findViewById(R.id.listView_calendar_daily_morning_places);
        listViewMiddaySensivities = (ListView) findViewById(R.id.listView_calendar_daily_midday_sensivities);
        listViewMiddayActivities = (ListView) findViewById(R.id.listView_calendar_daily_midday_activities);
        listViewMiddayPlaces = (ListView) findViewById(R.id.listView_calendar_daily_midday_places);
        listViewEveningSensivities = (ListView) findViewById(R.id.listView_calendar_daily_evening_sensivities);
        listViewEveningActivities = (ListView) findViewById(R.id.listView_calendar_daily_evening_activities);
        listViewEveningPlaces = (ListView) findViewById(R.id.listView_calendar_daily_evening_places);
        datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        spinner = (Spinner) findViewById(R.id.spinner);
        initializeSpinner();
        pickedDate = getIntent().getStringExtra("date");
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        datePickerButton.setText(pickedDate);
        fillListViews();
        initializeClickListenerForDatePickerButton();
    }

    private void fillListViews(){
        dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<Entry> allEntries = dataSource.getAllEntries(pickedDate);
        ArrayList<String> morningEntriesSensivities = new ArrayList<>(), morningEntriesActivities = new ArrayList<>(), morningEntriesPlaces = new ArrayList<>();
        ArrayList<String> middayEntriesSensivities = new ArrayList<>(), middayEntriesActivities = new ArrayList<>(), middayEntriesPlaces = new ArrayList<>();
        ArrayList<String> eveningEntriesSensivities = new ArrayList<>(), eveningEntriesActivities = new ArrayList<>(), eveningEntriesPlaces = new ArrayList<>();
        for(int i = 0; i<allEntries.size(); i++){
            String currentDaytime = dateTimePicker.getDaytime(allEntries.get(i).getTime());
            if(currentDaytime.equals("Morgen")){
                if(allEntries.get(i).getSensitivies()!=null) {
                    morningEntriesSensivities.add(allEntries.get(i).getSensitivies());
                }
                if(allEntries.get(i).getActivities()!=null) {
                    morningEntriesActivities.add(allEntries.get(i).getActivities());
                }
                if(allEntries.get(i).getPlaces()!=null) {
                    morningEntriesPlaces.add(allEntries.get(i).getPlaces());
                }
            }
            if(currentDaytime.equals("Mittag")){
                if(allEntries.get(i).getSensitivies()!=null) {
                    middayEntriesSensivities.add(allEntries.get(i).getSensitivies());
                }
                if(allEntries.get(i).getActivities()!=null) {
                    middayEntriesActivities.add(allEntries.get(i).getActivities());
                }
                if(allEntries.get(i).getPlaces()!=null) {
                    middayEntriesPlaces.add(allEntries.get(i).getPlaces());
                }
            }
            if(currentDaytime.equals("Abend")){
                if(allEntries.get(i).getSensitivies()!=null) {
                    eveningEntriesSensivities.add(allEntries.get(i).getSensitivies());
                }
                if(allEntries.get(i).getActivities()!=null) {
                    eveningEntriesActivities.add(allEntries.get(i).getActivities());
                }
                if(allEntries.get(i).getPlaces()!=null) {
                    eveningEntriesPlaces.add(allEntries.get(i).getPlaces());
                }
            }
        }
        if(morningEntriesSensivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_simple_listview, morningEntriesSensivities);
            listViewMorningSensivities.setAdapter(adapter);
        }
        if(morningEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, morningEntriesActivities);
            listViewMorningActivities.setAdapter(adapter);
        }
        if(morningEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, morningEntriesPlaces);
            listViewMorningPlaces.setAdapter(adapter);
        }


        if(middayEntriesSensivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_simple_listview, middayEntriesSensivities);
            listViewMiddaySensivities.setAdapter(adapter);
        }
        if(middayEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, middayEntriesActivities);
            listViewMiddayActivities.setAdapter(adapter);
        }
        if(middayEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, middayEntriesPlaces);
            listViewMiddayPlaces.setAdapter(adapter);
        }


        if(eveningEntriesSensivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_simple_listview, eveningEntriesSensivities);
            listViewEveningSensivities.setAdapter(adapter);
        }
        if(eveningEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, eveningEntriesActivities);
            listViewEveningActivities.setAdapter(adapter);
        }
        if(eveningEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, eveningEntriesPlaces);
            listViewEveningPlaces.setAdapter(adapter);
        }
    }


    private void initializeSpinner(){
        ArrayAdapter<String>spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        //spinnerAdapter.setDropDownViewResource(R.layout.listentry_simple_listview);
        spinner.setAdapter(spinnerAdapter);
    }


    private void initializeClickListenerForDatePickerButton(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivityDaily.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        Intent reloadIntent = new Intent(CalendarActivityDaily.this, CalendarActivityDaily.class);
                        reloadIntent.putExtra("date", newDate);
                        CalendarActivityDaily.this.finish();
                        startActivity(reloadIntent);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}
