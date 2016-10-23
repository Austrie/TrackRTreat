package com.trackrtreat.trackrtreat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;

public class MainActivity extends AppCompatActivity {
        private MapboxMap mMapboxMap;
        private MapView mMapView;

        PopupWindow pWindow;
        LayoutInflater layoutInflator;
        ViewGroup container;
        CoordinatorLayout rLayout;
        GPSTracker gpsTracker;
        double lat, lon;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

            mMapView = (MapView) findViewById(R.id.mapquestMapView);
            mMapView.onCreate(savedInstanceState);
            
            gpsTracker = new GPSTracker(this);

            if(!gpsTracker.canGetLocation()) {
                gpsTracker.showSettingsAlert();
            } else {
                gpsTracker.getLocation();
                lat = gpsTracker.getLatitude();
                lon = gpsTracker.getLongitude();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMapboxMap = mapboxMap;
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 11));
                    addMarker(mMapboxMap);
                }
            });

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupWindow();
                }
            });
        }

        public void showPopupWindow() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting DialogHelp Title
            alertDialog.setTitle("Add Location");

            // Setting DialogHelp Message
            alertDialog
                    .setMessage("Do you want to add your current location to our map, so users can visit your house for candy on Halloween?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //To-Do
                        }
                    });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //To-Do
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }

        private void addMarker(MapboxMap mapBoxMap) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, lon));
            markerOptions.title("Current Location");
            markerOptions.snippet("This is your current location");
            mapBoxMap.addMarker(markerOptions);
        }
    }