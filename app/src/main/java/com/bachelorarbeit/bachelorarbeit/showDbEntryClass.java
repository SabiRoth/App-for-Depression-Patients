package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class showDbEntryClass extends AppCompatActivity {


    private dataSource dataSource;
    private ListView entriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db_entry);

        entriesListView = (ListView)findViewById(R.id.db_listView);
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
