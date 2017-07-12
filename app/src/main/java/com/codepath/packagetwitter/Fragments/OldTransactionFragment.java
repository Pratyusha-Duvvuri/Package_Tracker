package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class OldTransactionFragment extends TransactionList_Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();


    }
    //    //this is for the intermediate progress bar
    MenuItem miActionProgressItem;
    ProgressBar v;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }


    private void populateTimeline(){

    }
}
