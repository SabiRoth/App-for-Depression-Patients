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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



public class CalendarActivityWeekly  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final int DAYS_OF_WEEK = 7;
    Spinner spinner;
    Button datePickerButton;
    dataSource dataSource;
    DateTimePicker dateTimePicker;
    String pickedDate;
    String[] spinnerList;
    String[] mainSymptomsArray;
    String newDate;
    int check = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly);
        dataSource = new dataSource(this);
        dataSource.open();
        String mainSymptomsString  = dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptomsinSettingsTable));
        if(mainSymptomsString==null || mainSymptomsString.equals("")){
            mainSymptomsArray = new String[0];
        }
        else{
            mainSymptomsArray = mainSymptomsString.split(",");
        }
        spinnerList = getResources().getStringArray(R.array.spinner_view_weekly);
        dateTimePicker = DateTimePicker.getInstance();
        newDate = dateTimePicker.getCurrentDate();
        datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        datePickerButton.setVisibility(View.GONE);
        spinner = (Spinner) findViewById(R.id.spinner);
        initializeSpinner();
        pickedDate = getIntent().getStringExtra(getResources().getString(R.string.key_date));
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
      //  datePickerButton.setText(pickedDate);
        //initializeClickListenerForDatePickerButton();
        getWeekView();
    }




    private void getWeekView(){
        Calendar calendar = Calendar.getInstance();
        LinearLayout layoutCalendarWeekly = (LinearLayout)findViewById(R.id.layout_listentry_calendar_weekly);
        for(int i = 0; i<DAYS_OF_WEEK; i++){
            TextView header_textView = new TextView(this);
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            header_textView.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMANY));
            if(i==0){
                header_textView.setText(getResources().getString(R.string.today));
            }
            if(i==1){
                header_textView.setText(getResources().getString(R.string.yesterday));
            }
            setHeaderTextViewStyle(header_textView);
            layoutCalendarWeekly.addView(header_textView);
            TextView score_textView = new TextView(this);

            ArrayList<String[]> scoresOfDate = dataSource.getMainSymptomScoresViaNameAndDate(getResources().getString(R.string.key_score), newDate);
            if (scoresOfDate.size()!=0) {
                int average = calculateAverage(scoresOfDate);
                score_textView.setText(getResources().getString(R.string.average) + " " + average);
            }
            else{
                score_textView.setText(getResources().getString(R.string.noEntry));
            }
            setContentTextViewStyle(score_textView);
            layoutCalendarWeekly.addView(score_textView);

            if(mainSymptomsArray.length!=0) {
                for(int j = 0; j < mainSymptomsArray.length; j++) {
                    ArrayList<String[]> entry = dataSource.getMainSymptomScoresViaNameAndDate(mainSymptomsArray[j], newDate);
                    if(entry.size()!=0){
                        TextView mainSymptomsScore_textView = new TextView(this);
                        int average = calculateAverage(entry);
                        if(j ==0){
                            mainSymptomsScore_textView.setText(mainSymptomsArray[j] + ": " + average);
                        }
                        else{
                            mainSymptomsScore_textView.setText(mainSymptomsArray[j].substring(1) + ": " + average);
                        }
                        setContentTextViewStyle(mainSymptomsScore_textView);
                        layoutCalendarWeekly.addView(mainSymptomsScore_textView);
                    }

                }
            }
           newDate = calculateNewDate(newDate);

        }
    }

    private String calculateNewDate(String latestDate){
        Integer currentDayInt = Integer.parseInt(latestDate.substring(0,2));
        if(currentDayInt>1) {
            currentDayInt -= 1;
            return currentDayInt.toString() + dateTimePicker.getCurrentDate().substring(2, dateTimePicker.getCurrentDate().length());
        }
        else{
            Integer currentMonthInt = Integer.parseInt(dateTimePicker.getCurrentDate().substring(3,5))-1;
            switch (currentMonthInt){
                case 1: currentDayInt = 31;
                case 2: currentDayInt = 28;
                case 3: currentDayInt = 31;
                case 4: currentDayInt = 30;
                case 5: currentDayInt = 31;
                case 6: currentDayInt = 30;
                case 7: currentDayInt = 31;
                case 8: currentDayInt = 31;
                case 9: currentDayInt = 30;
                case 10: currentDayInt = 31;
                case 11: currentDayInt = 30;
                case 12: currentDayInt = 31;
            }

            String currentDayString = currentDayInt.toString();
            if(currentDayString.length()==1){
                currentDayString = "0" + currentDayString;
            }
            String currentMonthString = currentMonthInt.toString();
            if(currentMonthString.length()==1){
                currentMonthString = "0" + currentMonthString;
            }

            return currentDayString + "." + currentMonthString + dateTimePicker.getCurrentDate().substring(dateTimePicker.getCurrentDate().length()-5, dateTimePicker.getCurrentDate().length());
        }
    }


    private int calculateAverage(ArrayList<String[]> scoresOfDate){
        float average = 0;
        for(int i = 0; i<scoresOfDate.size(); i++){
            average+=Integer.parseInt(scoresOfDate.get(i)[1]);
        }
        return Math.round(average / scoresOfDate.size());
    }


    private void setHeaderTextViewStyle(TextView textView){
        textView.setTextAppearance(getApplicationContext(), R.style.headerTextView);
        LinearLayout.LayoutParams paramsForTextView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsForTextView.setMargins(15,40,15,15);
        textView.setLayoutParams(paramsForTextView);
    }

    private void setContentTextViewStyle(TextView textView){
        textView.setTextAppearance(getApplicationContext(), R.style.normalText);
        LinearLayout.LayoutParams paramsForTextView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsForTextView.setMargins(15,5,15,5);
        textView.setLayoutParams(paramsForTextView);
    }

    private void initializeSpinner(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 1) {   //Source: https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization
            if (parent.getItemAtPosition(position).toString().equals("Tagesansicht")) {
                Intent i = new Intent(getApplicationContext(), CalendarActivityDaily.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




   /* private void initializeClickListenerForDatePickerButton(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivityWeekly.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        Intent reloadIntent = new Intent(CalendarActivityWeekly.this, CalendarActivityDaily.class);
                        reloadIntent.putExtra(getResources().getString(R.string.key_date), newDate);
                        CalendarActivityWeekly.this.finish();
                        startActivity(reloadIntent);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }
    */
}
