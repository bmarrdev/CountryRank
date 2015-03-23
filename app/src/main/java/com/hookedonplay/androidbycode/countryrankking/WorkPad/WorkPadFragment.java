/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.WorkPad;

import android.support.v4.app.Fragment;

/**
 * @author BRM20150315
 */
public abstract class WorkPadFragment extends Fragment {
    protected GameListener mCallback;

    public interface GameListener {
        public void onQuestionFinished(int result, int total);
        public void onLoadFinished();
        public void onHideFinished();
    }

    public abstract int getResult();
    public abstract void loadWorkPad();
    public abstract void saveWorkPad();

    /**
     * Attach a callback to a given fragment to receive menu
     * selections for processing
     *
     * @param fragment that will receive the callback for notification
     *
     * @throws ClassCastException Fragment does not implement callback interface
     */
    public void attachCallback(Fragment fragment)
            throws ClassCastException
    {
        if (mCallback != null) {
            return;
        }

        try {
            mCallback = (GameListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement GameListener");
        }
    }

}
