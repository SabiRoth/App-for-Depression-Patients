package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ActivitiesActivity extends AppCompatActivity {

    //TODO: Zusätzliche Eingaben
    //TODO: Scrollbar

    public ListView socialActivitiesListView;
    public ListView sportActivititesListView;
    public ListView obligationsActivitiesListView;
    public EditText editTextField;
    public String[] socialActivitiesArrayString = {"Mit Freunden/Bekannten getroffen", "Mit Freunden/Bekannten telefoniert"};
    public String[] sportActivitiesArrayString = {"Spazieren gehen", "Schwimmen"};
    public String[] obligationsActivitiesArrayString = {"Zur Arbeit gegangen", "Einkaufen gegangen"};
    public ArrayList<String> allSelectedEntries = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        Button nextButton = (Button)findViewById(R.id.button_activities_next);
        Button saveButton = (Button)findViewById(R.id.saveButton_activities);
        editTextField = (EditText)findViewById(R.id.editText_activities);
        socialActivitiesListView = (ListView)findViewById(R.id.listView_activities1);
        sportActivititesListView = (ListView)findViewById(R.id.listView_activities2);
        obligationsActivitiesListView = (ListView)findViewById(R.id.listView_activities3);

        final ArrayAdapter<String> adapter_socialActivities = new ArrayAdapter<String>(this,
                R.layout.listentry_sensitivities, socialActivitiesArrayString);

        socialActivitiesListView.setAdapter(adapter_socialActivities);
        socialActivitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                entryClicked(adapter_socialActivities.getItem(i), view);
            }
        });

        final ArrayAdapter<String> adapter_sportActivities = new ArrayAdapter<String>(this,
                R.layout.listentry_sensitivities, sportActivitiesArrayString);

        sportActivititesListView.setAdapter(adapter_sportActivities);
        sportActivititesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                entryClicked(adapter_sportActivities.getItem(i), view);
            }
        });

        final ArrayAdapter<String> adapter_obligationsActivities = new ArrayAdapter<String>(this,
                R.layout.listentry_sensitivities, obligationsActivitiesArrayString);

        obligationsActivitiesListView.setAdapter(adapter_obligationsActivities);
        obligationsActivitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                entryClicked(adapter_obligationsActivities.getItem(i), view);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButtonClicked();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonClicked();
            }
        });
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

    private void nextButtonClicked(){

        Intent i = new Intent(this, PlacesActivity.class);
        i.putExtra("selectedActivities", allSelectedEntries);
        startActivity(i);
    }

    private void saveButtonClicked(){
        allSelectedEntries.add(editTextField.getText().toString());
        CharSequence text = "Gespeichert";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
        editTextField.setText("");
    }
}