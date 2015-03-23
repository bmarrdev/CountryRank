/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hookedonplay.androidbycode.countrydatabase.Continent;
import com.hookedonplay.androidbycode.countrydatabase.CountryDifficulty;
import com.hookedonplay.androidbycode.countrydatabase.DbCountry;
import com.hookedonplay.androidbycode.countrydatabase.DbTableCountry;
import com.hookedonplay.androidbycode.countrydatabase.DbTableCountryHelper;
import com.hookedonplay.androidbycode.countryrankking.BuildConfig;

import java.util.ArrayList;

/**
 * This class handles constructing the sqlite statements to query the database
 * for the random countries based on the current game settings and question
 * type
 *
 * @author BRM20150309
 */
public class RandomCountryGenerator {
    private static final String TAG = "RandomCountryGenerator";

    /**
     * Continent to select from, may be null or all continents
     */
    private Continent mContinent;

    /**
     * Level of difficulty to select countries, may be null
     */
    private CountryDifficulty mCountryDifficulty;

    /**
     * Type of question. Some counties are not applicable for some questions
     */
    private QuestionType mQuestionType;

    /**
     * Determines if the same country can be returned from multiple calls to {@link #getRandomCountry()}
     */
    private boolean mAllowDuplicates;

    /**
     * Db entries to ignore
     * When {@link #mAllowDuplicates} is set the db ids are saved in this list
     */
    private ArrayList<Long> mExcludeList;

    /**
     * Context used to load database
     */
    private Context mContext;

    public RandomCountryGenerator(@NonNull Context context,
                                  boolean allowDuplicates,
                                  @Nullable CountryDifficulty difficulty,
                                  @Nullable QuestionType questionType,
                                  @Nullable Continent continent) {
        mContext = context;
        mAllowDuplicates = allowDuplicates;
        mCountryDifficulty = difficulty;
        mQuestionType = questionType;
        mContinent = continent;
        mExcludeList = new ArrayList<>();
    }

    public DbCountry getCountryFromId(long id) {
        DbTableCountry tableCountry = new DbTableCountry(mContext);
        tableCountry.open();
        DbCountry country = tableCountry.getCountry(id);
        tableCountry.close();

        if (!mAllowDuplicates && country != null) {
            mExcludeList.add(country.getDbId());
        }
        return country;
    }

    public DbCountry getRandomCountry() {
        DbTableCountry tableCountry = new DbTableCountry(mContext);
        tableCountry.open();

        DbCountry country = tableCountry.getRandomCountry(getFilterWhere(), getFilterArgs());
        Log.v(TAG, "Random country: " + ((country != null) ? country.getName() : " none"));
        tableCountry.close();

        if (!mAllowDuplicates && country != null) {
            mExcludeList.add(country.getDbId());
        }
        return country;
    }

    private String getContinentWhere() {
        if (mContinent == null) {
            return null;
        }

        return DbTableCountryHelper.COLUMN_CONTINENT + "!=?";
    }

    private String[] getContinentArgs() {
        if (mContinent == null) {
            return null;
        }
        return new String[]{String.valueOf(mContinent.ordinal())};
    }

    private String getQuestionTypeWhere() {
        if (mQuestionType == QuestionType.QUESTION_LATITUDE) {
            return DbTableCountryHelper.COLUMN_CAPITAL_LAT + " !=?" + " AND " + DbTableCountryHelper.COLUMN_CAPITAL + " !=?";
        }
        if (mQuestionType == QuestionType.QUESTION_LAND_BORDERS) {
            return DbTableCountryHelper.COLUMN_LAND_BORDERS + " >=?";
        }
        return null;
    }

    private String[] getQuestionTypeArgs() {
        if (mQuestionType == QuestionType.QUESTION_LATITUDE) {
            return new String[]{String.valueOf(0), ""};
        }
        if (mQuestionType == QuestionType.QUESTION_LAND_BORDERS) {
            return new String[]{String.valueOf(0)};
        }

        return null;

    }

    private String getDifficultyWhere() {
        if (mCountryDifficulty == null) {
            return null;
        }

        return DbTableCountryHelper.COLUMN_DIFFICULTY + "<=?";
    }

