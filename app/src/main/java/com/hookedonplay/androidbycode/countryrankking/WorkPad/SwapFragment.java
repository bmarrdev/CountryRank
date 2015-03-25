/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.WorkPad;

import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.hookedonplay.androidbycode.countrydatabase.CountryDifficulty;
import com.hookedonplay.androidbycode.countrydatabase.DbCountry;
import com.hookedonplay.androidbycode.countryrankking.Audio.AudioEngine;
import com.hookedonplay.androidbycode.countryrankking.util.LabelMaker;
import com.hookedonplay.androidbycode.countryrankking.util.QuestionType;
import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.util.RandomCountryGenerator;
import com.hookedonplay.androidbycode.countryrankking.app.RankActivity;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author BRM20150315
 *
 * SwapFragment allows the player to drag and drop to arrange items in a linear style layout.
 * Items need to be arranged in order of the metric.  For example the linear layout may
 * contain Australia, China & France. The player would need to recognize the flag that represents
 * each country and then arrange the flags in the order China > France > Australia to get maximum
 * points.
 *
 * If the player does not get all in the correct order the game finishes.
 *
 * The bundle passed sets the following attributes:
 *
 * <CountryDifficulty> (Easy..Hard..Harder..Insane) Harder the level the more countries in the
 * pool that may be displayed to be ranked.  In easy mode well known countries with easily
 * destinguished flags will be presented.  In Insane mode there are countries that are obscure
 *
 * <QuestionType> Metric to rank the flags (Population, density, GDP, life expectancy...)
 *
 * <NumberLines> 3, 4 or 5 flags to arrange
 */
public class SwapFragment extends WorkPadFragment implements DragLinearLayout.OnViewSwapListener {

    private static final String TAG = "SwapFragment";

    /**
     * The special linear layout that supports dragging to rearrange items
     */
    private DragLinearLayout mLayout;

    /**
     * The number of flags (countries) that are required to be arranged.  This must be
     * in the range of 3-5
     */
    private int mNumberItems;

    /**
     * The CountryItems we have. This array will contain {@link #mNumberItems} countries. After random
     * countries are generated they are displayed in that order and then this list is sorted in
     * the correct order ready to check if the user gets them correct
     */
    private ArrayList<CountryItem> mCountryViews;

    private ArrayList<LoadResourceTask> mImageLoadList = new ArrayList<>();

    private CountryDifficulty mDifficulty;
    private QuestionType mQuestionType;

    private boolean mFirstRun;
    //private Context mContext;

    /**
     * Palette of colors used for the background of the {@link #mLayout} items. If there are more
     * items than this palette holds the layout will simply loop through to the start
     */
    private int[] mPalette;
    @SuppressWarnings("FieldCanBeLocal")
    private View mHelpView;

