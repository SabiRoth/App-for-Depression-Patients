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

    ListView listViewMorning;
    ListView listViewMidday;
    ListView listViewEvening;
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
        listViewMorning = (ListView) findViewById(R.id.listView_calendar_daily_morning);
        listViewMidday = (ListView) findViewById(R.id.listView_calendar_daily_midday);
        listViewEvening = (ListView) findViewById(R.id.listView_calendar_daily_evening);
        datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        spinner = (Spinner) findViewById(R.id.spinner);
        initializeSpinner();
        pickedDate = getIntent().getStringExtra("date");
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        fillListViews();
        initializeClickListenerForDatePickerButton();
    }

    private void fillListViews(){
        dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<Entry> allEntries = dataSource.getAllEntries(pickedDate);
        ArrayList<Entry> morningEntries = new ArrayList<>(), middayEntries = new ArrayList<>(), eveningEntries = new ArrayList<>();
        for(int i = 0; i<allEntries.size(); i++){
            String currentDaytime = dateTimePicker.getDaytime(allEntries.get(i).getTime());
            if(currentDaytime == "Morgen"){
                morningEntries.add(allEntries.get(i));
            }
            if(currentDaytime == "Mittag"){
                middayEntries.add(allEntries.get(i));
            }
            if(currentDaytime == "Abend"){
                eveningEntries.add(allEntries.get(i));
            }
        }

        final ArrayAdapter<Entry> adapter_morning = new ArrayAdapter<Entry>(this,
                R.layout.listentry_simple_listview, morningEntries);
        listViewMorning.setAdapter(adapter_morning);

        final ArrayAdapter<Entry> adapter_midday = new ArrayAdapter<Entry>(this,
                R.layout.listentry_simple_listview, middayEntries);
        listViewMidday.setAdapter(adapter_midday);

        final ArrayAdapter<Entry> adapter_evening = new ArrayAdapter<Entry>(this,
                R.layout.listentry_simple_listview, eveningEntries);
        listViewEvening.setAdapter(adapter_evening);

    }


    private void initializeSpinner(){
        ArrayAdapter<String>spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_simple_listview, spinnerList);
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
