package com.codepath.packagetwitter.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import static com.codepath.packagetwitter.OtherChatActivity.tabTitles_main;

/**
 * Created by pratyusha98 on 7/23/17.
 */

public class ChatPagerAdapter extends FragmentPagerAdapter {
    //private String tabTitles[] = new String[] {"Person1","Person2"};
    private Context context;
    public Tab1Chat_Fragment tab1Chat_fragment;
    public Tab2Chat_Fragment tab2Chat_fragment;


    public ChatPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
        Log.d("Chat Pager Adap", "Construct");
        tab1Chat_fragment = new Tab1Chat_Fragment();
        tab2Chat_fragment = new Tab2Chat_Fragment();
    }

    @Override
    public Fragment getItem(int position) {


        if(position==0)
            return tab1Chat_fragment;
        else if(position ==1)
            return tab2Chat_fragment;
        else return  null;
    }

    //return total # of fragments
    @Override
    public int getCount() {
        return 2;
    }


    //return title based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        //get the username of sender
        //if(ProfileActivity.parseUser.getUsername().equals(OtherChatActivity.transaction_id))

        return tabTitles_main[position];

    }

}
