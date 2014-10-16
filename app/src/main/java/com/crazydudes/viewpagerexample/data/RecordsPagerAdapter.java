package com.crazydudes.viewpagerexample.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Very very simple PagerAdapter.
 * I used FragmentStatePagerAdapter to make use of the page title
 * that is being used in the  tab actionbar.
 */
public class RecordsPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> fragments;

    public RecordsPagerAdapter(FragmentManager manager, List<Fragment> fragmentList) {
        super(manager);
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       //if you're using Android Studio this next line probably has an error
        //ignore it as its an IDE bug!
        return fragments.get(position).getClass().getSimpleName();
    }
}
