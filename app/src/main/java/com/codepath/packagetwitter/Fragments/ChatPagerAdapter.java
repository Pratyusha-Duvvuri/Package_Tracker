package com.codepath.packagetwitter.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.codepath.packagetwitter.R;

import static com.codepath.packagetwitter.ChatActivity.messages_main;
import static com.codepath.packagetwitter.ChatActivity.tabTitles_main;
import static com.codepath.packagetwitter.ChatActivity.type;

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
        {
            return tab1Chat_fragment;}
        else if(position ==1)
            {type = messages_main[0];
            return tab2Chat_fragment;}
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
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) v.findViewById(R.id.tabsText);
        tv.setText(tabTitles_main[position]);
        return v;
    }

}
