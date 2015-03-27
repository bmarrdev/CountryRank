/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countrydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author BRM20150310
 *         Helper subclass for country database table
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class DbTableCountryHelper extends SQLiteOpenHelper {
    public static final String TABLE_TARGET = "table_country_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "col_country_name";
    public static final String COLUMN_CAPITAL = "col_country_capital";
    public static final String COLUMN_CAPITAL_LAT = "col_country_capital_lat";
    public static final String COLUMN_CAPITAL_LON = "col_country_capital_lon";
    public static final String COLUMN_POPULATION = "col_country_population";
    public static final String COLUMN_POPULATION_RANK = "col_country_population_rank";
    public static final String COLUMN_POPULATION_DENSITY = "col_country_pop_density";
    public static final String COLUMN_GDP = "col_country_gdp";
    public static final String COLUMN_GDP_PER_CAPITA = "col_country_gdp_per_capita";
    public static final String COLUMN_PHONE_CALLING_CODE = "col_country_calling_code";
    public static final String COLUMN_INTERNET_TLD = "col_country_internet_tld";
    public static final String COLUMN_LIFE_EXPECTANCY = "col_country_life_expectancy";
    public static final String COLUMN_INFANT_MORTALITY = "col_country_infant_mortality";
    public static final String COLUMN_OBESITY = "col_country_obesity";
    public static final String COLUMN_INTERNET_ACCESS = "col_country_internet_access";
    public static final String COLUMN_OLYMPIC_SUMMER = "col_country_olympic_medals_summer";
    public static final String COLUMN_OLYMPIC_WINTER = "col_country_olympic_medals_winter";
    public static final String COLUMN_ACTIVE_MILITARY = "col_country_active_military";
    public static final String COLUMN_HEMISPHERE = "col_country_hemisphere";
    public static final String COLUMN_CONTINENT = "col_country_continent";
    public static final String COLUMN_AREA = "col_country_area";
    public static final String COLUMN_AREA_RANK = "col_country_area_rank";
    public static final String COLUMN_LENGTH_COASTLINE = "col_country_coastline_length";
    public static final String COLUMN_LAND_BORDERS = "col_country_borders";
    public static final String COLUMN_CURRENCY = "col_country_currency";
    public static final String COLUMN_DRIVE_ROAD = "col_country_road_side";
    public static final String COLUMN_FLAG_ID = "col_country_flag_id";
    public static final String COLUMN_MAP_ID = "col_country_map_id";
    public static final String COLUMN_DIFFICULTY = "col_country_difficulty";
    // Database creation sql statement
    @SuppressWarnings("unused")
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TARGET + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME + " text, "
            + COLUMN_CAPITAL + " text, "
            + COLUMN_CAPITAL_LAT + " real, "
            + COLUMN_CAPITAL_LON + " real, "
            + COLUMN_POPULATION + " integer, "
            + COLUMN_POPULATION_RANK + " integer, "
            + COLUMN_POPULATION_DENSITY + " real, "
            + COLUMN_GDP + " real, "
            + COLUMN_GDP_PER_CAPITA + " integer, "
            + COLUMN_PHONE_CALLING_CODE + " text, "
            + COLUMN_INTERNET_TLD + " text, "
            + COLUMN_LIFE_EXPECTANCY + " real, "
            + COLUMN_INFANT_MORTALITY + " real, "
            + COLUMN_OBESITY + " real, "
            + COLUMN_INTERNET_ACCESS + " real, "
            + COLUMN_OLYMPIC_SUMMER + " integer, "
            + COLUMN_OLYMPIC_WINTER + " integer, "
            + COLUMN_ACTIVE_MILITARY + " integer, "
            + COLUMN_HEMISPHERE + " integer, "
            + COLUMN_CONTINENT + " integer, "
            + COLUMN_AREA + " integer, "
            + COLUMN_AREA_RANK + " integer, "
            + COLUMN_LENGTH_COASTLINE + " integer, "
            + COLUMN_LAND_BORDERS + " integer, "
            + COLUMN_CURRENCY + " text, "
            + COLUMN_DRIVE_ROAD + " integer, "
            + COLUMN_FLAG_ID + " integer, "
            + COLUMN_MAP_ID + " integer, "
            + COLUMN_DIFFICULTY + " integer);";
    private static final String TAG = "DbTableCountryHelper";
    private static final String DATABASE_NAME = "countries.db";

    private static final int DATABASE_VERSION = 1;

    public DbTableCountryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TARGET);
        onCreate(db);
    }
} 