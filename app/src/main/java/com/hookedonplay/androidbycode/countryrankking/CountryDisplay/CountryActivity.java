/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.CountryDisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.hookedonplay.androidbycode.countrydatabase.DbCountry;
import com.hookedonplay.androidbycode.countrydatabase.DbTableCountry;
import com.hookedonplay.androidbycode.countryrankking.R;
import com.hookedonplay.androidbycode.countryrankking.EffectBlur.BlurBehind;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author BRM20150315
 *         <p/>
 *         Activity to display details about the country
 */
public class CountryActivity extends ActionBarActivity {
    private static final String TAG = "CountryActivity";
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Country that is displayed on this dialog
     */
    private DbCountry mCountry;

    public CountryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        BlurBehind.getInstance()
                .withAlpha(80)
                .setBackground(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mRecyclerView = (RecyclerView) findViewById(R.id.countryMetricsList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final long dbId = getIntent().getLongExtra(getString(R.string.database_country), -1);
        new LoadDataTask(getApplicationContext(), dbId).execute();

        View layout = findViewById(R.id.layoutMain);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupFapIconMenu();
    }


    @Override
    protected void onPause() {
        // Use our own exit transition
        overridePendingTransition(0, R.anim.abc_fade_out);
        super.onPause();
    }

    private void fillData() {

        // Add the country name to the title of the toolbar
        getSupportActionBar().setTitle(mCountry.getName());

        final ImageView map = (ImageView) findViewById(R.id.imageMap);
        map.setImageResource(mCountry.getMapID());
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(map, mCountry.getMapID());
            }
        });

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        new LoadResourceTask(getApplicationContext()).execute(mCountry.getFlagID());

        List<CountryInfo> countryDataSet = new ArrayList<>();

        if (!mCountry.getCapital().isEmpty()) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_capital), mCountry.getCapital(), R.drawable.icon_capital_location, mCountry.getCapitalLat(), mCountry.getCapitalLon()));
        }

        if (mCountry.getPopulation() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_population), mCountry.getPopulationString(this) + " " + getString(R.string.country_dialog_people), R.drawable.icon_population_highest));
        }

        if (mCountry.getArea() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_area), mCountry.getAreaString(this), R.drawable.icon_area_highest));
        }

        if (mCountry.getGdp() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_gdp), mCountry.getGdpString(this), R.drawable.icon_gdp_highest));
        }

        if (mCountry.getGdpPerCapita() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_gdp_per_capita), mCountry.getGdpPerCapitaString(), R.drawable.icon_gdp_percapital_highest));
        }

        if (mCountry.getCoastLength() >= 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_coast_length), mCountry.getCoastLengthString(this), R.drawable.icon_coast_highest));
        }

        if (mCountry.getLifeExpectancy() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_life_expectancy), "" + mCountry.getLifeExpectancy(), R.drawable.icon_life_expectancy));
        }

        if (mCountry.getOlympicsSummer() >= 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_olympics_summer), "" + mCountry.getOlympicsSummer() + " " + getString(R.string.country_dialog_medals), R.drawable.icon_olympic_summer));
        }

        if (mCountry.getOlympicsWinter() >= 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_olympics_winter), "" + mCountry.getOlympicsWinter() + " " + getString(R.string.country_dialog_medals), R.drawable.icon_olympic_winter));
        }

        if (mCountry.getObesity() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_obesity), "" + mCountry.getObesity() + "%", R.drawable.icon_obesity));
        }

        if (mCountry.getMilitaryCount() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_military), "" + mCountry.getMilitaryCount() + " " + getString(R.string.country_dialog_people), R.drawable.icon_military));
        }

        if (mCountry.getInternetAccess() > 0) {
            countryDataSet.add(new CountryInfo(getString(R.string.country_dialog_internet_access), "" + mCountry.getInternetAccess() + " " + getString(R.string.country_dialog_percent_of_people), R.drawable.icon_internet_access));
        }
        mAdapter = new CountryAdapter(countryDataSet);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupFapIconMenu() {
        final ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_action_new_light);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .attachTo(actionButton)
                .build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                if (mCountry == null) {
                    return;
                }
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=9", mCountry.getCapitalLat(), mCountry.getCapitalLon());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
            }
        });

    }

    /**
     * Zoom image from thumbnail to full screen with animation.
     *
     * @param thumbView View to zoom
     * @param imageResId full sized image resource id
     *
     * Taken from android developer site: developer.android.com/training/animation/zoom.html
     */
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.imageViewFullScreen);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.layoutMain)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    /**
     * Load country information from the sqlite database
     */
    private class LoadDataTask extends AsyncTask<Integer, Integer, Integer> {
        private Context mContext;
        private long mId;

        public LoadDataTask(@NonNull Context context, final long id) {
            mContext = context;
            mId = id;
        }

        protected Integer doInBackground(Integer... resourceId) {
            DbTableCountry tableCountry = new DbTableCountry(mContext);
            tableCountry.open();
            mCountry = tableCountry.getCountry(mId);
            tableCountry.close();

            return 0;
        }

        protected void onPostExecute(Integer value) {
            if (mCountry == null) {
                Log.e(TAG, "Unable to lookup country information from database: id=" + mId);
                finish();
            }

            fillData();
        }
    }

    /**
     * Load the flag from the svg source image
     */
    private class LoadResourceTask extends AsyncTask<Integer, Integer, Picture> {
        private Context mContext;
        private int mFlagId;

        public LoadResourceTask(Context context) {
            mContext = context;
        }

        protected Picture doInBackground(Integer... resourceId) {
            try {
                mFlagId = resourceId[0];
                SVG svg = SVG.getFromResource(mContext, mFlagId);
                return svg.renderToPicture();
            } catch (SVGParseException e) {
                Log.e(TAG, "SVG Error loading resource " + String.valueOf(resourceId) + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Picture picture) {
            ImageView flag = (ImageView) findViewById(R.id.imageFlag);
            flag.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            flag.setImageDrawable(new PictureDrawable(picture));
        }
    }
}
