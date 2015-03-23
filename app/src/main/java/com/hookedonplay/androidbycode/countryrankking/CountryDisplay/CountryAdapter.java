/*
 * Copyright (C) 2015 Hooked On Play
 *
 */
package com.hookedonplay.androidbycode.countryrankking.CountryDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hookedonplay.androidbycode.countryrankking.R;

import java.util.List;

/**
 * @author BRM20150315
 *
 * Adapter for the RecyclerView to list country attributes such as population, gdp...
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = "CountryAdapter";

    private List<CountryInfo> mCountryDataset;

    public CountryAdapter(List<CountryInfo> countryDataset) {
        mCountryDataset = countryDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_country_metric, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CountryInfo country = mCountryDataset.get(position);
        holder.mTextDescription.setText(country.mDescription);
        holder.mTextValue.setText(country.mValue);
        holder.mImageMetric.setImageResource(country.mImageId);
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCountryDataset.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextDescription;
        public TextView mTextValue;
        public ImageView mImageMetric;

        public ViewHolder(View view) {
            super(view);
            mTextDescription = (TextView) view.findViewById(R.id.textDescription);
            mTextValue = (TextView) view.findViewById(R.id.textValue);
            mImageMetric = (ImageView) view.findViewById(R.id.imageMetric);
        }
    }
}