package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.Calendar;


public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private GoogleMap googleMap;
    private DateTimePicker dateTimePicker;
    private Button datePickerButton;

    private static final String MAP_VIEW_BUNDLE_KEY = "@string/google_android_map_api_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        dateTimePicker = DateTimePicker.getInstance();
        datePickerButton = (Button) findViewById(R.id.button_date_picker);
        initializeClickListenerForDatePickerButton();
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
        setPolyline(gm);
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void setPolyline(GoogleMap gm){
        PolylineOptions polylineOptions = new PolylineOptions();
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<LatLng> points = new ArrayList<>();
        String pickedDate = getIntent().getStringExtra("date");
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        datePickerButton.setText(pickedDate);
        ArrayList<String[]> allMovementEntries = dataSource.getAllMovementEntriesViaDate(pickedDate);
        if(allMovementEntries.size()!= 0){
            if(allMovementEntries.size()==1){
                MarkerOptions mO = new MarkerOptions();
                mO.position(new LatLng(Double.valueOf(allMovementEntries.get(0)[2]),Double.valueOf(allMovementEntries.get(0)[1])));
                gm.addMarker(mO);
            }

            for(int i = 0; i<allMovementEntries.size(); i++){
                LatLng latLng = new LatLng(Double.valueOf(allMovementEntries.get(i)[2]), Double.valueOf(allMovementEntries.get(i)[1]));
                points.add(latLng);
                gm.moveCamera(CameraUpdateFactory.newLatLng(latLng));//TODO: BESSERE LÖSUNG FÜR KAMERA!
            }
        }

        else{
            TextView noDataTextView = (TextView)findViewById(R.id.map_textView);
            noDataTextView.setVisibility(View.VISIBLE);
        }
        polylineOptions.addAll(points);
        polylineOptions.visible(true);
        polylineOptions.color(getResources().getColor(R.color.colorPrimaryDark));
        polylineOptions.width(5);
        polylineOptions.startCap(new RoundCap());
        polylineOptions.jointType(2);
        gm.addPolyline(polylineOptions);
    }

    private void initializeClickListenerForDatePickerButton(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapViewActivity.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                Intent reloadIntent = new Intent(MapViewActivity.this, MapViewActivity.class);
                                reloadIntent.putExtra("date", newDate);
                                MapViewActivity.this.finish();
                                startActivity(reloadIntent);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}