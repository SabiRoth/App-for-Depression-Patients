package com.bachelorarbeit.bachelorarbeit;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap googleMap;

    private static final String MAP_VIEW_BUNDLE_KEY = "@string/google_android_map_api_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.map);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap gm) {
        googleMap = gm;
        googleMap.setMinZoomPreference(16);
        String[] coordinates = getCoordinates();
        setMarkers(gm);
        LatLng ny = new LatLng(Double.valueOf(coordinates[2]), Double.valueOf(coordinates[1]));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        googleMap.stopAnimation();
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    public String[] getCoordinates(){
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        return dataSource.getLastMovementEntry();
    }

    private void setMarkers(GoogleMap gm){
        MarkerOptions mo = new MarkerOptions();
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<String[]> allMovementEntries = dataSource.getAllMovementEntries();
        if(allMovementEntries.size()!= 0){
            for(int i = 0; i<allMovementEntries.size(); i++){
                LatLng latLng = new LatLng(Double.valueOf(allMovementEntries.get(i)[2]), Double.valueOf(allMovementEntries.get(i)[1]));
                mo.position(latLng);
                gm.addMarker(mo);
            }
        }
    }
}