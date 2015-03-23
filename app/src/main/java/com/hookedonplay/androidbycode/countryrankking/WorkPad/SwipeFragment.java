/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.WorkPad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150318
 *
 * TODO: Implement a swipe type fragment for a different type of gamestyle. This will work by
 * presenting a list of flags, say two columns of 5 flags and then allow the player to 'swipe to
 * dismiss' flags that do not meet a given criteria. For example the criteria may be 'Commonwealth
 * nation'. The player will remove all flags that are not a member of the commonwealth.
 *
 * This class will extend the same Wordpad fragment as the Swap Fragment so it can be inserted into
 * game easily for any question.
 */
@SuppressWarnings("unused")
public class SwipeFragment extends WorkPadFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_workpad_swipe, container, false);

        //TODO: Remove these sample buttons with a real game
        final Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onQuestionFinished(1, 1);
            }
        });

        final Button button2 = (Button) rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onQuestionFinished(0, 1);
            }
        });
        return rootView;
    }

    @Override
    public int getResult() {
        return 0;
    }

    @Override
    public void loadWorkPad() {

    }

    @Override
    public void saveWorkPad() {

    }
}
