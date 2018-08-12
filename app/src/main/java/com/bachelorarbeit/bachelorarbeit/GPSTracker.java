package com.bachelorarbeit.bachelorarbeit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GPSTracker extends AppCompatActivity{

    BufferedOutputStream bOut;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_tracker);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            bOut = new BufferedOutputStream(openFileOutput("location.dat", MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            try {
                locationManager.requestLocationUpdates("gps", 60000, 1, locationListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Location l = getLastKnownLocation();
            if (l != null) {
                printSaveLocation(l);
            }
        }
    }

    private Location getLastKnownLocation() {
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

    private void printSaveLocation(Location l) {


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

  /*  public Double[] getLocation(){
        Location location  = getLastKnownLocation();
        Double[] locationDouble = new Double[2];
        locationDouble[0] = location.getLatitude();
        locationDouble[1] = location.getLongitude();
        return locationDouble;
    }
    */

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location l) {
            printSaveLocation(l);
        }
        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
}