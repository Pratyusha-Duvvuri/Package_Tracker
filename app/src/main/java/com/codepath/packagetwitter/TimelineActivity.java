package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toolbar;

public class TimelineActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar v;
    TransactionAdapter adapter;
    //OldTransactionsFragment oldFrag;
    //CurrentTransactionsFragment newFrag;
    ViewPager vpPager;
    LinearLayoutManager llayout;
    Integer tabPosition;


    @Override
    //TODO when does this get executed or called
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO when are the tabs actually created? like when is the first time the stuff is instantiated??
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }


}
