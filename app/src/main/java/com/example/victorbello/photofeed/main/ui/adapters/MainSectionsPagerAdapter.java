package com.example.victorbello.photofeed.main.ui.adapters;

/**
 * Created by victorbello on 16/09/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class MainSectionsPagerAdapter extends FragmentPagerAdapter{

    private String[]titles;
    private Fragment[] fragments;

    public MainSectionsPagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments){
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position){
        return this.titles[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
