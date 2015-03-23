/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.util;

import android.support.annotation.NonNull;

import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150315
 *         <p/>
 *         Generates string ids based on QuestionTypes
 */
public class LabelMaker {
    @SuppressWarnings("unused")
    private static final String TAG = "LabelMaker";

    static public String getQuestion(QuestionType type)
            throws IllegalStateException {
        switch (type) {
            case QUESTION_AREA:
                return "Rank by area";
            case QUESTION_LATITUDE:
                return "Rank from North to South";
            case QUESTION_POPULATION:
                return "Rank by population";
            case QUESTION_POPULATION_DENSITY:
                return "Rank by population density";
            case QUESTION_GDP:
                return "Rank by GDP";
            case QUESTION_GDP_PER_CAPITA:
                return "Rank by GDP per capita";
            case QUESTION_COASTLINE_LENGTH:
                return "Rank by coast length";
            case QUESTION_LAND_BORDERS:
                return "Rank by bordering countries";

            default:
                throw new IllegalStateException("Unknown question type");
        }
    }

    /**
     * Retrieve label id for the rank top or bottom of the swap wordpad
     *
     * @param type QuestionType for the label
     * @param top  bottom or top label
     * @return resource ID for requested label string
     * @throws IllegalStateException for unknown question type
     */
    static public int getLabel(@NonNull QuestionType type, final boolean top)
            throws IllegalStateException {
        if (type == null) {
            throw new IllegalStateException("Unknown question type");
        }
        switch (type) {
            case QUESTION_AREA:
                return top ? R.string.rank_area_highest : R.string.rank_area_lowest;
            case QUESTION_LATITUDE:
                return top ? R.string.rank_latitude_north : R.string.rank_latitude_south;
            case QUESTION_POPULATION:
                return top ? R.string.rank_population_highest : R.string.rank_population_lowest;
            case QUESTION_POPULATION_DENSITY:
                return top ? R.string.rank_density_highest : R.string.rank_density_lowest;
            case QUESTION_GDP:
                return top ? R.string.rank_gdp_highest : R.string.rank_gdp_lowest;
            case QUESTION_GDP_PER_CAPITA:
                return top ? R.string.rank_gdp_capita_highest : R.string.rank_gdp_capita_lowest;
            case QUESTION_COASTLINE_LENGTH:
                return top ? R.string.rank_coastline_highest : R.string.rank_coastline_lowest;
            case QUESTION_LAND_BORDERS:
                return top ? R.string.rank_borders_highest : R.string.rank_borders_lowest;

            default:
                throw new IllegalStateException("Unknown question type");
        }
    }

    /**
     * Retrieve icon id for the rank top or bottom of the swap wordpad
     *
     * @param type QuestionType for the label
     * @param top  bottom or top label
     * @return resource ID for requested icon drawable id
     * @throws IllegalStateException for unknown question type
     */
    static public int getIconResourceId(@NonNull QuestionType type, final boolean top)
            throws IllegalStateException {
        switch (type) {
            case QUESTION_AREA:
                return top ? R.drawable.icon_area_highest : R.drawable.icon_area_lowest;
            case QUESTION_LATITUDE:
                return top ? R.drawable.icon_latitude_highest : R.drawable.icon_latitude_lowest;
            case QUESTION_POPULATION:
                return top ? R.drawable.icon_population_highest : R.drawable.icon_population_lowest;
            case QUESTION_POPULATION_DENSITY:
                return top ? R.drawable.icon_population_highest : R.drawable.icon_population_lowest;
            case QUESTION_GDP:
                return top ? R.drawable.icon_gdp_highest : R.drawable.icon_gdp_lowest;
            case QUESTION_GDP_PER_CAPITA:
                return top ? R.drawable.icon_gdp_percapital_highest : R.drawable.icon_gdp_percapital_lowest;
            case QUESTION_COASTLINE_LENGTH:
                return top ? R.drawable.icon_coast_highest : R.drawable.icon_coast_lowest;
            case QUESTION_LAND_BORDERS:
                return top ? R.drawable.icon_borders_highest : R.drawable.icon_borders_lowest;
            default:
                throw new IllegalStateException("Unknown question type");
        }
    }
}
