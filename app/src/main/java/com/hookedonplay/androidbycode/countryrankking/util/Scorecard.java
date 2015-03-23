/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.util;

import java.util.Observable;
import java.util.Observer;

/**
 * @author BRM20150315
 *         <p/>
 *         Scorecard object to maintain the current score and notify any observers of change to the score
 *         or the highest score
 */
public class Scorecard extends Observable {
    @SuppressWarnings("unused")
    private static final String TAG = "Scorecard";
    private int mCurrentScore;
    private int mHighestScore;
    public Scorecard() {
    }

    /**
     * add a new observer to the scorecard to be notified of any changes to the current or
     * highest score.
     * <p/>
     * This overrides {@link java.util.Observable#addObserver(java.util.Observer)}
     *
     * @param observer Observer object to be added to the list
     */
    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
        observer.update(this, GameScoreEvent.GAMEEVENT_SCORE_CHANGED);
    }

    /**
     * Retrieve the current score
     *
     * @return current game score
     */
    public int getCurrentScore() {
        return mCurrentScore;
    }

    /**
     * Retrieve the highest score for the current game type
     *
     * @return highest score
     */
    public int getHighestScore() {
        return mHighestScore;
    }

    /**
     * Game options have been changed so the current and highest score must be updated
     *
     * @param highestScore highest score for new game type
     */
    public void changeGameType(final int highestScore) {
        mHighestScore = highestScore;
        mCurrentScore = 0;
        setChanged();
        notifyObservers(GameScoreEvent.GAMEEVENT_SCORE_CHANGED);
    }

    /**
     * Set the current score and notify observers of change
     *
     * @param score new score
     */
    public void setScore(final int score) {
        mCurrentScore = score;
        setChanged();
        notifyObservers(GameScoreEvent.GAMEEVENT_SCORE_CHANGED);
    }

    /**
     * Increment the current score and determine if game is finished
     *
     * @param correct number of answers correct
     * @param total   number of questions
     */
    public void incrementScore(final int correct, final int total) {
        incrementScore(correct);
        if (correct < total) {
            setGameFinished();
        }
    }

    /**
     * Increment the current score by 1
     */
    @SuppressWarnings("unused")
    public void incrementScore() {
        incrementScore(1);
    }

    /**
     * Increment the current score by any amount
     *
     * @param count amount to increment current score
     */
    public void incrementScore(final int count) {
        mCurrentScore += count;

        if (mCurrentScore > mHighestScore) {
            mHighestScore = mCurrentScore;
            setChanged();
            notifyObservers(GameScoreEvent.GAMEEVENT_NEW_HIGH_SCORE);
        }

        setChanged();
        notifyObservers(GameScoreEvent.GAMEEVENT_SCORE_CHANGED);
    }

    /**
     * Set the current game as finished
     */
    public void setGameFinished() {
        setChanged();
        notifyObservers(GameScoreEvent.GAMEEVENT_FINISHED);
    }

    public enum GameScoreEvent {GAMEEVENT_SCORE_CHANGED, GAMEEVENT_RESET, GAMEEVENT_FINISHED, GAMEEVENT_NEW_HIGH_SCORE}
}
