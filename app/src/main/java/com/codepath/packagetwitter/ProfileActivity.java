package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;

/**
 * Created by michaunp on 7/13/17.
 */

public class ProfileActivity extends AppCompatActivity {

    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pagerAdapter = new TransactionsPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }
}
