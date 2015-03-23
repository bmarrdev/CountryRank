/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countrydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author BRM20150310
 *
 * SQLite table to store information about each country
 *
 * @see {@link android.database.sqlite.SQLiteDatabase}
 */
public class DbTableCountry {
    @SuppressWarnings("unused")
    private static final String TAG = "DbTableCountry";

    private SQLiteDatabase mDatabase;
    private DbTableCountryHelper mDbHelper;

    /**
     * Array of all columns in the table
     */
    private String[] mAllColumns = {
            DbTableCountryHelper.COLUMN_ID,
            DbTableCountryHelper.COLUMN_NAME,
            DbTableCountryHelper.COLUMN_CAPITAL,
            DbTableCountryHelper.COLUMN_CAPITAL_LAT,
            DbTableCountryHelper.COLUMN_CAPITAL_LON,
            DbTableCountryHelper.COLUMN_POPULATION,
            DbTableCountryHelper.COLUMN_POPULATION_RANK,
            DbTableCountryHelper.COLUMN_POPULATION_DENSITY,
            DbTableCountryHelper.COLUMN_GDP,
            DbTableCountryHelper.COLUMN_GDP_PER_CAPITA,
            DbTableCountryHelper.COLUMN_PHONE_CALLING_CODE,
            DbTableCountryHelper.COLUMN_INTERNET_TLD,
            DbTableCountryHelper.COLUMN_LIFE_EXPECTANCY,
            DbTableCountryHelper.COLUMN_INFANT_MORTALITY,
            DbTableCountryHelper.COLUMN_OBESITY,
            DbTableCountryHelper.COLUMN_INTERNET_ACCESS,
            DbTableCountryHelper.COLUMN_OLYMPIC_SUMMER,
            DbTableCountryHelper.COLUMN_OLYMPIC_WINTER,
            DbTableCountryHelper.COLUMN_ACTIVE_MILITARY,
            DbTableCountryHelper.COLUMN_HEMISPHERE,
            DbTableCountryHelper.COLUMN_CONTINENT,
            DbTableCountryHelper.COLUMN_AREA,
            DbTableCountryHelper.COLUMN_AREA_RANK,
            DbTableCountryHelper.COLUMN_LENGTH_COASTLINE,
            DbTableCountryHelper.COLUMN_LAND_BORDERS,
            DbTableCountryHelper.COLUMN_CURRENCY,
            DbTableCountryHelper.COLUMN_DRIVE_ROAD,
            DbTableCountryHelper.COLUMN_FLAG_ID,
            DbTableCountryHelper.COLUMN_MAP_ID,
            DbTableCountryHelper.COLUMN_DIFFICULTY
    };

    public DbTableCountry(Context context) {
        mDbHelper = new DbTableCountryHelper(context);
    }

    /**
     * Open the SQLite database
     */
    public void open() throws SQLException {
        try {
            mDatabase = mDbHelper.getWritableDatabase();
        }
         catch (SQLiteException ex) {
             Log.e(TAG, "Unable to open database");
             throw ex;
         }
    }

