package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    LinearLayout layoutCalendarWeekly;
    int check = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly);
        dataSource = new dataSource(this);
        dataSource.open();
        String mainSymptomsString  = dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptoms));
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
        Button sendButton = (Button) findViewById(R.id.button_sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButtonClicked();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        initializeSpinner();
        pickedDate = getIntent().getStringExtra(getResources().getString(R.string.key_date));
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        getWeekView();
    }


    private void getWeekView(){
        Calendar calendar = Calendar.getInstance();
        layoutCalendarWeekly = (LinearLayout)findViewById(R.id.layout_listentry_calendar_weekly);
        for(int i = 0; i<DAYS_OF_WEEK; i++){
            TextView header_textView = new TextView(this);
            header_textView.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMANY));
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            if(i==0){
                header_textView.setText(getResources().getString(R.string.today));
            }
            if(i==1){
                header_textView.setText(getResources().getString(R.string.yesterday));
            }
            setHeaderTextViewStyle(header_textView);
            layoutCalendarWeekly.addView(header_textView);
            TextView score_textView = new TextView(this);
            setContentTextViewStyle(score_textView);

            ArrayList<String[]> scoresOfDate = dataSource.getMainSymptomScoresViaNameAndDate(getResources().getString(R.string.key_score), newDate);
            if (scoresOfDate.size()!=0) {
                int average = calculateAverage(scoresOfDate);
                score_textView.setText(getResources().getString(R.string.average) + " " + average);
            }
            else{
                score_textView.setText(getResources().getString(R.string.noEntry));
                score_textView.setTextAppearance(getApplicationContext(), R.style.hintTextViewCalendar);
            }

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

            TextView line_textView = new TextView(this);
            line_textView.setText(getResources().getString(R.string.separation));
            LinearLayout.LayoutParams paramsForTextView = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsForTextView.setMargins(15, 0, 0, 5);
            if(i ==DAYS_OF_WEEK-1) {
                line_textView.setText("");
            }
            line_textView.setLayoutParams(paramsForTextView);
            layoutCalendarWeekly.addView(line_textView);

            newDate = dateTimePicker.getTheDayBefore(newDate);
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

    private void sendButtonClicked(){
        final int childCount = layoutCalendarWeekly.getChildCount();
        String viewContents = new String();
        for (int i = 0; i < childCount; i++) {
            View v = layoutCalendarWeekly.getChildAt(i);
            TextView temp = (TextView) v;
            viewContents = viewContents + " " + temp.getText().toString();
        }
        FragmentManager fm = getSupportFragmentManager();
        PopUp_Send popUp_send = PopUp_Send.newInstance(viewContents);
        popUp_send.show(fm, getResources().getString(R.string.key_popUp_send));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 1) {   //Source: https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization
            if (parent.getItemAtPosition(position).toString().equals(getResources().getString(R.string.key_dayView))) {
                Intent i = new Intent(getApplicationContext(), CalendarActivityDaily.class);
                this.finish();
                startActivity(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    @Override
    public void onBackPressed(){
        this.finish();
    }
}
