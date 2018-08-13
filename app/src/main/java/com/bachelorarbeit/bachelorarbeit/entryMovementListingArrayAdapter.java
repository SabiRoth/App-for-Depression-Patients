package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class entryMovementListingArrayAdapter extends ArrayAdapter<String[]> {

    private final Context context;
    private final ArrayList<String[]> entryArrayList;

    public entryMovementListingArrayAdapter(Context context, ArrayList<String[]> entryArrayList){
        super(context, -1, entryArrayList);
        this.context = context;
        this.entryArrayList = entryArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listentry_database_movement, parent, false);
        TextView textViewDate = (TextView) rowView.findViewById(R.id.entry_listing_date);
        TextView textViewLongitude = (TextView) rowView.findViewById(R.id.entry_listing_longitude);
       // TextView textViewLatitude = (TextView) rowView.findViewById(R.id.entry_listing_latitude);
        textViewDate.setText(entryArrayList.get(position)[0]);
        textViewLongitude.setText(entryArrayList.get(position)[1]);
      //  textViewLatitude.setText(entryArrayList.get(position)[2]);

        return rowView;

    }

    @Override
    public int getCount() {
        return entryArrayList.size();
    }
}

