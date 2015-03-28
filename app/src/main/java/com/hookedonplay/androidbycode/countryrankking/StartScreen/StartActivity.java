/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.StartScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hookedonplay.androidbycode.countrydatabase.CountryDifficulty;
import com.hookedonplay.androidbycode.countryrankking.util.GamePreferences;
import com.hookedonplay.androidbycode.countryrankking.util.QuestionType;
import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.app.RankActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

/**
 * @author BRM20150315
 *
 * Start menu and settings page. Entry point for the user
 */
public class StartActivity extends FragmentActivity {

    MenuPagerAdapter mAdapterViewPager;

    private FloatingActionMenu mItemCountMenu;
    private FloatingActionMenu mDifficultyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initMenuDifficulty();
        initMenuItemCount();

        setupGameTypePager();

        RankActivity.loadAudioEngine(getApplicationContext());

        Intent intent = new Intent(StartActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    /**
     * Initialize the pager to swipe through the various game types to select the
     * game to start
     */
    private void setupGameTypePager() {
        ViewPager vpPager = (ViewPager) findViewById(R.id.pagerQuestions);
        vpPager.setClipToPadding(false);

        vpPager.setPageMargin(16);
        vpPager.setClipChildren(false);

        mAdapterViewPager = new MenuPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(mAdapterViewPager);

        GamePreferences prefs = new GamePreferences(getApplicationContext());

        vpPager.setCurrentItem(mAdapterViewPager.getItemIndex(prefs.getQuestionType()));
    }

    public void startGame(View view) {
        int type = (int) view.getTag();
        QuestionType questionType = (QuestionType.values()[type]);
        GamePreferences prefs = new GamePreferences(getApplicationContext());
        prefs.putQuestionType(questionType);
        //prefs.putRandomQuestion(questionType == QuestionType.QUESTION_RANDOM);

        Intent intent = new Intent(StartActivity.this, RankActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private ImageView createImageButtonDiff(final ImageView actionButton, final int resourceId, final int difficulty) {
        ImageView imageRows = new ImageView(this/*getActivity()*/);
        imageRows.setImageResource(resourceId);
        imageRows.setTag(R.string.tag_resourceid, resourceId);
        imageRows.setTag(R.string.tag_difficulty, difficulty);
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageRows.setLayoutParams(tvParams);
        imageRows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.setImageResource((int) v.getTag(R.string.tag_resourceid));
                if (mDifficultyMenu != null) {
                    mDifficultyMenu.close(true);
                }
                GamePreferences prefs = new GamePreferences(getApplicationContext());
                prefs.putDifficulty(CountryDifficulty.values()[(int) v.getTag(R.string.tag_difficulty)]);
            }
        });

        return imageRows;
    }

