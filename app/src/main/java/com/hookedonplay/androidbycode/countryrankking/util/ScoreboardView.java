/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hookedonplay.androidbycode.countryrankking.R;

import java.util.Observable;
import java.util.Observer;

/**
 * @author BRM20150315
 *         <p/>
 *         Scoreboard to show the current score and highest score
 */
public class ScoreboardView extends RelativeLayout implements Observer {
    private static final String TAG = "ScoreboardView";

    private TextView mScore;

    public ScoreboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_scoreboard, this, true);
        mScore = (TextView) findViewById(R.id.textViewCurrentScore);
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.v(TAG, "Scorecard updated, updating scoreboard");

        Scorecard sc = (Scorecard) observable;
        if (sc == null) {
            Log.e(TAG, "Unable to cast observable to the scorecard.");
            return;
        }

        Scorecard.GameScoreEvent event = (Scorecard.GameScoreEvent) data;
        if (event != null) {
            if (event == Scorecard.GameScoreEvent.GAMEEVENT_SCORE_CHANGED ||
                    event == Scorecard.GameScoreEvent.GAMEEVENT_FINISHED) {
                mScore.setText("Score : " + String.valueOf(sc.getCurrentScore()) + " (" + String.valueOf(sc.getHighestScore() + ")"));
            }
        }
    }
}
