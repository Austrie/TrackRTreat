package com.trackrtreat.trackrtreat.EditedMapQuestFiles;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.annotation.UiThread;
import android.support.v4.util.LongSparseArray;

import com.mapbox.mapboxsdk.annotations.Annotation;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.maps.NativeMapView;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.maps.MapboxMap;

/**
 * Created by Shane Austrie on 10/23/2016.
 */

public class MapboxMap2 extends MapboxMap {

    float mScreenDensity = 1.0F;
    boolean mDestroyed;
    NativeMapView mNativeMapView ;
    MapView mMapView;
    MapboxMap2 mapboxMap = this;
    public LongSparseArray<Annotation> mAnnotations = new LongSparseArray();

    public MapboxMap2(@NonNull MapView mapView) {
        super(mapView);
        mNativeMapView = new NativeMapView(mMapView);
    }

    @UiThread
    @NonNull
    public Marker addMarker(@NonNull BaseMarkerOptions markerOptions, Icon2 icon) {
        Marker marker = mapboxMap.prepareMarker2(markerOptions, icon);
        long id = mNativeMapView.addMarker(marker);
        marker.setMapboxMap(this);
        marker.setId(id);
        this.mAnnotations.put(id, marker);
        return marker;
    }

    private Marker prepareMarker2(BaseMarkerOptions markerOptions, Icon2 icon) {
        Marker marker = markerOptions.getMarker();
        marker.setTopOffsetPixels(getTopOffsetPixelsForIcon(icon));
        return marker;
    }

    int getTopOffsetPixelsForIcon(Icon2 icon) {
        return !this.mDestroyed && !this.mNativeMapView.isPaused()
                ?(int)(this.mNativeMapView.getTopOffsetPixelsForAnnotationSymbol(icon.getId())
                * (double)this.mScreenDensity):0;
    }

    @UiThread
    public void onDestroy() {
        this.mDestroyed = true;
        this.mNativeMapView.terminateContext();
        this.mNativeMapView.terminateDisplay();
        this.mNativeMapView.destroySurface();
        this.mNativeMapView.destroy();
        this.mNativeMapView = null;
    }
}
