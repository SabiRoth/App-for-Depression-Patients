package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class showDbClass extends AppCompatActivity {


    private dataSource dataSource;
    private ListView entriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        entriesListView = (ListView)findViewById(R.id.calendar_listView);
        dataSource = new dataSource(this); //TODO: Context?
        dataSource.open();
        showAllEntries();
    }

    private void showAllEntries(){
        Context context = this;
        if(context != null){
            ArrayList<Entry> entryArrayList = dataSource.getAllEntries();
            entryListingArrayAdapter adapter = new entryListingArrayAdapter(context, entryArrayList);
            entriesListView.setAdapter(adapter);
        }

        dataSource.close();
    }


}