    /**
     * Close the SQLite database
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Add a Country into the sqlite database
     *
     * @param country Contains all fields to be added into the database
     * @return record on success, null on failure
     */
    public DbCountry addDbCountry(@NonNull final DbCountry country) {
        ContentValues values = new ContentValues();
        values.put(DbTableCountryHelper.COLUMN_NAME, country.getName());
        values.put(DbTableCountryHelper.COLUMN_CAPITAL, country.getCapital());
        values.put(DbTableCountryHelper.COLUMN_CAPITAL_LAT, country.getCapitalLat());
        values.put(DbTableCountryHelper.COLUMN_CAPITAL_LON, country.getCapitalLon());
        values.put(DbTableCountryHelper.COLUMN_POPULATION, country.getPopulation());
        values.put(DbTableCountryHelper.COLUMN_POPULATION_RANK, country.getPopulationRank());
        values.put(DbTableCountryHelper.COLUMN_POPULATION_DENSITY, country.getPopulationDensity());
        values.put(DbTableCountryHelper.COLUMN_GDP, country.getGdp());
        values.put(DbTableCountryHelper.COLUMN_GDP_PER_CAPITA, country.getGdpPerCapita());
        values.put(DbTableCountryHelper.COLUMN_PHONE_CALLING_CODE, country.getPhoneCallingCode());
        values.put(DbTableCountryHelper.COLUMN_INTERNET_TLD, country.getInternetTld());
        values.put(DbTableCountryHelper.COLUMN_LIFE_EXPECTANCY, country.getLifeExpectancy());
        values.put(DbTableCountryHelper.COLUMN_INFANT_MORTALITY, country.getInfantMortality());
        values.put(DbTableCountryHelper.COLUMN_OBESITY, country.getObesity());
        values.put(DbTableCountryHelper.COLUMN_INTERNET_ACCESS, country.getInternetAccess());
        values.put(DbTableCountryHelper.COLUMN_OLYMPIC_SUMMER, country.getOlympicsSummer());
        values.put(DbTableCountryHelper.COLUMN_OLYMPIC_WINTER, country.getOlympicsWinter());
        values.put(DbTableCountryHelper.COLUMN_ACTIVE_MILITARY, country.getMilitaryCount());
        values.put(DbTableCountryHelper.COLUMN_HEMISPHERE, country.getHemisphere().ordinal());
        values.put(DbTableCountryHelper.COLUMN_CONTINENT, country.getContinent().ordinal());
        values.put(DbTableCountryHelper.COLUMN_AREA, country.getArea());
        values.put(DbTableCountryHelper.COLUMN_AREA_RANK, country.getAreaRank());
        values.put(DbTableCountryHelper.COLUMN_LENGTH_COASTLINE, country.getCoastLength());
        values.put(DbTableCountryHelper.COLUMN_LAND_BORDERS, country.getBorders());
        values.put(DbTableCountryHelper.COLUMN_CURRENCY, country.getCurrency());
        values.put(DbTableCountryHelper.COLUMN_DRIVE_ROAD, country.getDrivesOn().ordinal());
        values.put(DbTableCountryHelper.COLUMN_FLAG_ID, country.getFlagID());
        values.put(DbTableCountryHelper.COLUMN_MAP_ID, country.getMapID());
        values.put(DbTableCountryHelper.COLUMN_DIFFICULTY, country.getDifficulty().ordinal());

        final long insertId = mDatabase.insert(DbTableCountryHelper.TABLE_TARGET, null, values);
        Cursor cursor = mDatabase.query(DbTableCountryHelper.TABLE_TARGET,
                mAllColumns, DbTableCountryHelper.COLUMN_ID + " = "
                        + insertId, null, null, null, null);

        cursor.moveToFirst();

        DbCountry newCountry = cursorToCountry(cursor);
        cursor.close();

        return newCountry;
    }

    public long getEntryCount(@Nullable String where, @Nullable String[] args) {
        return DatabaseUtils.queryNumEntries(mDatabase, DbTableCountryHelper.TABLE_TARGET, where, args);
    }

    /**
     * Delete all records from the database table (but not the table itself)
     */
    public void deleteAll() {
        mDatabase.delete(DbTableCountryHelper.TABLE_TARGET, null, null);
    }

    public List<DbCountry> getCountries(@Nullable String where, @Nullable String[] args) {
        return getCountries(where, args, null, null, null);
    }
    /**
     * Retrieve a list of DbCountry that match the filters passed
     *
     * @param where Sqlite where filter string
     * @param args  Sqlite args array
     * @return List of dbQuake objects that meet filter
     */
    public List<DbCountry> getCountries(@Nullable String where, @Nullable String[] args,
                                        @Nullable String groupBy, @Nullable String having, @Nullable String orderBy) {
        List<DbCountry> databaseSelectedCountries = new ArrayList<>();
        Cursor cursor = mDatabase.query(DbTableCountryHelper.TABLE_TARGET,
                mAllColumns, where, args, groupBy, having, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DbCountry resultCountry = cursorToCountry(cursor);
            databaseSelectedCountries.add(resultCountry);
            cursor.moveToNext();
        }

        cursor.close();

        return databaseSelectedCountries;
    }

    /**
     * Take the cursor from a database query and create fill the DbCountry structure
     *
     * @param cursor Database cursor from sqlite query
     * @return new DbCountry structure
     */
    private DbCountry cursorToCountry(@NonNull Cursor cursor) {
        DbCountry country = new DbCountry();
        int index = 0;
        country.setDbId(cursor.getLong(index++));
        country.setName(cursor.getString(index++));
        country.setCapital(cursor.getString(index++));
        country.setCapitalLat(cursor.getDouble(index++));
        country.setCapitalLon(cursor.getDouble(index++));
        country.setPopulation(cursor.getInt(index++));
        country.setPopulationRank(cursor.getInt(index++));
        country.setPopulationDensity(cursor.getDouble(index++));
        country.setGdp(cursor.getDouble(index++));
        country.setGdpPerCapita(cursor.getInt(index++));
        country.setPhoneCallingCode(cursor.getString(index++));
        country.setInternetTld(cursor.getString(index++));
        country.setLifeExpectancy(cursor.getDouble(index++));
        country.setInfantMortality(cursor.getDouble(index++));
        country.setObesity(cursor.getDouble(index++));
        country.setInternetAccess(cursor.getDouble(index++));
        country.setOlympicsSummer(cursor.getInt(index++));
        country.setOlympicsWinter(cursor.getInt(index++));
        country.setMilitaryCount(cursor.getInt(index++));
        country.setHemisphere(Hemisphere.values()[cursor.getInt(index++)]);
        country.setContinent(Continent.values()[cursor.getInt(index++)]);
        country.setArea(cursor.getInt(index++));
        country.setAreaRank(cursor.getInt(index++));
        country.setCoastLength(cursor.getInt(index++));
        country.setBorders(cursor.getInt(index++));
        country.setCurrency(cursor.getString(index++));
        country.setDrivesOn(DriveRoad.values()[cursor.getInt(index++)]);
        country.setFlagID(cursor.getInt(index++));
        country.setMapID(cursor.getInt(index++));
        country.setDifficulty(CountryDifficulty.values()[cursor.getInt(index++)]);
        return country;
    }

