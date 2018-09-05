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

import java.util.List;

public class GPSTracker extends AppCompatActivity{

    private LocationManager locationManager;
    private dataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new dataSource(this);
        dataSource.open();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 40, locationListener);
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

    /*
       Save location in database if it has changed at least one of the first 7 numbers.
       The last numbers of the location means not enough movement to be important for the app (movement in the flat/house shouldn't be saved).
     */
    private void locationInDB(Location l){
        dataSource.open();
        DateTimePicker dateTimePicker = new DateTimePicker();
        String[] lastMovementEntry = dataSource.getLastMovementEntry(dateTimePicker.getCurrentDate());
        if(lastMovementEntry!=null) {
            if (lastMovementEntry[1].substring(0, 7).equals((String.valueOf(l.getLongitude())).substring(0, 7)) && lastMovementEntry[2].substring(0, 7).equals((String.valueOf(l.getLatitude())).substring(0, 7))) {
                return;
            }
        }
        dataSource.createMovementEntry(dateTimePicker.getCurrentDate(), (String.valueOf(l.getLongitude())).substring(0, 10), (String.valueOf(l.getLatitude())).substring(0,10));
        dataSource.close();
    }
}