    @Override
    public void onPause() {
        super.onPause();
        /**
         * On pause of the page we need to ensure we cancel all the tasks that are generating
         * the image for the flags from the SVG source file
         */
        for (LoadResourceTask loadResourceTask : mImageLoadList) {
            loadResourceTask.cancel(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_workpad_swap, container, false);

        mLayout = (DragLinearLayout) rootView.findViewById(R.id.dragLayoutCountries);

        final int minimum_items = getResources().getInteger(R.integer.list_item_count_minimum);
        final int maximum_items = getResources().getInteger(R.integer.list_item_count_maximum);
        final int default_items = getResources().getInteger(R.integer.list_item_count_default);

        Bundle bundle = this.getArguments();
        mDifficulty = (CountryDifficulty) bundle.getSerializable(getString(R.string.bundle_difficulty));
        mNumberItems = bundle.getInt(getString(R.string.bundle_lines), default_items);
        if (mNumberItems < minimum_items ||
                mNumberItems > maximum_items) {
            throw new IllegalArgumentException("Unsupported number of items " + mNumberItems);
        }
        mQuestionType = (QuestionType) bundle.getSerializable(getString(R.string.bundle_question));
        if (mQuestionType == null) {
            throw new IllegalArgumentException("No question type provided in bundle");
        }

        setResourcesForQuestionType(rootView, mQuestionType);

        //mContext = getActivity();
        mFirstRun = true;
        mPalette = getResources().getIntArray(R.array.material_palette_blue);
        mHelpView = null;

        // We want to listen for the swap messages to play a sound
        mLayout.setOnViewSwapListener(this);

        initDraggableLayout();

        insertCountries(null);

        return rootView;
    }

    public void load() {
        mCallback.onLoadFinished();
    }

    public void next() {

    }

    @SuppressWarnings("unused")
    public boolean setNumberItems(final int numberItems, final boolean reload) {
        if (mNumberItems != numberItems) {
            mNumberItems = numberItems;
            if (reload) {
                reset();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    public boolean setDifficulty(@NonNull CountryDifficulty difficulty, final boolean reload) {
        if (difficulty != mDifficulty) {
            mDifficulty = difficulty;
            if (reload) {
                reset();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    public boolean setQuestionType(@NonNull QuestionType questionType, final boolean reload) {
        if (mQuestionType != questionType) {
            mQuestionType = questionType;
            if (reload) {
                reset();
                return true;
            }
        }
        return false;
    }

    private void reset() {
        initDraggableLayout();
        insertCountries(null);
    }

    private void initDraggableLayout() {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }

        mLayout.setWeightSum(mNumberItems);
        for (int i = 0; i < mNumberItems; i++) {
            inflateNewView(mLayout);
        }
    }

    /**
     * Injects a new view into the linear layout. This view is a country that can be dragged into
     * order within the linear layout
     *
     * @param layout LinearLayout to insert the new view into
     */
    private void inflateNewView(@NonNull DragLinearLayout layout) {

        LayoutInflater layoutInflater = (LayoutInflater) layout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View inflatedView = layoutInflater.inflate(R.layout.country_item, null);
        View inflatedView = layoutInflater.inflate(R.layout.country_item, layout, false);
        /**
         * We have our inflated view, now we just need to set it up in a default state
         */

        // Set the item to a color in our pallete
        inflatedView.setBackgroundColor(mPalette[layout.getChildCount() % mPalette.length]);

        ImageView viewResult = (ImageView) inflatedView.findViewById(R.id.imageViewResult);
        viewResult.setVisibility(View.GONE);

        TextView viewCountryName = (TextView) inflatedView.findViewById(R.id.textViewCountryName);
        viewCountryName.setVisibility(View.GONE);

        ImageView imageViewData = (ImageView) inflatedView.findViewById(R.id.imageViewData);
        imageViewData.setImageResource(R.drawable.flag_generic);
        inflatedView.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //if (layout.getChildCount() > 0) {
        //    layoutParams.setMargins(0, 8, 0, 8);
        //}
        layout.addView(inflatedView, -1, layoutParams);

        /**
         * We need to set this layout item to be draggable
         */
        layout.setViewDraggable(inflatedView, inflatedView);
    }

    /**
     * Get a string containing the result for the given country with the
     * given questionType
     *
     * @param country Country to get result string from
     * @return Answer string
     * @throws IllegalStateException if questionType is unknown
     */
    private String getAnswerForCountry(@NonNull DbCountry country)
            throws IllegalStateException {
        switch (mQuestionType) {
            case QUESTION_POPULATION:
                return country.getPopulationString(getActivity());
            case QUESTION_LATITUDE:
                return country.getDistanceFromEquator(getActivity());
            case QUESTION_AREA:
                return country.getAreaString(getActivity());
            case QUESTION_POPULATION_DENSITY:
                return country.getPopulationDensityString(getActivity());
            case QUESTION_GDP:
                return country.getGdpString(getActivity());
            case QUESTION_GDP_PER_CAPITA:
                return country.getGdpPerCapitaString();
            case QUESTION_COASTLINE_LENGTH:
                return country.getCoastLengthString(getActivity());
            case QUESTION_LAND_BORDERS:
                return country.getBordersString(getActivity());
            default:
                throw new IllegalStateException("Missing Question type");
        }

    }

    /**
     * Inserts the details for a country into an item in the linear layout. We will also prepare
     * the answers by filling in the result and country views and then hiding them until the
     * user has finished arranging the linear layout items
     *
     * @param childItem the child of the linear layout- 1 per country
     * @param country   Database country that will have its details inserted in to the linear layout
     */
    private void insertCountryIntoLayout(@NonNull View childItem, @NonNull DbCountry country) {

        // The result is the answer placed on the bottom right of each item in the linear layout
        TextView viewCountryResult = (TextView) childItem.findViewById(R.id.textViewCountryResult);
        viewCountryResult.setText(getAnswerForCountry(country));
        viewCountryResult.setVisibility(View.INVISIBLE);

        TextView viewCountryName = (TextView) childItem.findViewById(R.id.textViewCountryName);

        // Latitude question is based on capital not country, so we show the name of the capital
        // city in the answer
        if (mQuestionType == QuestionType.QUESTION_LATITUDE) {
            viewCountryName.setText(country.getName() + "\n" + country.getCapital());
        } else {
            viewCountryName.setText(country.getName());
        }
        viewCountryName.setVisibility(View.INVISIBLE);

        ImageView imageViewResult = (ImageView) childItem.findViewById(R.id.imageViewResult);
        imageViewResult.clearAnimation();
        imageViewResult.setVisibility(View.GONE);

        ImageView imageViewData = (ImageView) childItem.findViewById(R.id.imageViewData);
        imageViewData.setImageResource(R.drawable.flag_generic);

        ImageView imageViewHand = (ImageView) childItem.findViewById(R.id.imageViewHand);

        CountryItem countryItem = new CountryItem(country, imageViewResult, imageViewData, imageViewHand);
        mCountryViews.add(countryItem);

        childItem.setTag(countryItem.mCountry);
        setImage(countryItem.mCountry.getFlagID());

        View clickHandlerView = childItem.findViewById(R.id.frameClickInfo);
        clickHandlerView.setTag(countryItem.mCountry);
        clickHandlerView.setVisibility(View.GONE);

        childItem.invalidate();
        childItem.forceLayout();
    }

    /**
     * Insert countries into the layout, either randomly (based on current country filters)
     * or a given list of countries to insert (ie. on resume of app)
     *
     * @param countries list of countries to insert, or null for random
     */
    public void insertCountries(@Nullable long[] countries) {
        mImageLoadList.clear();

        final int numberItems = mLayout.getChildCount();

        boolean restore = (countries != null && countries.length == numberItems);
        RandomCountryGenerator countryGenerator = new RandomCountryGenerator(getActivity(), false, mDifficulty, mQuestionType, null);
        mCountryViews = new ArrayList<>();
        for (int i = 0; i < numberItems; i++) {
            DbCountry country = null;
            if (restore) {
                country = countryGenerator.getCountryFromId(countries[i]);
            }
            if (country == null) {
                country = countryGenerator.getRandomCountry();
            }
            View child = mLayout.getChildAt(i);
            insertCountryIntoLayout(child, country);
        }

        // We can now sort the Array of items into correct order so we can later check the users answers with the correct
        // order
        Collections.sort(mCountryViews, CountryItem.getComparator(mQuestionType, CountryItem.SortOrder.SORT_DESCENDING));

        // We only want to animate if these are no countries, not being restored in onresume()
        animateLayout(countries == null);
    }

    /**
     * Create a simulation of dragging and dropping the items in the country layout to arrange
     * in order
     */
    private void simulateDragAndDrop() {
//
//        final int startY = mLayout.getChildAt(0).getBottom() - 10;
//        final int endY = mLayout.getChildAt(mLayout.getChildCount() - 1).getBottom() - 10;
//
//        CountryItem countryItem = null;
//        DbCountry country = (DbCountry) mLayout.getChildAt(0).getTag();
//        for (CountryItem countryView : mCountryViews) {
//            if (countryView.mCountry.getDbId() == country.getDbId()) {
//                countryItem = countryView;
//                break;
//            }
//        }
//        try {
//            SimulateDragDrop simulateDragDrop = new SimulateDragDrop();
//            simulateDragDrop.setCustomEventListener(new SimulateDragDrop.OnFinishEventListener() {
//                public void onEndDragDropSimulation() {
//                    Log.e(TAG, "Finished drag drop");
//                    mHelpView.setVisibility(View.GONE);
//                }
//            });
//            simulateDragDrop.executeVerticalDrag(countryItem, mLayout, startY, endY, 2000, 100);
//        } catch (IllegalArgumentException iae) {
//            Log.e(TAG, "Unable to initiate drag and drop demonstration");
//            iae.printStackTrace();
//        }
    }

    /**
     * Get the number of answers the user has correct in the current layout
     *
     * @return number correct
     */
    private int getNumberCorrect() {
        int correct = 0;
        for (int i = 0; i < mLayout.getChildCount(); i++) {
            if (isItemCorrect(i)) {
                correct++;
            }
        }
        return correct;
    }

    private boolean isItemCorrect(final int pos) {
        Comparator<CountryItem> cic = CountryItem.getComparator(mQuestionType, CountryItem.SortOrder.SORT_DESCENDING);
        final View child = mLayout.getChildAt(pos);
        DbCountry country = (DbCountry) child.getTag();
        if (country.getDbId() == mCountryViews.get(pos).mCountry.getDbId()) {
            return true;
        } else {
            // check for equal values. This works by looking for countryitems that are the same to check for correctness
            for (int i = 0; i < mLayout.getChildCount(); i++) {
                if (pos != i) {
                    boolean equal = 0 == cic.compare(mCountryViews.get(pos), mCountryViews.get(i));
                    if (equal) {
                        if (country.getDbId() == mCountryViews.get(i).mCountry.getDbId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public long[] getDatabaseIdList()
            throws IllegalStateException {
        try {
            long idList[] = new long[mLayout.getChildCount()];
            for (int i = 0; i < mLayout.getChildCount(); i++) {
                final View child = mLayout.getChildAt(i);
                DbCountry country = (DbCountry) child.getTag();
                idList[i] = country.getDbId();
            }
            return idList;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to retrieve country database ids");
        }
    }

    public void checkAnswers(final boolean restoringLayout) {
        final int numCorrect = getNumberCorrect();

        for (int i = 0; i < mLayout.getChildCount(); i++) {
            final int countryIndex = i;
            final View child = mLayout.getChildAt(i);
            View clickHandlerView = child.findViewById(R.id.frameClickInfo);
            clickHandlerView.setVisibility(View.VISIBLE);
            showStatus(clickHandlerView);

            if (!restoringLayout) {
                Animation animation = AnimationUtils.loadAnimation(mLayout.getContext(), R.anim.anim_reveal_answer);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        showAnswer(child, countryIndex, numCorrect, restoringLayout);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                animation.setStartTime(AnimationUtils.currentAnimationTimeMillis() + (500 * i));//.setStartOffset(1000*i);
                child.setAnimation(animation);
            } else {
                showAnswer(child, countryIndex, numCorrect, restoringLayout);
            }
        }
    }

    private void showAnswer(@NonNull View child,
                            final int countryIndex, final int numCorrect, boolean restoring) {
        final boolean lastItem = (countryIndex == mLayout.getChildCount() - 1);
        //DbCountry country = (DbCountry) child.getTag();
        ImageView viewResult = (ImageView) child.findViewById(R.id.imageViewResult);
        //if (country.getDbId() == mCountryViews.get(countryIndex).mCountry.getDbId()) {
        if (isItemCorrect(countryIndex)) {
            viewResult.setImageResource(R.drawable.result_correct);
            viewResult.setVisibility(View.VISIBLE);
            if (!restoring) {
                RankActivity.mAudioEngine.playSound(AudioEngine.SOUND_CORRECT);
            }
        } else {
            viewResult.setImageResource(R.drawable.result_wrong);
            viewResult.setVisibility(View.VISIBLE);
        }

        child.findViewById(R.id.textViewCountryName).setVisibility(View.VISIBLE);
        child.findViewById(R.id.textViewCountryResult).setVisibility(View.VISIBLE);

        // We don't want to tell the user the score until the animation is finished
        if (lastItem && !restoring) {
            //scorecard.incrementScore(numCorrect, mLayout.getChildCount());
            mCallback.onQuestionFinished(numCorrect, mLayout.getChildCount());
        }

    }

    private void showStatus(View view) {
        Log.e(TAG, "Visibility: " + view.getVisibility() + " Visible: " + (view.getVisibility() == View.VISIBLE));
        Log.e(TAG, "Clickable: " + view.isClickable());
        DbCountry country = (DbCountry) view.getTag();
        if (country != null) {
            Log.e(TAG, "Tag: " + country.getName());
        }
    }

    public void nextQuestion() {
        for (int i = 0; i < mLayout.getChildCount(); i++) {
            View child = mLayout.getChildAt(i);
            View clickHandlerView = child.findViewById(R.id.frameClickInfo);
            clickHandlerView.setVisibility(View.GONE);
            showStatus(clickHandlerView);

            if (mLayout.getVisibility() == View.VISIBLE) {
                Animation animation = AnimationUtils.loadAnimation(mLayout.getContext(), R.anim.anim_country_hide);
                if (i == mLayout.getChildCount() - 1) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (mLayout.getChildCount() != mNumberItems) {
                                initDraggableLayout();
                            }
                            insertCountries(null);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                animation.setStartOffset(100 * i);
                child.startAnimation(animation);
            }
        }
    }

    private void playSoundForPosition(int position) {
        int audio;
        switch (position) {
            case 0:
                audio = AudioEngine.SOUND_DISPLAY_1;
                break;
            case 1:
                audio = AudioEngine.SOUND_DISPLAY_2;
                break;
            case 2:
                audio = AudioEngine.SOUND_DISPLAY_3;
                break;
            case 3:
                audio = AudioEngine.SOUND_DISPLAY_4;
                break;
            default:
                audio = AudioEngine.SOUND_DISPLAY_5;
                break;
        }

        RankActivity.mAudioEngine.playSound(audio);
    }

    private void animateLayout(boolean showAnimationEffect) {
        for (int i = 0; i < mLayout.getChildCount(); i++) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_reveal_country);
            final View countryView = mLayout.getChildAt(i);
            if (!showAnimationEffect) {
                // Boring! no animation, just show the items
                countryView.setVisibility(View.VISIBLE);
                continue;
            }
            final boolean isLastItem = (i == mLayout.getChildCount() - 1);
            final int position = i;
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    countryView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    playSoundForPosition(position);
                    Log.v(TAG, "Finished display of countries");
                    if (isLastItem && mFirstRun) {
                        mFirstRun = false;
                        simulateDragAndDrop();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            animation.setStartTime(AnimationUtils.currentAnimationTimeMillis() + (300 * i));

            countryView.setAnimation(animation);

        }
    }

    public void setResourcesForQuestionType(@NonNull View rootView, @NonNull QuestionType questionType) {
        TextView topDesc = (TextView) rootView.findViewById(R.id.textDescTop);
        topDesc.setText(LabelMaker.getLabel(questionType, true));

        TextView bottomDesc = (TextView) rootView.findViewById(R.id.textDescBottom);
        bottomDesc.setText(LabelMaker.getLabel(questionType, false));

        ImageView topIcon = (ImageView) rootView.findViewById(R.id.imageViewHighest);
        topIcon.setImageResource(LabelMaker.getIconResourceId(questionType, true));

        ImageView bottomIcon = (ImageView) rootView.findViewById(R.id.imageViewLowest);
        bottomIcon.setImageResource(LabelMaker.getIconResourceId(questionType, false));
    }

    private void setImage(final int imageId) {
        LoadResourceTask loadResource = new LoadResourceTask();
        loadResource.execute(imageId);
        mImageLoadList.add(loadResource);
    }

    @Override
    public void onSwap(View firstView, int firstPosition, View secondView, int secondPosition) {
        RankActivity.mAudioEngine.playSound(AudioEngine.SOUND_SWAP);
    }

    @Override
    public int getResult() {
        checkAnswers(false);
        return 0;
    }

    @Override
    public void loadWorkPad() {

    }

    @Override
    public void saveWorkPad() {

    }

    private class LoadResourceTask extends AsyncTask<Integer, Integer, Picture> {
        private int mFlagId;

        protected Picture doInBackground(Integer... resourceId) {
            try {
                mFlagId = resourceId[0];
                SVG svg = SVG.getFromResource(getActivity(), mFlagId);
                return svg.renderToPicture();
            } catch (SVGParseException e) {
                Log.e(TAG, "Error loading resource " + String.valueOf(mFlagId) + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Picture picture) {
            if (picture != null) {
                for (CountryItem countryView : mCountryViews) {
                    if (countryView.mCountry.getFlagID() == mFlagId) {
                        countryView.mImageFlag.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        countryView.mImageFlag.setImageDrawable(new PictureDrawable(picture));
                        countryView.mImageFlag.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
    }
}