    private String[] getDifficultyArgs() {
        if (mCountryDifficulty == null) {
            return null;
        }
        return new String[]{String.valueOf(mCountryDifficulty.ordinal())};
    }

    private String getCountryExcludeWhere() {
        if (mExcludeList == null) {
            return null;
        }

        String str = null;
        for (int i = 0; i < mExcludeList.size(); i++) {
            if (str != null) {
                str += " AND ";
            } else {
                str = "";
            }
            str += DbTableCountryHelper.COLUMN_ID + "!=?";
        }
        return str;
    }

    private String[] getCountryExcludeArgs() {

        if (mExcludeList == null || mExcludeList.size() <= 0) {
            return null;
        }
        String[] args = new String[mExcludeList.size()];

        for (int i = 0; i < mExcludeList.size(); i++) {
            args[i] = String.valueOf(mExcludeList.get(i));
        }
        return args;
    }

    private String addWhere(@Nullable String where1, @Nullable String where2) {
        if (where1 == null || where1.isEmpty()) return where2;
        if (where2 == null || where2.isEmpty()) return where1;
        return where1 + " AND " + where2;
    }

    /**
     * Generate the Sqlite where string for all filters set
     *
     * @return Sqlite where string
     */
    public String getFilterWhere() {
        String where = getCountryExcludeWhere();
        where = addWhere(where, getDifficultyWhere());
        where = addWhere(where, getQuestionTypeWhere());
        where = addWhere(where, getContinentWhere());
        return where;
    }

    /**
     * Generate the Sqlite args string array based on all filters set
     *
     * @return Sqlite args array
     */
    public String[] getFilterArgs() {
        /**
         * Order is paramount and must match up with the where filter string
         */
        String[] args = appendStringArray(getCountryExcludeArgs(), getDifficultyArgs());
        args = appendStringArray(args, getQuestionTypeArgs());
        args = appendStringArray(args, getContinentArgs());
        return args;
    }

    /**
     * Verify that the where statement generated from the filter dialog matches
     * the where args to be passed to sqlite
     * <p/>
     * Each ? in a where statement must have 1 and only 1 corresponding String[] entry
     * For example if you have
     * where: String: Depth <= ?
     * args:  String[] {"0"}
     * Everything is ok,
     * However if you have mismatched arguments there is a programming error involved
     * and sqlite may fail, for example
     * where: String: Depth > ? AND Depth < ?
     * args:  String[] {"0", "1", "2"}
     *
     * @throws IllegalStateException Number of arguments don't match where statement
     */
    @SuppressWarnings("unused")
    private void verifyFilters(String where, String[] args)
            throws IllegalStateException {
        if (!BuildConfig.DEBUG) {
            return;
        }

        int numQms = 0;
        {
            int lastIndex = 0;
            while (lastIndex != -1) {
                lastIndex = where.indexOf("?", lastIndex);
                if (lastIndex != -1) {
                    numQms++;
                    lastIndex++;
                }
            }
        }

        if (numQms != args.length) {
            throw new IllegalStateException("Filter where statement and arguments do not match: " + where);
        }
    }


    /**
     * Append one array of Strings (String []) onto another array of strings
     *
     * @param strArgs1 First items to be added to new array
     * @param strArgs2 Second items to be added to new array
     * @return new String[] containing both arrays, If both arrays are null or empty, null will be returned
     */
    public String[] appendStringArray(@Nullable String[] strArgs1, @Nullable String[] strArgs2) {
        final int size = (strArgs1 != null ? strArgs1.length : 0) + (strArgs2 != null ? strArgs2.length : 0);
        if (size <= 0) {
            Log.v(TAG, "Warning no items to append");
            return null;
        }

        if (strArgs1 == null || strArgs1.length == 0) {
            return strArgs2;
        }
        if (strArgs2 == null || strArgs2.length == 0) {
            return strArgs1;
        }

        String strBoth[] = new String[size];
        System.arraycopy(strArgs1, 0, strBoth, 0, strArgs1.length);
        System.arraycopy(strArgs2, 0, strBoth, strArgs1.length, strArgs2.length);
        return strBoth;
    }
}
