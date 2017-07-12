package com.codepath.packagetwitter.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by michaunp on 7/11/17.
 */

public class TransactionsPagerAdapter extends FragmentPagerAdapter{
    private String tabTitles[] = new String[] {"Current", "Completed"};
    private Context context;
    public Fragment currentTransactionsFragment = new CurrentTransactionsFragment();
    public Fragment oldTransactionsFragment = new OldTransactionsFragment();

    public TransactionsPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return currentTransactionsFragment;
        }
        else if (position == 1) {
            return oldTransactionsFragment;
        }
        else {
            return null;
        }
    }
    //return total # of fragments
    @Override
    public int getCount() {
        return 2;
    }

    //return title based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
