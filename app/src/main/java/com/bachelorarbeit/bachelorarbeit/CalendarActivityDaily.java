package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivityDaily extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ListView listViewMorningSensivities, listViewMorningActivities, listViewMorningPlaces, listViewMiddaySensivities, listViewMiddayActivities,
                     listViewMiddayPlaces, listViewEveningSensivities, listViewEveningActivities, listViewEveningPlaces;
    private Spinner spinner;
    private Button datePickerButton;
    private DateTimePicker dateTimePicker;
    private String pickedDate;
    private String[] spinnerList;
    int check = 0;


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
        spinnerList = getResources().getStringArray(R.array.spinner_view_daily);
        initializeSpinner();
        pickedDate = getIntent().getStringExtra(getResources().getString(R.string.key_date));
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        datePickerButton.setText(pickedDate);
        fillListViews();
        initializeClickListenerForDatePickerButton();
    }

    private void fillListViews(){
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<Entry> allEntries = dataSource.getAllEntries(pickedDate);
        dataSource.close();
        ArrayList<String> morningEntriesSensivities = new ArrayList<>(), morningEntriesActivities = new ArrayList<>(), morningEntriesPlaces = new ArrayList<>();
        ArrayList<String> middayEntriesSensivities = new ArrayList<>(), middayEntriesActivities = new ArrayList<>(), middayEntriesPlaces = new ArrayList<>();
        ArrayList<String> eveningEntriesSensivities = new ArrayList<>(), eveningEntriesActivities = new ArrayList<>(), eveningEntriesPlaces = new ArrayList<>();
        for(int i = 0; i<allEntries.size(); i++){
            String currentDaytime = allEntries.get(i).getDaytime();
            if(currentDaytime.equals(getResources().getString(R.string.calendar_daily_morning_textview))){
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
            if(currentDaytime.equals(getResources().getString(R.string.calendar_daily_midday_textview))){
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
            if(currentDaytime.equals(getResources().getString(R.string.calendar_daily_evening_textview))){
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
            listViewMorningSensivities.setVisibility(View.VISIBLE);
        }
        if(morningEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, morningEntriesActivities);
            listViewMorningActivities.setAdapter(adapter);
            listViewMorningActivities.setVisibility(View.VISIBLE);
        }
        if(morningEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, morningEntriesPlaces);
            listViewMorningPlaces.setAdapter(adapter);
            listViewMorningPlaces.setVisibility(View.VISIBLE);
        }
        if(middayEntriesSensivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_simple_listview, middayEntriesSensivities);
            listViewMiddaySensivities.setAdapter(adapter);
            listViewMiddaySensivities.setVisibility(View.VISIBLE);
        }
        if(middayEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, middayEntriesActivities);
            listViewMiddayActivities.setAdapter(adapter);
            listViewMiddayActivities.setVisibility(View.VISIBLE);
        }
        if(middayEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, middayEntriesPlaces);
            listViewMiddayPlaces.setAdapter(adapter);
            listViewMiddayPlaces.setVisibility(View.VISIBLE);
        }
        if(eveningEntriesSensivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_simple_listview, eveningEntriesSensivities);
            listViewEveningSensivities.setAdapter(adapter);
            listViewEveningSensivities.setVisibility(View.VISIBLE);
        }
        if(eveningEntriesActivities.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_activities, eveningEntriesActivities);
            listViewEveningActivities.setAdapter(adapter);
            listViewEveningActivities.setVisibility(View.VISIBLE);
        }
        if(eveningEntriesPlaces.size()!=0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.listentry_calendar_places, eveningEntriesPlaces);
            listViewEveningPlaces.setAdapter(adapter);
            listViewEveningPlaces.setVisibility(View.VISIBLE);
        }

        if(morningEntriesSensivities.size()==0 && morningEntriesActivities.size()==0 && morningEntriesPlaces.size() ==0){
            TextView noMorningEntry = (TextView) findViewById(R.id.noMorningEntry);
            noMorningEntry.setVisibility(View.VISIBLE);
        }
        if(middayEntriesSensivities.size() == 0 && middayEntriesActivities.size() == 0 && middayEntriesPlaces.size() == 0){
            TextView noMiddayEntry = (TextView) findViewById(R.id.noMiddayEntry);
            noMiddayEntry.setVisibility(View.VISIBLE);
        }
        if(eveningEntriesSensivities.size() == 0 && eveningEntriesActivities.size() == 0 && eveningEntriesPlaces.size() == 0){
            TextView noEveningEntry = (TextView)findViewById(R.id.noEveningEntry);
            noEveningEntry.setVisibility(View.VISIBLE);
        }
    }

    private void initializeSpinner(){
        ArrayAdapter<String>spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 1) {
            if (parent.getItemAtPosition(position).toString().equals(getResources().getString(R.string.key_weekView))) {
                Intent i = new Intent(getApplicationContext(), CalendarActivityWeekly.class);
                this.finish();
                startActivity(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void initializeClickListenerForDatePickerButton(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivityDaily.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        String newDate = dateTimePicker.setDateFormat(dayOfMonth, (monthOfYear + 1),  year);
                        Intent reloadIntent = new Intent(CalendarActivityDaily.this, CalendarActivityDaily.class);
                        reloadIntent.putExtra(getResources().getString(R.string.key_date), newDate);
                        CalendarActivityDaily.this.finish();
                        startActivity(reloadIntent);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }
}
