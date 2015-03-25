/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countrydatabase;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author BRM20150310
 *
 *         Basic structure class to hold country information required for
 *         insertion and retrieval from the database
 */
public class DbCountry {

    @SuppressWarnings("unused")
    private static final String TAG = "DbCountry";

    private long mDbId;
    private String mName;
    private String mCapital;
    private double mCapitalLat;
    private double mCapitalLon;
    private long mPopulation;
    private int mPopulationRank;
    private double mPopulationDensity; // Number of people per km2
    private double mGdp;
    private int mGdpPerCapita;
    private String mPhoneCallingCode;
    private String mInternetTld;
    private double mLifeExpectancy;
    private double mInfantMortality;
    private double mObesity; // Percent people classified as obese
    private double mInternetAccess; // Percent of people who have accessed the internet
    private int mOlympicsSummer; // Number of modern summer olympic medals
    private int mOlympicsWinter; // Number of winter olympic medals
    private int mMilitaryCount; // Active military
    private Hemisphere mHemisphere;
    private Continent mContinent;
    private Continent mContinent2; // Second continent country is part of (if any)
    private int mArea; // Country area in km2
    private int mAreaRank; // Rank of country area ie. Russia rank#1
    private int mCoastLength; // Total length in km of the coastline of the country
    private int mBorders; // Number of countries which have land borders with this country
    private String mCurrency; // General currency used in this country
    private DriveRoad mDrivesOn; // Side of the road cars drive on
    private int mFlagResourceID; // ResourceID for the flag image (SVG)
    private int mMapResourceID; // ResourceID of the map image (PNG)
    private CountryDifficulty mCountryDifficulty; // Level of Difficulty of this country

    public DbCountry() {
    }

    @SuppressWarnings("unused")
    public DbCountry(   String name,
                        String capital, double capitalLat, double capitalLon,
                        long population, int populationRank,
                        double gdp, int gdpPerCapita,
                        String phoneCallingCode, String internetTld,
                        Hemisphere hemisphere, Continent continent,
                        int area, int areaRank, int coastLength, int borderCount,
                        String currency, DriveRoad drivesOn,
                        int flagID, int mapID, CountryDifficulty countryDifficulty) {
        mName = name;
        mCapital = capital;
        mCapitalLat = capitalLat;
        mCapitalLon = capitalLon;
        mPopulation = population;
        mPopulationRank = populationRank;
        mPopulationDensity = population / area;
        mGdp = gdp;
        mGdpPerCapita = gdpPerCapita;
        mPhoneCallingCode = phoneCallingCode;
        mInternetTld = internetTld;
        mHemisphere = hemisphere;
        mContinent = continent;
        mContinent2 = Continent.CONTINENT_NONE;
        mArea = area;
        mAreaRank = areaRank;
        mCoastLength = coastLength;
        mBorders = borderCount;
        mCurrency = currency;
        mDrivesOn = drivesOn;
        mFlagResourceID = flagID;
        mMapResourceID = mapID;
        mCountryDifficulty = countryDifficulty;
        // Extended data
        mLifeExpectancy = -1;
        mInfantMortality = -1;
        mObesity = -1;
        mInternetAccess = -1;
        mOlympicsSummer = -1;
        mOlympicsWinter = -1;
        mMilitaryCount = -1;
    }

    @SuppressWarnings("unused")
    public void addExtendedData(double lifeExpectancy,
                                double infantMortality,
                                double obesity,
                                double internetAccess,
                                int olympicsSummer,
                                int olympicsWinter,
                                int militaryCount) {
        mLifeExpectancy = lifeExpectancy;
        mInfantMortality = infantMortality;
        mObesity = obesity;
        mInternetAccess = internetAccess;
        mOlympicsSummer = olympicsSummer;
        mOlympicsWinter = olympicsWinter;
        mMilitaryCount = militaryCount;
    }

    public long getDbId() {
        return mDbId;
    }

    public void setDbId(long dbId) {
        mDbId = dbId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String capital) {
        mCapital = capital;
    }

    public String getDistanceFromEquator(@NonNull Context context) {
        DecimalFormat df = new DecimalFormat("#.#");
        final int distance = (int)Math.abs(mCapitalLat * 111.2);
        final boolean isNorth = mCapitalLat >= 0;
        return df.format(mCapitalLat) + "° " + String.valueOf(distance) + " " + (context.getString(isNorth ? R.string.data_km_north : R.string.data_km_south));
    }

    public double getCapitalLat() {
        return mCapitalLat;
    }

    public void setCapitalLat(double capitalLat) {
        mCapitalLat = capitalLat;
    }

    public double getCapitalLon() {
        return mCapitalLon;
    }

    public void setCapitalLon(double capitalLon) {
        mCapitalLon = capitalLon;
    }

    public long getPopulation() {
        return mPopulation;
    }

    public String getPopulationString(@NonNull Context context) {
        DecimalFormat df = new DecimalFormat("#.##");

        if (mPopulation >= 1000000000) {
            double pop = mPopulation / 1000000000f;
            return df.format(pop) + " " + context.getString(R.string.data_billion);
        }
        if (mPopulation >= 1000000) {
            double pop = mPopulation / 1000000f;
            return df.format(pop) + " " + context.getString(R.string.data_million);
        }
        return NumberFormat.getNumberInstance(Locale.US).format(mPopulation);
    }

    public void setPopulation(long population) {
        mPopulation = population;
    }

    public int getPopulationRank() {
        return mPopulationRank;
    }

    public void setPopulationRank(int populationRank) {
        mPopulationRank = populationRank;
    }

