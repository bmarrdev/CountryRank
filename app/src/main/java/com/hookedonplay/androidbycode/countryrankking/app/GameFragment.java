/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hookedonplay.androidbycode.countryrankking.util.GamePreferences;
import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.util.ScoreboardView;
import com.hookedonplay.androidbycode.countryrankking.util.Scorecard;
import com.hookedonplay.androidbycode.countryrankking.WorkPad.SwapFragment;
import com.hookedonplay.androidbycode.countryrankking.WorkPad.WorkPadFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * @author BRM20150312
 *
 */
public class GameFragment extends Fragment implements WorkPadFragment.GameListener, Observer {
    private static final String TAG = "GameFragment";
    /**
     * Current game state
     */
    private GameState mGameState;
    /**
     * Scorecard for the current game
     */
    private Scorecard mScorecard;
    /**
     * Preferences
     */
    private GamePreferences mPrefs;
    /**
     * Flag to be set on creation to determine if the activity has been restored
     * via a screen rotate or app switch
     */
    private boolean mRestoreGame;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_game, container, false);


        mPrefs = new GamePreferences(getActivity().getApplicationContext());

        mRestoreGame = (savedInstanceState != null);

        // Create the scorecard and attach the scoreboard as an observer, so it can be updated as the score changes
        mScorecard = new Scorecard();
        ScoreboardView scoreboardView = (ScoreboardView) rootView.findViewById(R.id.customViewScoreboard);
        mScorecard.addObserver(scoreboardView);
        mScorecard.addObserver(this);

        mScorecard.changeGameType(mPrefs.getHighestScore());


        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.bundle_lines), mPrefs.getNumberLines());
        bundle.putSerializable(getString(R.string.bundle_difficulty), mPrefs.getDifficulty());
        bundle.putSerializable(getString(R.string.bundle_question), mPrefs.getQuestionType());

        if (savedInstanceState == null) {
            setGameState(rootView, GameState.GAME_STATE_QUESTION);
            final SwapFragment swapFragment = new SwapFragment();
            swapFragment.attachCallback(this);

            swapFragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.container_workpad, swapFragment)
                    .commit();

        }

        final Button buttonAction = (Button) rootView.findViewById(R.id.buttonGo);
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwapFragment fragment = (SwapFragment) getChildFragmentManager().getFragments().get(0);
                if (mGameState == GameState.GAME_STATE_FINISHED) {
                    mScorecard.setScore(0);
                    setGameState(GameState.GAME_STATE_QUESTION);
                    fragment.nextQuestion();
                } else if (mGameState == GameState.GAME_STATE_QUESTION) {
                    fragment.checkAnswers(false);
                    setGameState(GameState.GAME_STATE_ANSWERS);
                } else {
                    fragment.nextQuestion();
                    setGameState(GameState.GAME_STATE_QUESTION);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveGameState();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreGameState();
    }

    /**
     * Save the current game state so it can be restored later
     */
    public void saveGameState() {
        if (mScorecard != null) {
            mPrefs.putScore(mScorecard.getCurrentScore());
        }

        mPrefs.putGameState(mGameState);

        SwapFragment fragment = (SwapFragment) getChildFragmentManager().getFragments().get(0);
        final long[] databaseIds = fragment.getDatabaseIdList();
        mPrefs.putCountryList(databaseIds);

    }

    /**
     * Restore the last game state. This is called when the app has been rotated or restored
     * after being switched
     */
    public void restoreGameState() {
        if (!mRestoreGame) {
            return;
        }

        setGameState(mPrefs.getGameState());
        if (mScorecard != null) {
            int score = mPrefs.getScore();
            mScorecard.changeGameType(mPrefs.getHighestScore());
            mScorecard.setScore(score);
        }

        SwapFragment fragment = (SwapFragment) getChildFragmentManager().getFragments().get(0);
        fragment.attachCallback(this);

        long[] databaseIds;
        try {

            databaseIds = mPrefs.getCountryList();
            if (databaseIds != null) {

                fragment.insertCountries(databaseIds);
                //generateGame(null, null, databaseIds);
                if (mGameState != GameState.GAME_STATE_QUESTION) {
                    fragment.checkAnswers(true);
                }

            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "Unable to restore list of countries to inject");
            //startGame(null, null);
            fragment.insertCountries(null);
        }
    }

    private void setGameState(GameState state) {
        setGameState(null, state);
    }

//    private void swapWorkPadFragments() {
//        SwipeFragment swipeFragment = new SwipeFragment();
//        swipeFragment.attachCallback(this);
//
//        //Fragment frag = getChildFragmentManager().getFragments().get(0);
//        getChildFragmentManager().beginTransaction().replace(R.id.container_workpad, swipeFragment).commit();
//    }

    private void setGameState(View root, GameState state) {
        mGameState = state;

        Button button;
        if (root != null) {
            button = (Button) root.findViewById(R.id.buttonGo);
        } else {
            button = (Button) getActivity().findViewById(R.id.buttonGo);
        }
        if (mGameState == GameState.GAME_STATE_FINISHED) {
            button.setText(R.string.button_new_game);
        } else if (mGameState == GameState.GAME_STATE_QUESTION) {
            button.setText(R.string.button_answer);
        } else if (mGameState == GameState.GAME_STATE_ANSWERS) {
            button.setText(R.string.button_next);
        }
    }

    /**
     * Scorecard observer to monitor changes in the scorecard such as new high score
     *
     * @param observable Scorecard observable
     * @param data       type of event {@link com.hookedonplay.androidbycode.countryrankking.util.Scorecard.GameScoreEvent}
     */
    @Override
    public void update(Observable observable, Object data) {
        Scorecard.GameScoreEvent event = (Scorecard.GameScoreEvent) data;
        if (Scorecard.GameScoreEvent.GAMEEVENT_FINISHED == event) {
            setGameState(GameState.GAME_STATE_FINISHED);
        } else if (Scorecard.GameScoreEvent.GAMEEVENT_NEW_HIGH_SCORE == event) {
            mPrefs.putHighestScore(mScorecard.getHighestScore());
        }
    }

    @Override
    public void onQuestionFinished(int result, int total) {
        mScorecard.incrementScore(result, total);
    }

    @Override
    public void onLoadFinished() {

    }

    @Override
    public void onHideFinished() {

    }

    public enum GameState {
        GAME_STATE_QUESTION, // State question has been asked and awaiting response from player
        GAME_STATE_ANSWERS, // State answers have been shown to player
        GAME_STATE_FINISHED // State game finished as player got question wrong
    }
}
