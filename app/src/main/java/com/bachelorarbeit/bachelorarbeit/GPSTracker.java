package com.bachelorarbeit.bachelorarbeit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.gms.maps.MapView;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GPSTracker extends AppCompatActivity{


    BufferedOutputStream bOut;
    LocationManager locationManager;
    dataSource dataSource;
    DateTimePicker dateTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_show_db_entry);

        dateTimePicker = DateTimePicker.getInstance();
        dataSource = new dataSource(this);
        dataSource.open();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            bOut = new BufferedOutputStream(openFileOutput("location.dat", MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location l) {
                locationInDB(l);
            }
            public void onProviderDisabled(String provider){}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            try {
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 50, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, locationListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Location l = getLastKnownLocation();
            if (l != null) {
                locationInDB(l);
            }
        }
        dataSource.close();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private Location getLastKnownLocation() {
        //Source: https://www.programcreek.com/java-api-examples/?class=android.location.LocationManager&method=getProviders
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
        }
        return bestLocation;
    }

   /* private void printSaveLocation(Location l) {
        TextView tv_longitude = (TextView)findViewById(R.id.longitude);
        TextView tv_latitude = (TextView)findViewById(R.id.latidude);


        tv_longitude.setText(String.valueOf(l.getLatitude()));
        tv_latitude.setText(String.valueOf(l.getLongitude()));

        try {
            String temp = String.valueOf(l.getTime()) + ":" + String.valueOf(l.getLatitude()) + "," + String.valueOf(l.getLongitude() + "");
            bOut.write(temp.getBytes());
        }
         catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
*/

    private void locationInDB(Location l){
        dataSource.open();
        String[] lastMovementEntry = dataSource.getLastMovementEntry(dateTimePicker.getCurrentDate());
        //erst ab dritter Nachkommastelle "wirklich" eine Bewegung drin TODO Ändern zu 6!
        if(lastMovementEntry!=null) {
            if (lastMovementEntry[1].substring(0, 7).equals((String.valueOf(l.getLongitude())).substring(0, 7)) || lastMovementEntry[2].substring(0, 7).equals((String.valueOf(l.getLatitude())).substring(0, 7))) {
                return;
            }
        }
        dataSource.createMovementEntry(dateTimePicker.getCurrentDate(), (String.valueOf(l.getLongitude())).substring(0, 10), (String.valueOf(l.getLatitude())).substring(0,10));
        dataSource.close();
    }

   /* private void showLocationFromDB(){
        dataSource.open();
        ListView entries = (ListView)findViewById(R.id.db_listView);
        Context context = this;
        if(context!= null){
            ArrayList<String[]> dbEntries = dataSource.getAllMovementEntriesViaDate(dateTimePicker.getCurrentDate());
            entryMovementListingArrayAdapter adapter = new entryMovementListingArrayAdapter(context, dbEntries);
            entries.setAdapter(adapter);
        }
        dataSource.close();
    }

    */
  /*  public Double[] getLocation(){
        Location location  = getLastKnownLocation();
        Double[] locationDouble = new Double[2];
        locationDouble[0] = location.getLatitude();
        locationDouble[1] = location.getLongitude();
        return locationDouble;
    }
    */


}