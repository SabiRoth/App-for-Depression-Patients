package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckedTextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class SensitivitiesActivity extends AppCompatActivity {

    public ArrayList<String> allSelectedEntries = new ArrayList<String>();
    public ArrayList<String[]> arrayListStringArrays = new ArrayList<>();
    int counter = 0;
    public ListView listViewSensitivities;
    public TextView header;
    public String[] headerString;
    public Button buttonSensitivitiesNext;
    dataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitivities);
        header = (TextView)findViewById(R.id.textView_sensitivities_header);
        listViewSensitivities = (ListView)findViewById(R.id.listViewCheckboxes);
        buttonSensitivitiesNext = (Button)findViewById(R.id.button_sensitivies_next);
        headerString = getResources().getStringArray(R.array.sensivities_header);

        String[] somatic = getResources().getStringArray(R.array.sensivities_somatic);
        String[] psychic = getResources().getStringArray(R.array.sensivities_psychic);
        String[] social = getResources().getStringArray(R.array.sensivities_social);
        String[] ownEntries;

        arrayListStringArrays.add(somatic);
        arrayListStringArrays.add(psychic);
        arrayListStringArrays.add(social);


        dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<String> allOwnSensitivities = dataSource.getAllOwnSensitivities();
        if(allOwnSensitivities.size() != 0){
            ownEntries = new String[allOwnSensitivities.size()];
            for(int i = 0; i<allOwnSensitivities.size(); i++){
                ownEntries[i] = allOwnSensitivities.get(i);
            }
        }
        else{
            ownEntries = new String[0];
        }
        arrayListStringArrays.add(ownEntries);
        buildActualPage();

        buttonSensitivitiesNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 nextButtonClicked();
            }
        });
    }


    private void buildActualPage(){
         header.setText(headerString[counter]);
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 R.layout.listentry_sensitivities_activities, arrayListStringArrays.get(counter));

         listViewSensitivities.setAdapter(adapter);
         listViewSensitivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 entryClicked(adapter.getItem(i), view);
             }
         });
         if(counter == arrayListStringArrays.size()-1){
             String temp = getIntent().getStringExtra(getResources().getString(R.string.key_intentSource));
             if(getIntent().getStringExtra(getResources().getString(R.string.key_intentSource)).equals(getResources().getString(R.string.key_home_value))){
                 buttonSensitivitiesNext.setText(getResources().getString(R.string.end_button));
             }
             final EditText editText = (EditText)findViewById(R.id.editText_sensitivities);
             Button saveButtonOwnEntries = (Button)findViewById(R.id.saveButton_sensitivities);
             editText.setVisibility(View.VISIBLE);
             saveButtonOwnEntries.setVisibility(View.VISIBLE);
             saveButtonOwnEntries.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     saveButtonOwnEntriesClicked(editText);
                 }
             });
         }
    }

    private void entryClicked(String clickedEntry, View viewListEntry){
        if (allSelectedEntries.contains(clickedEntry)) {
            allSelectedEntries.remove(clickedEntry);
        }
        else{
            allSelectedEntries.add(clickedEntry);
        }
        CheckedTextView checkedTextView = (CheckedTextView) viewListEntry;
        checkedTextView.toggle();
        checkedTextView.refreshDrawableState();
    }

    private void saveButtonOwnEntriesClicked(EditText editText){
        boolean alreadySelected = false;
        String input = editText.getText().toString();
        if(!(input.equals(""))) {
            for(int i = 0; i<allSelectedEntries.size(); i++){
                if(allSelectedEntries.get(i).equals(input)){
                    alreadySelected = true;
                }
            }

            if(!alreadySelected) {
                allSelectedEntries.add(input);
                checkAndSaveEntry(input);
                CharSequence text = input +  " " + getResources().getString(R.string.toast_end);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
            editText.setText("");
        }

    }

    private void checkAndSaveEntry(String entry){
        ArrayList<String> allEntries = dataSource.getAllOwnSensitivities();
        for(int i= 0; i<allEntries.size(); i++){
            if(allEntries.get(i).equals(entry)){
                return;
            }
        }
        dataSource.createSensitivityEntry(entry);
    }

    private void nextButtonClicked(){
       counter++;
       if(counter<arrayListStringArrays.size()) {
           buildActualPage();
       }
       if(counter==arrayListStringArrays.size()){
           String[] temp = new String[allSelectedEntries.size()];
           String sensitivitiesString = Arrays.toString(allSelectedEntries.toArray(temp));
           if(sensitivitiesString.equals("[]")){
               sensitivitiesString = null;
           }

           if(getIntent().getStringExtra(getResources().getString(R.string.key_intentSource)).equals(getResources().getString(R.string.key_home_value))){
               DateTimePicker dateTimePicker = DateTimePicker.getInstance();
               if(sensitivitiesString!=null){
                   sensitivitiesString = sensitivitiesString.substring(1, sensitivitiesString.length()-1);
                   dataSource.createEntry(sensitivitiesString, null, null, dateTimePicker.getCurrentDate(), dateTimePicker.getCurrentTime(), dateTimePicker.getDaytime());
               }
               Intent i = new Intent(this, HomeActivity.class);
               startActivity(i);
           }
           else {
               Intent i = new Intent(this, ActivitiesActivity.class);
               i.putExtra(getResources().getString(R.string.key_sensitivitiesString), sensitivitiesString);
               i.putExtra(getResources().getString(R.string.key_intentSource), getIntent().getStringExtra(getResources().getString(R.string.key_intentSource)));
               startActivity(i);
           }
       }
    }
}