    public double getPopulationDensity() {
        return mPopulationDensity;
    }

    public String getPopulationDensityString(@NonNull Context context) {
        return NumberFormat.getNumberInstance(Locale.US).format(mPopulationDensity) + " " + context.getString(R.string.data_per_km);
    }

    public void setPopulationDensity(double populationDensity) {
        mPopulationDensity = populationDensity;
    }

    public double getGdp() {
        return mGdp;
    }

    public String getGdpString(@NonNull Context context) {
        return "$" + String.valueOf(mGdp) + " " + context.getString(R.string.data_billion);
    }

    public void setGdp(double gdp) {
        mGdp = gdp;
    }

    public int getGdpPerCapita() {
        return mGdpPerCapita;
    }

    public String getGdpPerCapitaString() {
        return "$"+ NumberFormat.getNumberInstance(Locale.US).format(mGdpPerCapita);
    }

    public void setGdpPerCapita(int gdpPerCapita) {
        mGdpPerCapita = gdpPerCapita;
    }

    public String getPhoneCallingCode() {
        return mPhoneCallingCode;
    }

    public void setPhoneCallingCode(String phoneCallingCode) {
        mPhoneCallingCode = phoneCallingCode;
    }

    public String getInternetTld() {
        return mInternetTld;
    }

    public void setInternetTld(String internetTld) {
        mInternetTld = internetTld;
    }

    public double getInfantMortality() {
        return mInfantMortality;
    }

    public void setInfantMortality(double infantMortality) {
        mInfantMortality = infantMortality;
    }

    public double getLifeExpectancy() {
        return mLifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        mLifeExpectancy = lifeExpectancy;
    }

    public double getObesity() {
        return mObesity;
    }

    public void setObesity(double obesity) {
        mObesity = obesity;
    }

    public double getInternetAccess() {
        return mInternetAccess;
    }

    public void setInternetAccess(double internetAccess) {
        mInternetAccess = internetAccess;
    }

    public int getOlympicsSummer() {
        return mOlympicsSummer;
    }

    public void setOlympicsSummer(int olympicsSummer) {
        mOlympicsSummer = olympicsSummer;
    }

    public int getOlympicsWinter() {
        return mOlympicsWinter;
    }

    public void setOlympicsWinter(int olympicsWinter) {
        mOlympicsWinter = olympicsWinter;
    }

    public int getMilitaryCount() {
        return mMilitaryCount;
    }

    public void setMilitaryCount(int militaryCount) {
        mMilitaryCount = militaryCount;
    }

    public String getMilitaryCountString(@NonNull Context context) {
        DecimalFormat df = new DecimalFormat("#.##");

        if (mMilitaryCount >= 1000000000) {
            double pop = mMilitaryCount / 1000000000f;
            return df.format(pop) + " " + context.getString(R.string.data_billion);
        }
        if (mMilitaryCount >= 1000000) {
            double pop = mMilitaryCount / 1000000f;
            return df.format(pop) + " " + context.getString(R.string.data_million);
        }
        return NumberFormat.getNumberInstance(Locale.US).format(mMilitaryCount);
    }

    public Hemisphere getHemisphere() {
        return mHemisphere;
    }

    public void setHemisphere(Hemisphere hemisphere) {
        mHemisphere = hemisphere;
    }

    @SuppressWarnings("unused")
    public Continent getContinent2() {
        return mContinent2;
    }

    @SuppressWarnings("unused")
    public void setContinent2(Continent continent) {
        mContinent2 = continent;
    }

    public Continent getContinent() {
        return mContinent;
    }

    public void setContinent(Continent continent) {
        mContinent = continent;
    }

    public String getAreaString(@NonNull Context context) {
        return NumberFormat.getNumberInstance(Locale.US).format(mArea) + " " + context.getString(R.string.data_km2);
    }

    public int getArea() {
        return mArea;
    }

    public void setArea(int area) {
        mArea = area;
    }

    public int getAreaRank() {
        return mAreaRank;
    }

    public void setAreaRank(int areaRank) {
        mAreaRank = areaRank;
    }

    public int getCoastLength() {
        return mCoastLength;
    }

    public String getCoastLengthString(@NonNull Context context) {
        return NumberFormat.getNumberInstance(Locale.US).format(mCoastLength) +  " " + context.getString(R.string.data_km);
    }

    public void setCoastLength(int coastLength) {
        mCoastLength = coastLength;
    }

    public int getBorders() {
        return mBorders;
    }
    public String getBordersString(@NonNull Context context) {
        if (mBorders > 1) {
            return String.valueOf(mBorders) +  " " + context.getString(R.string.data_countries);
        }
        if (mBorders == 1) {
            return String.valueOf(mBorders) + " " + context.getString(R.string.data_country);
        }
        return context.getString(R.string.data_no_borders);
    }

    public void setBorders(int borderCount) {
        mBorders = borderCount;
    }
    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public DriveRoad getDrivesOn() {
        return mDrivesOn;
    }

    public void setDrivesOn(DriveRoad drivesOn) {
        mDrivesOn = drivesOn;
    }

    public int getFlagID() {
        return mFlagResourceID;
    }

    public void setFlagID(int flagID) {
        mFlagResourceID = flagID;
    }

    public int getMapID() {
        return mMapResourceID;
    }

    public void setMapID(int mapID) {
        mMapResourceID = mapID;
    }

    public CountryDifficulty getDifficulty() {
        return mCountryDifficulty;
    }

    public void setDifficulty(CountryDifficulty countryDifficulty) {
        mCountryDifficulty = countryDifficulty;
    }
}