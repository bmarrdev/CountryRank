/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hookedonplay.androidbycode.countrydatabase.CountryDifficulty;
import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.app.GameFragment;

/**
 * @author BRM20150315
 *         <p/>
 *         Preferences for the application that are used for storing settings across runs of the app
 *         and also when to store values between onpause() and onresume() lifecycle
 */
public class GamePreferences {
    private static final String TAG = "GamePreferences";
    private Context mContext;

    public GamePreferences(Context context) {
        mContext = context;
    }

    public void putGameState(GameFragment.GameState state) {
        savePref(mContext.getString(R.string.pref_game_state), state.ordinal());
    }

    public GameFragment.GameState getGameState() {
        try {
            int state = getPref(mContext.getString(R.string.pref_game_state),
                    GameFragment.GameState.GAME_STATE_QUESTION.ordinal());
            return (GameFragment.GameState.values()[state]);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            Log.e(TAG, "Error: Invalid game state stored");
            return GameFragment.GameState.GAME_STATE_QUESTION;
        }
    }

    public void putNumberLines(final int lines) {
        savePref(mContext.getString(R.string.pref_number_items), lines);
    }

    public int getNumberLines() {
        return getPref(mContext.getString(R.string.pref_number_items), mContext.getResources().getInteger(R.integer.list_item_count_default));
    }

    @SuppressWarnings("unused")
    public void putRandomQuestion(final boolean random) {
        savePref(mContext.getString(R.string.pref_random_question), random ? 1 : 0);
    }

    @SuppressWarnings("unused")
    public boolean getRandomQuestion() {
        return 0 != getPref(mContext.getString(R.string.pref_number_items), 0);
    }

    @SuppressWarnings("unused")
    public void putFirstRun(final boolean firstRun) {
        savePref(mContext.getString(R.string.pref_first_run), firstRun ? 1 : 0);
    }

    @SuppressWarnings("unused")
    public boolean getFirstRun() {
        return 0 != getPref(mContext.getString(R.string.pref_first_run), 1);
    }

    public void putQuestionType(QuestionType questionType) {
        savePref(mContext.getString(R.string.pref_questions), questionType.ordinal());
    }

    public QuestionType getQuestionType() {
        QuestionType questionType;
        try {
            questionType = QuestionType.values()[getPref(mContext.getString(R.string.pref_questions), QuestionType.QUESTION_POPULATION.ordinal())];
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            questionType = QuestionType.QUESTION_POPULATION;
        }
        return questionType;
    }


    public void putDifficulty(CountryDifficulty difficulty) {
        savePref(mContext.getString(R.string.pref_difficulty), difficulty.ordinal());
    }

    public CountryDifficulty getDifficulty() {
        CountryDifficulty difficulty;
        try {
            difficulty = CountryDifficulty.values()[getPref(mContext.getString(R.string.pref_difficulty), CountryDifficulty.DIFFICULTY_EASY.ordinal())];
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            difficulty = CountryDifficulty.DIFFICULTY_EASY;
        }
        return difficulty;
    }

    public void putScore(int score) {
        savePref(mContext.getString(R.string.pref_current_score), score);
    }

    public int getScore() {
        return getPref(mContext.getString(R.string.pref_current_score), 0);
    }

    public void putHighestScore(int score) {
        savePref(getGamePrefsName(), score);
    }

    public int getHighestScore() {
        return getPref(getGamePrefsName(), 0);
    }

    public void putCountryList(@Nullable long[] countryIds) {
        if (countryIds == null) {
            savePref(mContext.getString(R.string.pref_country_list), "");
            return;
        }

        String list = "";
        for (long databaseId : countryIds) {
            list += databaseId + ";";
        }
        savePref(mContext.getString(R.string.pref_country_list), list);
    }


    public long[] getCountryList()
            throws NumberFormatException {
        long[] databaseIds;

        String str = getPrefString(mContext.getString(R.string.pref_country_list), "");
        String[] strArray = str.split(";");
        databaseIds = new long[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            databaseIds[i] = Long.parseLong(strArray[i]);
        }

        return databaseIds;

    }

    private void savePref(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getString(R.string.pref_game), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void savePref(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getString(R.string.pref_game), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @SuppressWarnings("unused")
    private int getPref(String key) {
        return getPref(key, -1);
    }

    private int getPref(String key, int defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getString(R.string.pref_game), Activity.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    private String getPrefString(String key, String defaultValue) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getString(R.string.pref_game), Activity.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * Generate a preference name to save the highest score for the current game
     * settings
     *
     * @return String containing name to be used to load and store highest scores
     */
    public String getGamePrefsName() {

        final int pref_questionType = getPref(mContext.getString(R.string.pref_questions), QuestionType.QUESTION_POPULATION.ordinal());
        final int defaultItemCount = mContext.getResources().getInteger(R.integer.list_item_count_default);
        final int pref_itemCount = getPref(mContext.getString(R.string.pref_number_items), defaultItemCount);
        final int pref_difficulty = getPref(mContext.getString(R.string.pref_difficulty), CountryDifficulty.DIFFICULTY_EASY.ordinal());
        final int pref_continents = 0; //TODO: Continents
        String prefName = "hs_" + pref_questionType + "_" + pref_itemCount + "_" + pref_difficulty + "_" + pref_continents;
        Log.v(TAG, "Generating preference name : " + prefName);
        return prefName;
    }
}
