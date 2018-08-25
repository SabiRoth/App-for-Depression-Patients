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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ActivitiesActivity extends AppCompatActivity {

    public ListView socialActivitiesListView;
    public ListView sportActivititesListView;
    public ListView relaxationActivitiesListView;
    public ListView obligationsActivitiesListView;
    public ListView ownEntriesActivitiesListView;
    public EditText editTextField;
    public ArrayList<String> allSelectedEntries;
    Button nextButton;
    dataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        String[] socialActivitiesArrayString = getResources().getStringArray(R.array.activities_social);
        String[] sportActivitiesArrayString = getResources().getStringArray(R.array.activities_sport);
        String[] relaxationActivitiesArrayString = getResources().getStringArray(R.array.activities_relaxation);
        String[] obligationsActivitiesArrayString = getResources().getStringArray(R.array.activities_obligations);
        allSelectedEntries = new ArrayList<String>();
        nextButton = (Button)findViewById(R.id.button_activities_next);
        Button saveButton = (Button)findViewById(R.id.saveButton_activities);
        editTextField = (EditText)findViewById(R.id.editText_activities);
        socialActivitiesListView = (ListView)findViewById(R.id.listView_activities_social);
        sportActivititesListView = (ListView)findViewById(R.id.listView_activities_sport);
        relaxationActivitiesListView = (ListView)findViewById(R.id.listView_activities_relaxation);
        obligationsActivitiesListView = (ListView)findViewById(R.id.listView_activities_obligations);
        ownEntriesActivitiesListView = (ListView) findViewById(R.id.listView_activities_own_entries);
        dataSource = new dataSource(this);
        dataSource.open();

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

        final ArrayAdapter<String> adapter_relaxationActivities = new ArrayAdapter<String>(this,
                R.layout.listentry_sensitivities, relaxationActivitiesArrayString);

        relaxationActivitiesListView.setAdapter(adapter_relaxationActivities);
        relaxationActivitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                entryClicked(adapter_relaxationActivities.getItem(i), view);
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

        ArrayList<String> allOwnActivities = dataSource.getAllOwnActivities();
        if(allOwnActivities.size()>0){
            final ArrayAdapter<String> adapter_ownActivities = new ArrayAdapter<String>(this,
                    R.layout.listentry_sensitivities, allOwnActivities);
            ownEntriesActivitiesListView.setAdapter(adapter_ownActivities);
            ownEntriesActivitiesListView.setVisibility(View.VISIBLE);
            ownEntriesActivitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    entryClicked(adapter_ownActivities.getItem(i), view);
                }
            });
        }


        nextButton.setEnabled(false);
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
            nextButton.setEnabled(true);
        }
        if(allSelectedEntries.size()==0){
            nextButton.setEnabled(false);
        }
        CheckedTextView checkedTextView = (CheckedTextView) viewListEntry;
        checkedTextView.toggle();
        checkedTextView.refreshDrawableState();
    }

    private void nextButtonClicked(){
        if(allSelectedEntries.size()!=0){
            Intent i;
            i = new Intent(this, PlacesActivity.class);
            i.putExtra(getResources().getString(R.string.key_selectedActivities), allSelectedEntries);
            i.putExtra(getResources().getString(R.string.key_sensitivitiesString), getIntent().getStringExtra("sensitivitiesString"));
            startActivity(i);
        }
    }

    private void saveButtonClicked(){
        if(!(editTextField.getText().toString().equals(""))) {
            allSelectedEntries.add(editTextField.getText().toString());
            dataSource.createActivityEntry(editTextField.getText().toString());
            CharSequence text = getResources().getString(R.string.toast);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            editTextField.setText("");
            nextButton.setEnabled(true);
        }
    }
}