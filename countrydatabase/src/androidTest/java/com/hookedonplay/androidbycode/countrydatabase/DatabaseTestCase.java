/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countrydatabase;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.List;

/**
 * @author BRM
 */
public class DatabaseTestCase extends AndroidTestCase {
    private static final String TAG = "DatabaseTestCase";
    private DbTableCountry mDatabaseCountry;

    public void setUp() throws Exception{
        super.setUp();
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");

    }

    /**
     * Test populating database
     */
    public void testFillDatabase(){
        //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_CLEAN_BUILD);
        mDatabaseCountry = new DbTableCountry(mContext);
        mDatabaseCountry.open();
        long count = mDatabaseCountry.getEntryCount(null, null);
        //mDatabaseCountry.outputDatabaseStats();
        mDatabaseCountry.close();
        //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_CLEAN_BUILD);
        mDatabaseCountry = new DbTableCountry(mContext);
        mDatabaseCountry.open();
        long count2 = mDatabaseCountry.getEntryCount(null, null);
        //mDatabaseCountry.outputDatabaseStats();
        mDatabaseCountry.close();
        assertTrue(count == count2);
    }

    /**
     * Check the rank of the population and the population value are in sync when compared
     * to all other records
     */
    public void testPopulationRank() {
        //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_CLEAN_BUILD);
        mDatabaseCountry = new DbTableCountry(mContext);
        mDatabaseCountry.open();
        List<DbCountry> countryList = mDatabaseCountry.getCountries(null, null, null, null, DbTableCountryHelper.COLUMN_POPULATION + " DESC");
        int populationRank = 0;
        for (DbCountry dbCountry : countryList) {
            //Log.v(TAG, dbCountry.getName() + " population: " + dbCountry.getPopulationString() + " rank: " + dbCountry.getPopulationRank());
            assertTrue(dbCountry.getPopulationRank() > populationRank);
            populationRank = dbCountry.getPopulationRank();
        }
        mDatabaseCountry.close();
    }

    /**
     * Validate that the data for area and area rank is valid.
     * The database includes a value for area in km2 and also a rank
     * for all countries based on size ie a country my be 12000km2 and
     * be the 12th largest country in the world. This test case validates
     * that all these values make sense, so it will fail in the case of:
     * [india,28000km2,15th]
     * [Nepal,1727km2,14th]
     */
    public void testAreaRank() {
        //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_CLEAN_BUILD);
        mDatabaseCountry = new DbTableCountry(mContext);
        mDatabaseCountry.open();
        List<DbCountry> countryList = mDatabaseCountry.getCountries(null, null, null, null, DbTableCountryHelper.COLUMN_AREA + " DESC");
        int areaRank = 0;
        for (DbCountry dbCountry : countryList) {
            Log.v(TAG, dbCountry.getName() + " area: " + dbCountry.getAreaString() + " rank: " + dbCountry.getAreaRank());
            assertTrue(dbCountry.getAreaRank() > areaRank);
            areaRank = dbCountry.getAreaRank();
        }
        mDatabaseCountry.close();
    }

    /**
     * Sanity check for values in each database entry
     */
    public void testDatabaseValues() {
        //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_CLEAN_BUILD);
        mDatabaseCountry = new DbTableCountry(mContext);
        mDatabaseCountry.open();
        List<DbCountry> countryList = mDatabaseCountry.getCountries(null, null);
        for (DbCountry dbCountry : countryList) {
            verifyLatitudeLongitude(dbCountry);
            verifyGdp(dbCountry);
        }
        mDatabaseCountry.close();
    }

    /**
     * Verify that the longitude and latitude are within the valid range
     *
     * @param dbCountry country to test
     */
    private void verifyLatitudeLongitude(@NonNull DbCountry dbCountry) {
        assertTrue(
                (dbCountry.getCapitalLon() != 0 && dbCountry.getCapitalLat() != 0) ||
                        (dbCountry.getCapitalLon() == 0 && dbCountry.getCapitalLat() == 0));

        assertTrue(dbCountry.getCapitalLat() >= -90);
        assertTrue(dbCountry.getCapitalLat() <= 90);
        assertTrue(dbCountry.getCapitalLon() >= -180);
        assertTrue(dbCountry.getCapitalLon() <= 180);
    }

    /**
     * Verify the the GDP and GDP per capital are reasonable values
     *
     * @param dbCountry
     */
    private void verifyGdp(@NonNull DbCountry dbCountry) {
        assertTrue(dbCountry.getGdp() < 10000);
        assertTrue(dbCountry.getGdp() >= 0);
        // The GDP is in billions, so we need to mulitply the result by one billion dollars to get
        // the gdp per person
        final double gdpCapita = 1000000000 * (dbCountry.getGdp() / dbCountry.getPopulation());

        Log.v(TAG, dbCountry.getName() + " Gdp " + dbCountry.getGdp() + " pop " + dbCountry.getPopulation() + " Gdp p cap : " + dbCountry.getGdpPerCapita() + " est " + gdpCapita);

        // We ensure that the gdp per capita figure is reasonable
        assertTrue(dbCountry.getGdpPerCapita() > (gdpCapita - (gdpCapita/10)));
        assertTrue(dbCountry.getGdpPerCapita() < (gdpCapita + (gdpCapita/10)));
    }


    public void testQueryDatabase(){

    }


    public void tearDown() throws Exception{
        //mDatabaseCountry.close();
        super.tearDown();
    }
}
