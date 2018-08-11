package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class entryListingArrayAdapter extends ArrayAdapter<Entry> {

    private final Context context;
    private final ArrayList<Entry> entryArrayList;

  public entryListingArrayAdapter(Context context, ArrayList<Entry> entryArrayList){
      super(context, -1, entryArrayList);
      this.context = context;
      this.entryArrayList = entryArrayList;
  }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listentry_database, parent, false);
        TextView textViewSensibilities = (TextView) rowView.findViewById(R.id.entry_listing_sensibilities);
        TextView textViewActivities = (TextView) rowView.findViewById(R.id.entry_listing_activities);
        TextView textViewPlaces = (TextView) rowView.findViewById(R.id.entry_listing_places);
        TextView textViewDates = (TextView) rowView.findViewById(R.id.entry_listing_dates);
        TextView textViewTime = (TextView) rowView.findViewById(R.id.entry_listing_time);
        textViewSensibilities.setText(entryArrayList.get(position).getSensitivies());
        textViewActivities.setText(entryArrayList.get(position).getActivities());
        textViewPlaces.setText(entryArrayList.get(position).getPlaces());
        textViewDates.setText(entryArrayList.get(position).getDate());
        textViewTime.setText(entryArrayList.get(position).getTime());

        /*dataSource = new dataSource(getContext());
        dataSource.open();
        final List<Entry> EntryList = dataSource.getAllEntries();
        */

        return rowView;

    }

    @Override
    public int getCount() {
        return entryArrayList.size();
    }
}
