package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codepath.packagetwitter.Fragments.ChatPagerAdapter;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by pratyusha98 on 7/23/17.
 */

public class OtherChatActivity extends AppCompatActivity {

    ChatPagerAdapter pagerAdapter;
    public static String tabTitles_main[] = new String[] {"Person1", "Person2","Person3"};
    public static String messages_main[] = new String[] {"FIRST", "SECOND","ME"};

    ViewPager vpPager;
    public  String transaction_id;
    public static String sender_main;
    public static String receiver_main;
    public static String courier_main;
    public static ParselTransaction transaction_main;
    public static String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transaction_id =  getIntent().getStringExtra("ParselTransactionId");
        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        actionBar.hide();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(transaction_id, new GetCallback<ParselTransaction>() {
            @Override
            public void done(ParselTransaction item, ParseException e) {
                 transaction_main= item;
                if (e == null) {

                    Log.d("why is this","happening");
                    sender_main = item.getString("sender");
                    receiver_main = item.getString("receiver");
                    courier_main = item.getString("courier");

                    if(sender_main.equals(parseUser.getString("username"))){
                        messages_main[0] = receiver_main;
                        messages_main[1] = courier_main;

                        tabTitles_main[0]="Receiver - "+ LoginActivity.dictionary_name.get(receiver_main).toString();
                        tabTitles_main[1]="Courier - "+LoginActivity.dictionary_name.get(courier_main).toString();
                        tabTitles_main[2]=sender_main;

                    }
                    else if(receiver_main.equals(parseUser.getString("username"))) {
                        messages_main[0] = sender_main;
                        messages_main[1] = courier_main;

                        tabTitles_main[0]="Sender - " + LoginActivity.dictionary_name.get(sender_main).toString();
                        tabTitles_main[1]="Courier - "+LoginActivity.dictionary_name.get(courier_main).toString();
                        tabTitles_main[2]=receiver_main;
                    }

                    else if(courier_main.equals(parseUser.getString("username"))) {

                        messages_main[0] = sender_main;
                        messages_main[1] = receiver_main;

                        tabTitles_main[0]="Sender - "+LoginActivity.dictionary_name.get(sender_main).toString();
                        tabTitles_main[1]="Receiver - "+LoginActivity.dictionary_name.get(receiver_main).toString();
                        tabTitles_main[2]=courier_main;
                    }
                        setUpStuff();
                }
                else {
                    Toast.makeText(OtherChatActivity.this, "Can't access user",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });


        super.onCreate(savedInstanceState);


    }


    public void setUpStuff(){

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
