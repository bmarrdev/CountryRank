/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.StartScreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hookedonplay.androidbycode.countryrankking.util.QuestionType;
import com.hookedonplay.androidbycode.countryrankking.R;

/**
 * @author BRM20150315
 *
 * Fragment used to represent each game type and allow the user to start a game of that type
 *
 */
public class MenuOptionFragment extends Fragment {

    private QuestionType mQuestionType;

    static private String BUNDLE_QUESTION_TYPE = "BUNDLE_QUESTION_TYPE";

    public static MenuOptionFragment newInstance(QuestionType questionType) {
        MenuOptionFragment fragmentMenu = new MenuOptionFragment();
        Bundle args = new Bundle();
        if (questionType != null) {
            args.putSerializable(BUNDLE_QUESTION_TYPE, questionType);
        }

        fragmentMenu.setArguments(args);
        return fragmentMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionType = (QuestionType) getArguments().getSerializable(BUNDLE_QUESTION_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_option, container, false);

        View button = view.findViewById(R.id.buttonStartGame);
        ImageView image = (ImageView) view.findViewById(R.id.imageViewMenuOption);
        button.setTag(mQuestionType.ordinal());
        switch (mQuestionType) {
            case QUESTION_AREA:
                image.setImageResource(R.drawable.category_area);
                break;
            case QUESTION_GDP:
                image.setImageResource(R.drawable.category_gdp);
                break;
            case QUESTION_GDP_PER_CAPITA:
                image.setImageResource(R.drawable.category_gdp_per_capita);
                break;
            case QUESTION_POPULATION:
                image.setImageResource(R.drawable.category_population);
                break;
            case QUESTION_POPULATION_DENSITY:
                image.setImageResource(R.drawable.category_population_density);
                break;
            case QUESTION_LATITUDE:
                image.setImageResource(R.drawable.category_latitude);
                break;
            case QUESTION_LIFE_EXPECTANCY:
                image.setImageResource(R.drawable.category_life_expectancy);
                break;
            case QUESTION_LAND_BORDERS:
                image.setImageResource(R.drawable.category_borders);
                break;
            case QUESTION_COASTLINE_LENGTH:
                image.setImageResource(R.drawable.category_coastline);
                break;
            default:
                image.setImageResource(R.drawable.category_gdp);
        }

        return view;
    }
}