/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hookedonplay.androidbycode.countryrankking.EffectBlur.BlurBehind;
import com.hookedonplay.androidbycode.countryrankking.BuildConfig;
import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150316
 *         <p/>
 *         Activity to show about app details. This includes App name, version, copyright
 *         and credits
 */
public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        BlurBehind.getInstance()
                .withAlpha(80)
                .setBackground(this);

        setVersionString();

        String[] credits = getResources().getStringArray(R.array.array_credits);
        String[] creditsFlags = getResources().getStringArray(R.array.flag_copyright);
        String[] copyrightCredits = new String[credits.length + creditsFlags.length];
        System.arraycopy(credits, 0, copyrightCredits, 0, credits.length);
        System.arraycopy(creditsFlags, 0, copyrightCredits, credits.length, creditsFlags.length);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.about_list_item, copyrightCredits);
        final ListView listCredits = (ListView) findViewById(R.id.listViewCredits);
        listCredits.setAdapter(adapter);

        final View buttonClose = findViewById(R.id.viewClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonCredits = (Button) findViewById(R.id.buttonCredits);
        buttonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCredits.getVisibility() != View.VISIBLE) {
                    animateLayout();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        // Use our own exit transition
        overridePendingTransition(0, R.anim.abc_fade_out);
        super.onPause();
    }

    /**
     * Move the main layout on the page to the top to make room to show the credits page
     */
    private void animateLayout() {
        final View layoutApp = findViewById(R.id.layoutAppDetails);
        layoutApp.animate().x(getResources().getDimension(R.dimen.activity_horizontal_margin)).y(getResources().getDimension(R.dimen.activity_vertical_margin)).setDuration(750).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                showCredits();
            }
        });
        Button buttonCredits = (Button) findViewById(R.id.buttonCredits);
        buttonCredits.setText(getString(R.string.button_close));
    }

    /**
     * Show the credits listview with a gentle fade effect
     */
    private void showCredits() {
        final ListView listCredits = (ListView) findViewById(R.id.listViewCredits);

        AlphaAnimation fade_in = new AlphaAnimation(0.0f, 1.0f);
        fade_in.setDuration(1500);
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation arg0) {
                listCredits.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationEnd(Animation arg0) {

            }
        });
        listCredits.startAnimation(fade_in);
    }

    /**
     * Build a version string to display to user
     */
    private void setVersionString() {
        final TextView versionView = (TextView) findViewById(R.id.textVersion);
        versionView.setText(getString(R.string.about_application_version) + " " + BuildConfig.VERSION_NAME);
    }
}