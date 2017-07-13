package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;

public class TimelineActivity extends AppCompatActivity {

    Toolbar toolbar;
    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO when are the tabs actually created? like when is the first time the stuff is instantiated??
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //find toolbar inside activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //sets toolbar to act as the actionbar
        setSupportActionBar(toolbar);
        //sets up toolbar title
        getSupportActionBar().setTitle(null);
        pagerAdapter = new TransactionsPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        //code to reduce ViewPager Lag
        vpPager.setOffscreenPageLimit(2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.miAdd:
                //Launch new Transaction activity
                Intent i = new Intent(this, NewtransactionActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