    private ImageView createImageButton(final ImageView actionButton, final int resourceId, final int numberItems) {
        ImageView imageRows = new ImageView(this);
        imageRows.setImageResource(resourceId);
        imageRows.setTag(R.string.tag_resourceid, resourceId);
        imageRows.setTag(R.string.tag_itemcount, numberItems);
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        imageRows.setLayoutParams(tvParams);
        imageRows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.setImageResource((int) v.getTag(R.string.tag_resourceid));
                if (mItemCountMenu != null) {
                    mItemCountMenu.close(true);
                }
                GamePreferences prefs = new GamePreferences(getApplicationContext());
                prefs.putNumberLines((int) v.getTag(R.string.tag_itemcount));
            }
        });

        return imageRows;
    }

    private void initMenuItemCount() {
        final ImageView actionButton = (ImageView) findViewById(R.id.imageViewLines);
        //Animation a = AnimationUtils.loadAnimation(this, R.anim.anim_menu_selected);
        //actionButton.startAnimation(a);

        ImageView imageRows3 = createImageButton(actionButton, R.drawable.items_three, 3);
        ImageView imageRows4 = createImageButton(actionButton, R.drawable.items_four, 4);
        ImageView imageRows5 = createImageButton(actionButton, R.drawable.items_five, 5);

        GamePreferences prefs = new GamePreferences(getApplicationContext());
        switch (prefs.getNumberLines()) {
            case 3:
                actionButton.setImageResource(R.drawable.items_three);
                break;
            case 5:
                actionButton.setImageResource(R.drawable.items_five);
                break;
            default:
                actionButton.setImageResource(R.drawable.items_four);
                break;
        }

        mItemCountMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(245)
                .setEndAngle(400)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_medium))
                .addSubActionView(imageRows3)
                .addSubActionView(imageRows4)
                .addSubActionView(imageRows5)
                .attachTo(actionButton)
                .build();
    }

    private void initMenuDifficulty() {
        final ImageView actionButton = (ImageView) findViewById(R.id.imageViewDifficulty);
        //Animation a = AnimationUtils.loadAnimation(this, R.anim.anim_menu_selected);
        //actionButton.startAnimation(a);

        ImageView imageEasy = createImageButtonDiff(actionButton, R.drawable.difficulty_easy, CountryDifficulty.DIFFICULTY_EASY.ordinal());
        ImageView imageHard = createImageButtonDiff(actionButton, R.drawable.difficulty_hard, CountryDifficulty.DIFFICULTY_HARD.ordinal());
        ImageView imageHarder = createImageButtonDiff(actionButton, R.drawable.difficulty_harder, CountryDifficulty.DIFFICULTY_HARDER.ordinal());
        ImageView imageInsane = createImageButtonDiff(actionButton, R.drawable.difficulty_insane, CountryDifficulty.DIFFICULTY_INSANE.ordinal());

        CountryDifficulty difficulty;
        try {
            GamePreferences prefs = new GamePreferences(getApplicationContext());
            //difficulty = CountryDifficulty.values()[pre(getString(R.string.pref_difficulty))];
            difficulty = prefs.getDifficulty();
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            difficulty = CountryDifficulty.DIFFICULTY_EASY;
        }

        switch (difficulty) {
            case DIFFICULTY_INSANE:
                actionButton.setImageResource(R.drawable.difficulty_insane);
                break;
            case DIFFICULTY_HARD:
                actionButton.setImageResource(R.drawable.difficulty_hard);
                break;
            case DIFFICULTY_HARDER:
                actionButton.setImageResource(R.drawable.difficulty_harder);
                break;
            default:
                actionButton.setImageResource(R.drawable.difficulty_easy);
                break;
        }

        mDifficultyMenu = new FloatingActionMenu.Builder(this/*getActivity()*/)
                .setStartAngle(145)
                .setEndAngle(305)
                .setRadius(getResources().getDimensionPixelSize(R.dimen.radius_medium))
                .addSubActionView(imageEasy)
                .addSubActionView(imageHard)
                .addSubActionView(imageHarder)
                .addSubActionView(imageInsane)
                .attachTo(actionButton)
                .build();
    }

    public static class MenuPagerAdapter extends FragmentPagerAdapter {
        private static QuestionType[] mMenuItems = {
                QuestionType.QUESTION_POPULATION,
                QuestionType.QUESTION_LATITUDE,
                QuestionType.QUESTION_AREA,
                QuestionType.QUESTION_LAND_BORDERS,
                QuestionType.QUESTION_COASTLINE_LENGTH,
                QuestionType.QUESTION_GDP,
                QuestionType.QUESTION_POPULATION_DENSITY,
                QuestionType.QUESTION_GDP_PER_CAPITA
        };

        public MenuPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        public int getItemIndex(QuestionType type) {
            for (int i = 0; i < mMenuItems.length; i++) {
                if (type == mMenuItems[i]) {
                    return i;
                }
            }
            return 0;
        }

        @Override
        public float getPageWidth(int position) {
            return 0.93f;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return mMenuItems.length;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return MenuOptionFragment.newInstance(mMenuItems[position]);
        }
    }
}
