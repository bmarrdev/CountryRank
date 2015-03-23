/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.StartScreen.StartActivity;

/**
 * @author BRM20150313
 *         <p/>
 *         Splash screen to display while generating the database of countries to be used as the main
 *         dataset for the application
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState == null) {
            new GenerateDatabaseTask(this).execute();
        }

        RankActivity.loadAudioEngine(getApplicationContext());
    }

    private class GenerateDatabaseTask extends AsyncTask<Integer, Integer, Integer> {
        private Context mContext;

        public GenerateDatabaseTask(@NonNull Context context/*, @NonNull View root, @NonNull QuestionType type*/) {
            mContext = context;
        }

        protected Integer doInBackground(Integer... resourceId) {
            //PopulateDatabase.execute(mContext, PopulateDatabase.PopulateCommand.POPULATE_IF_EMPTY);
//            DbTableCountry tableCountry = new DbTableCountry(getApplicationContext());
//            tableCountry.open();
//            tableCountry.outputDatabaseStats();
//            tableCountry.close();
            return 0;
        }

        protected void onPostExecute(Integer value) {
//            DbTableCountry tableCountry = new DbTableCountry(getApplicationContext());
//            tableCountry.open();
//            tableCountry.outputDatabaseStats();
//            tableCountry.close();

            Intent intent = new Intent(SplashActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
