package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class PlacesActivity extends AppCompatActivity {


    public ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
    public ArrayList<String> selectedActivites = new ArrayList<String>();
    public String sensitivitiesString;
    private dataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Button endButton = (Button)findViewById(R.id.button_places_end);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endButtonClicked();
            }
        });;
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_listentry_places);
        Intent intent = getIntent();
        selectedActivites = intent.getStringArrayListExtra("selectedActivities");
        sensitivitiesString = intent.getStringExtra("sensitivitiesString");
        LinearLayout.LayoutParams paramsForTextView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
         );
        paramsForTextView.setMargins(25,40,25,15);

        LinearLayout.LayoutParams paramsforEditView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsforEditView.setMargins(25, 0, 25, 35);

        if(selectedActivites.size()==0){
            endButtonClicked();
            return;
        }

        for(int i = 0; i<selectedActivites.size(); i++){
            TextView entry = new TextView(getApplicationContext());
            entry.setLayoutParams(paramsForTextView);
            buildTextView(entry, selectedActivites.get(i));
            entry.setText(selectedActivites.get(i));
            layout.addView(entry);
            EditText editText = new EditText(getApplicationContext());
            editText.setLayoutParams(paramsforEditView);
            buildEditText(editText);
            layout.addView(editText);
            editTextArrayList.add(editText);
        }
    }

    private void buildTextView(TextView entry, String textContent){
        entry.setText(textContent);
        entry.setTextColor(getResources().getColor(android.R.color.black));
        entry.setTextSize(23);
    }

    private void buildEditText(EditText editText){
        editText.setHint(R.string.places_edittext);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.getBackground().setColorFilter(getResources().getColor(R.color.button_pressed), PorterDuff.Mode.SRC_IN);
    }

    private void endButtonClicked(){
        ArrayList<String> placesArrayList = new ArrayList<String>();
        if(editTextArrayList.size()!=0) {
            for (int i = 0; i < editTextArrayList.size(); i++) {
                if (!(editTextArrayList.get(i).getText().toString().equals(""))) {
                    placesArrayList.add(editTextArrayList.get(i).getText().toString());
                }
            }
        }

        String[] temp = new String[selectedActivites.size()];
        String activitiesString = Arrays.toString(selectedActivites.toArray(temp));
        if(activitiesString == "[]"){
            activitiesString = null;
        }
        String[] temp2 = new String[placesArrayList.size()];
        String placesString = Arrays.toString(placesArrayList.toArray(temp2));
        if(placesString == "[]"){
            placesString = null;
        }

        if(activitiesString != null || sensitivitiesString != null){
            if(sensitivitiesString!=null){
                sensitivitiesString = sensitivitiesString.substring(1, sensitivitiesString.length()-1);
            }
            if(activitiesString!=null){
                activitiesString = activitiesString.substring(1, activitiesString.length()-1);
            }
            if(placesString!=null){
                placesString = placesString.substring(1, placesString.length()-1);
            }
            DateTimePicker  dateTimePicker = DateTimePicker.getInstance();
            dataSource = new dataSource(this);
            dataSource.open();
            dataSource.createEntry(sensitivitiesString, activitiesString, placesString, dateTimePicker.getCurrentDate(),  dateTimePicker.getCurrentTime(), dateTimePicker.getDaytime());
        }

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}