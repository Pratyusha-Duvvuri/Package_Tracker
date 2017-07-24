package com.codepath.packagetwitter.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by michaunp on 7/11/17.
 */

public class TransactionsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Pending","Current", "Completed"};
    private Context context;
    public PendingTransactionFragment pendingTransactionFragment;
    public CurrentTransactionFragment currentTransactionFragment;
    public OldTransactionFragment oldTransactionFragment;

    public TransactionsPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
        Log.d("Transaction Pager Adap", "Construct");
        pendingTransactionFragment = new PendingTransactionFragment();
        currentTransactionFragment = new CurrentTransactionFragment();
        oldTransactionFragment = new OldTransactionFragment();
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return pendingTransactionFragment;
        }
        else if (position == 1) {

            return currentTransactionFragment;
        }
        else if (position == 2) {
             return oldTransactionFragment;
        }
        else {
            return null;
        }
    }

    //return total # of fragments
    @Override
    public int getCount() {
        return 3;
    }

    //return title based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
