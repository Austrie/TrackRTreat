package com.trackrtreat.trackrtreat;

import android.content.DialogInterface;
import android.support.v4.util.LongSparseArray;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
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

import com.mapbox.mapboxsdk.annotations.Annotation;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;
import com.trackrtreat.trackrtreat.EditedMapQuestFiles.Icon2;
import com.trackrtreat.trackrtreat.EditedMapQuestFiles.MapboxMap2;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
//        private LongSparseArray<Annotation> mAnnotations;
        private MapboxMap mMapboxMap;
        private MapView mMapView;

        private GPSTracker gpsTracker;
        private double lat = 33.7489950;
        private double lon = -84.3879820;
        private MarkerOptions moMyLocation;
        private final LatLng MY_LOCATION = new LatLng(33.756351, -84.389144);
        private Bitmap bmp;
        Icon2 icon;
        private LongSparseArray<Annotation> mAnnotations;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            mMapView = (MapView) findViewById(R.id.mapquestMapView);
            mMapView.onCreate(savedInstanceState);

            //GPS code to check for user location
//            gpsTracker = new GPSTracker(this);
//
//            if(!gpsTracker.canGetLocation()) {
//                gpsTracker.showSettingsAlert();
//            } else {
//                gpsTracker.getLocation();
//                lat = gpsTracker.getLatitude();
//                lon = gpsTracker.getLongitude();
//            }
//            bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_location_icon);
//            icon = new Icon2("my_location_icon", bmp);

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMapboxMap = (MapboxMap2) mapboxMap;
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_LOCATION, 11));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("Current Location");
                    markerOptions.snippet("This is your current location");
//                    try {
//                        Field field = mMapboxMap.getClass().getDeclaredField("mAnnotation");
//                        field.setAccessible(true);
//                        mAnnotations = unpack(field);
//                    } catch(Exception e) {
//
//                    }
                    mMapboxMap.addMarker(markerOptions);
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
            markerOptions.icon(null);
            mapBoxMap.addMarker(markerOptions);
        }

    private void addMarkers(MapboxMap mapBoxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        for(int i = 0; i <  5; i++) {
            markerOptions.position(new LatLng(lat, lon));
            markerOptions.title("Location " + i );
            markerOptions.snippet("This is candy house" + i);
            mapBoxMap.addMarker(markerOptions);
            lat = (lat * 1.1);
            lon = (lon * 1.1);
        }
    }

    public static Object[] unpack(Field array) {
        Object[] array2 = new Object[Array.getLength(array)];
        for (int i = 0; i < array2.length; i++) {
            array2[i] = Array.get(array, i);
            return array2;
        }
        return null;
    }

    }