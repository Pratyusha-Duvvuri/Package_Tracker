package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Fragments.ChatPagerAdapter;

/**
 * Created by pratyusha98 on 7/23/17.
 */

public class OtherChatActivity extends AppCompatActivity {

    ChatPagerAdapter pagerAdapter;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        //this code is to set up the transactions for the three tabs
        pagerAdapter = new ChatPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager2);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs2);
        tabLayout.setupWithViewPager(vpPager);

    }

}
