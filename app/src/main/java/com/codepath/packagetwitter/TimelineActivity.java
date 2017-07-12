package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.packagetwitter.Models.Transaction;

public class TimelineActivity extends AppCompatActivity {


    ProgressBar v;
    TransactionAdapter adapter;
    OldTransactionsFragment oldFrag;
    CurrentTransactionsFragment newFrag;
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
        adapter = new TransactionAdapter(getSupportFragmentManager(),this);

        //get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(adapter);
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                //get an object
                //use this to set the page number to whatever using the setter
                // Check if this is the page you want.
                TweetsListFragment.setPage(position);
            }
        });

        // setup the tab layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //return true;


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                Intent i = new Intent(TimelineActivity.this, SearchActivity.class);

                i.putExtra("query",query);


                startActivityForResult(i, REQUEST_CODE);


                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }







}
