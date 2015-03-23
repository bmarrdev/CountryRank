/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.CountryDisplay;

/**
 * @author BRM20150316
 */
public class CountryInfo {
    public String mDescription;
    public String mValue;
    public int mImageId;
    public double mLatitude;
    public double mLongitude;

    public CountryInfo(String desc, String value, int imageId) {
        mDescription = desc;
        mValue = value;
        mImageId = imageId;
        mLatitude = 0;
        mLongitude = 0;
    }

    @SuppressWarnings("SameParameterValue")
    public CountryInfo(String desc, String value, int imageId, double latitude, double longitude) {
        mDescription = desc;
        mValue = value;
        mImageId = imageId;
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
