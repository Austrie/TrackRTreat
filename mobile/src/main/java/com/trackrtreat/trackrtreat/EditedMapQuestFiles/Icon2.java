package com.trackrtreat.trackrtreat.EditedMapQuestFiles;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.FloatRange;

import com.mapbox.mapboxsdk.annotations.Icon;


/**
 * Created by Shane Austrie on 10/23/2016.
 */

public final class Icon2 {
    private Bitmap mBitmap;
    private String mId;
    private float mScalarX = 0.5F;
    private float mScalarY = 0.5F;


    public Icon2(String id, Bitmap bitmap) {
        this.mId = id;
        this.mBitmap = bitmap;
    }

    public String getId() {
        return this.mId;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            Icon2 icon = (Icon2)o;
            return !this.mBitmap.equals(icon.mBitmap)?false:this.mId.equals(icon.mId);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.mBitmap.hashCode();
        result = 31 * result + this.mId.hashCode();
        return result;
    }

    @SuppressLint("SupportAnnotationUsage")
    @FloatRange(
            from = 0.0D,
            to = 1.0D
    )

    public void setScalarX(float scalarX) {
        this.mScalarX = scalarX;
    }

    @SuppressLint("SupportAnnotationUsage")
    @FloatRange(
            from = 0.0D,
            to = 1.0D
    )
    public void setScalarY(float scalarY) {
        this.mScalarY = scalarY;
    }

    public float getScalarX() {
        return this.mScalarX;
    }

    public float getScalarY() {
        return this.mScalarY;
    }
}
