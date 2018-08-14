package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;


public class MapView extends Fragment {

   MapView mapView;
   GoogleMap googleMap;

   public MapView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_view, container, false);
        //getActivity().setTitle("Bewegungsprofil");
        /*mapView= (MapView) view.findViewById(R.id.map);


        mapView.onCreate(savedInstanceState);
        if (mapView!= null){
            googleMap = mapView.getMap();
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(55.854049, 13.661331)));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                return view;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            MapsInitializer.initialize(this.getActivity());
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(55.854049, 13.661331));
            LatLngBounds bounds = builder.build();
            int padding = 0;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.moveCamera(cameraUpdate);

        }
        */

        return view;
    }


    @Override
    public void onResume(){
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
        }



}
