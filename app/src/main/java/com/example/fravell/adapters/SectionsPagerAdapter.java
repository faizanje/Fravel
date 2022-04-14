package com.example.fravell.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fravell.Utils.Constants;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final String[] TAB_TITLES;
    private final Fragment[] FRAGMENTS;
    private final Context mContext;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior, String[] tab_titles, Fragment[] fragments, Context mContext) {
        super(fm, behavior);
        TAB_TITLES = tab_titles;
        FRAGMENTS = fragments;
        this.mContext = mContext;
        Log.d(Constants.TAG, "SectionsPagerAdapter: Titles" + TAB_TITLES.length);
        Log.d(Constants.TAG, "SectionsPagerAdapter: FRAGMENTS" + FRAGMENTS.length);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return FRAGMENTS[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return FRAGMENTS.length;
    }
}