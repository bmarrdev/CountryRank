/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.WorkPad;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hookedonplay.androidbycode.countrydatabase.DbCountry;
import com.hookedonplay.androidbycode.countryrankking.util.QuestionType;

import java.util.Comparator;

/**
 * @author BRM20150312
 */
public class CountryItem {
    @SuppressWarnings("unused")
    private static final String TAG = "CountryItem";

    public DbCountry mCountry;
    public ImageView mImageResult;
    public ImageView mImageFlag;
    public ImageView mDemoHand;

    public CountryItem(@NonNull DbCountry country,
                       @NonNull ImageView viewResult,
                       @NonNull ImageView viewFlag,
                       @Nullable ImageView demoHand) {
        mCountry = country;
        mImageResult = viewResult;
        mImageFlag = viewFlag;
        mDemoHand = demoHand;
    }

    static public Comparator<CountryItem> getComparator(QuestionType type, SortOrder sortOrder) {
        return new CountryComparator(type, sortOrder);
    }

    public enum SortOrder {SORT_ASCENDING, SORT_DESCENDING}
    static public class CountryComparator implements Comparator<CountryItem>
    {
        private SortOrder mSortOrder;
        private QuestionType mQuestionType;

        public CountryComparator(@NonNull QuestionType questionType, @Nullable SortOrder sortOrder) {
            mQuestionType = questionType;
            mSortOrder = SortOrder.SORT_ASCENDING;
            if (sortOrder != null) {
                mSortOrder = sortOrder;
            }
        }

        public int compare(CountryItem left, CountryItem right)
                            throws IllegalStateException {
            switch(mQuestionType) {
                case QUESTION_POPULATION:
                    return compareValues(left.mCountry.getPopulation(), right.mCountry.getPopulation());
                case QUESTION_AREA:
                    return compareValues(left.mCountry.getArea(), right.mCountry.getArea());
                case QUESTION_LATITUDE:
                    return compareValues(left.mCountry.getCapitalLat(), right.mCountry.getCapitalLat());
                case QUESTION_POPULATION_DENSITY:
                    return compareValues(left.mCountry.getPopulationDensity(), right.mCountry.getPopulationDensity());
                case QUESTION_GDP:
                    return compareValues(left.mCountry.getGdp(), right.mCountry.getGdp());
                case QUESTION_GDP_PER_CAPITA:
                    return compareValues(left.mCountry.getGdpPerCapita(), right.mCountry.getGdpPerCapita());
                case QUESTION_COASTLINE_LENGTH:
                    return compareValues(left.mCountry.getCoastLength(), right.mCountry.getCoastLength());
                case QUESTION_LAND_BORDERS:
                    return compareValues(left.mCountry.getBorders(), right.mCountry.getBorders());

                default:
                    throw new IllegalStateException("Unknown sort element");
            }
        }

        private int compareValues(long left, long right) {
            if (left < right) return mSortOrder == SortOrder.SORT_ASCENDING ? -1 : 1;
            if (left > right) return mSortOrder == SortOrder.SORT_ASCENDING ? 1 : -1;
            return 0;
        }

        private int compareValues(double left, double right) {
            if (left < right) return mSortOrder == SortOrder.SORT_ASCENDING ? -1 : 1;
            if (left > right) return mSortOrder == SortOrder.SORT_ASCENDING ? 1 : -1;
            return 0;
        }
    }
}
