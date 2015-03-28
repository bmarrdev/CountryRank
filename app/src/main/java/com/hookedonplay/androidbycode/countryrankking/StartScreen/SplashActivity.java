/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.StartScreen;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hookedonplay.androidbycode.countrydatabase.PopulateDatabase;
import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150327
 *         <p/>
 *         Splash screen to display while generating the database of countries to be used as the main
 *         dataset for the application
 *         On completion of database generation the activity closes itself
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState == null) {
            new GenerateDatabaseTask(this).execute();
        }
    }

    private class GenerateDatabaseTask extends AsyncTask<Integer, Integer, Integer> {
        private Context mContext;

        public GenerateDatabaseTask(@NonNull Context context/*, @NonNull View root, @NonNull QuestionType type*/) {
            mContext = context;
        }

        protected Integer doInBackground(Integer... resourceId) {
            PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_IF_EMPTY);
            return 0;
        }

        protected void onPostExecute(Integer value) {
            finish();
        }
    }
}
