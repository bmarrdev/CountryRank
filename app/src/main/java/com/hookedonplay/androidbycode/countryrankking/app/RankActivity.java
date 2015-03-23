/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hookedonplay.androidbycode.countrydatabase.DbCountry;
import com.hookedonplay.androidbycode.countryrankking.Audio.AudioEngine;
import com.hookedonplay.androidbycode.countryrankking.CountryDisplay.CountryActivity;
import com.hookedonplay.androidbycode.countryrankking.EffectBlur.BlurBehind;
import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150310
 *         <p/>
 *         Main Application Activity which hosts the GameFragment.  This Activity is responsible for
 *         handing the toolbar and menu actions
 */
public class RankActivity extends ActionBarActivity {
    static public AudioEngine mAudioEngine;

    static public void loadAudioEngine(Context context) {
        if (mAudioEngine == null) {
            mAudioEngine = new AudioEngine();
            mAudioEngine.initSounds(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            GameFragment gameFragment = new GameFragment();
            gameFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, gameFragment)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAbout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Show country dialog Activity to the user
     *
     * @param dbId country id to show
     */
    private void showCountry(final long dbId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RankActivity.this, CountryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(getString(R.string.database_country), dbId);
                startActivity(intent);
            }
        };
        BlurBehind.getInstance().execute(RankActivity.this, runnable);
    }

    public void onClickCountry(View view) {
        DbCountry country = (DbCountry) view.getTag();
        if (country != null) {
            showCountry(country.getDbId());
        }
    }

    /**
     * Show application about dialog activity
     */
    private void showAbout() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RankActivity.this, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        };
        BlurBehind.getInstance().execute(RankActivity.this, runnable);
    }
}