    /**
     * Retrieve DbCountry for given country database id
     *
     * @param id Database country id
     *
     * @return Country structure or null for not found
     */
    public DbCountry getCountry(final long id) {
        String where = DbTableCountryHelper.COLUMN_ID + "=?";
        String args[] = new String[] {String.valueOf(id)};

        Cursor cursor = mDatabase.query(DbTableCountryHelper.TABLE_TARGET,
                mAllColumns, where, args, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            DbCountry newCountry = cursorToCountry(cursor);
            cursor.close();

            return newCountry;
        }
        cursor.close();
        return null;
    }

    /**
     * Return a random country that meets the given criteria
     * @param where sql where string
     * @param args sql args string array
     *
     * @return random DbCountry structure
     */
    public DbCountry getRandomCountry(@Nullable String where, @Nullable String args[]) {
        Random generator = new Random();
        // Get number of entries
        int val = (int)DatabaseUtils.queryNumEntries(mDatabase, DbTableCountryHelper.TABLE_TARGET, where, args);

        int pos = generator.nextInt(val);

        Cursor cursor = mDatabase.query(DbTableCountryHelper.TABLE_TARGET,
                mAllColumns, where, args, null, null, null, "" + pos + ",1");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            DbCountry newCountry = cursorToCountry(cursor);
            cursor.close();

            return newCountry;
        }
        cursor.close();
        return null;
    }

//   public SQLiteDatabase getDatabase() {
//        return mDatabase;
//    }

    @SuppressWarnings("unused")
    public void outputDatabaseStats() {

        Log.e(TAG, "-- DATABASE SUMMARY --");
        Log.e(TAG, "-- Countries : " + getEntryCount(null, null));
        Log.e(TAG, "-- Europe : "  + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_EUROPE.ordinal())}));
        Log.e(TAG, "-- Asia : " + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_ASIA.ordinal())}));
        Log.e(TAG, "-- Africa : " + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_AFRICA.ordinal())}));
        Log.e(TAG, "-- North America : " + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_NORTH_AMERICA.ordinal())}));
        Log.e(TAG, "-- South America : " + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_SOUTH_AMERICA.ordinal())}));
        Log.e(TAG, "-- Oceania/Australia : " + getEntryCount(DbTableCountryHelper.COLUMN_CONTINENT + "=?", new String[]{String.valueOf(Continent.CONTINENT_AUSTRALIA.ordinal())}));
        Log.e(TAG, " -- Drive on left : " + getEntryCount(DbTableCountryHelper.COLUMN_DRIVE_ROAD + "=?", new String[]{String.valueOf(DriveRoad.DRIVES_ON_LEFT.ordinal())}));
        Log.e(TAG, " -- Drive on right : " + getEntryCount(DbTableCountryHelper.COLUMN_DRIVE_ROAD + "=?", new String[]{String.valueOf(DriveRoad.DRIVES_ON_RIGHT.ordinal())}));

        Log.e(TAG, " -- LAT LON CHECK --");
        List<DbCountry> countries = getCountries(DbTableCountryHelper.COLUMN_CAPITAL_LAT + "=?" + " OR " + DbTableCountryHelper.COLUMN_CAPITAL_LON + "=?", new String[]{String.valueOf(0), String.valueOf(0)});
        for (DbCountry country : countries) {
            Log.e(TAG, country.getName() + " is missing capital lon lat");
        }

        Log.e(TAG, " -- ALL COUNTRIES --");
        countries = getCountries(null, null);
        for (DbCountry country : countries) {
            Log.e(TAG, country.getName() + " (" + country.getCapital() + ") Population " + country.getPopulation() + " Area " + country.getArea());
        }
    }
}