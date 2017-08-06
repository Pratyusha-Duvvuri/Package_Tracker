package com.codepath.packagetwitter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Fragments.ChatPagerAdapter;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import static com.codepath.packagetwitter.Fragments.Tab1Chat_Fragment.thisUser1;
import static com.codepath.packagetwitter.Fragments.Tab2Chat_Fragment.thisUser2;
import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by pratyusha98 on 7/23/17.
 */

public class ChatActivity extends AppCompatActivity {

    ChatPagerAdapter pagerAdapter;
    public static String tabTitles_main[] = new String[] {"Person1", "Person2","Person3"};
    public static String messages_main[] = new String[] {"FIRST", "SECOND","ME"};
    public static String status_main[] = new String[] {"FIRST", "SECOND","ME"};

    ViewPager vpPager;
    public  String transaction_id;
    public static String sender_main;
    public static String receiver_main;
    public static String courier_main;
    public static ParselTransaction transaction_main;
    public static String type;
    public static ImageView ivProfileImage;
    public static ParseFile postImage;
    public static Uri imageUri;
    public static Context context ;
    public static Boolean goOn;
    public static TextView tvNameHeader ;
    public static TextView tvStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
        goOn=false;
        ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage2);
        context = this;
        tvNameHeader = (TextView)findViewById(R.id.tvNameHeader);
        tvStatus = (TextView)findViewById(R.id.tvStatus);

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
                        status_main[0] = "Receiver";
                        status_main[1] = "Courier";

                        tabTitles_main[0]="Receiver - "+ LoginActivity.dictionary_name.get(receiver_main).toString();
                        tabTitles_main[1]="Courier - "+LoginActivity.dictionary_name.get(courier_main).toString();
                        tabTitles_main[2]=sender_main;

                    }
                    else if(receiver_main.equals(parseUser.getString("username"))) {
                        messages_main[0] = sender_main;
                        messages_main[1] = courier_main;
                        status_main[0] = "Sender";
                        status_main[1] = "Courier";

                        tabTitles_main[0]="Sender - " + LoginActivity.dictionary_name.get(sender_main).toString();
                        tabTitles_main[1]="Courier - "+LoginActivity.dictionary_name.get(courier_main).toString();
                        tabTitles_main[2]=receiver_main;
                    }

                    else if(courier_main.equals(parseUser.getString("username"))) {

                        messages_main[0] = sender_main;
                        messages_main[1] = receiver_main;
                        status_main[0] = "Sender";
                        status_main[1] = "Receiver";

                        tabTitles_main[0]="Sender - "+LoginActivity.dictionary_name.get(sender_main).toString();
                        tabTitles_main[1]="Receiver - "+LoginActivity.dictionary_name.get(receiver_main).toString();
                        tabTitles_main[2]=courier_main;
                    }
                        setUpStuff();
                }
                else {
                    Toast.makeText(ChatActivity.this, "Can't access user",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });
       super.onCreate(savedInstanceState);

    }

    public void setUpStuff(){


        //this code is to set up the transactions for the three tabs
        pagerAdapter = new ChatPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager2);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                if(goOn)
                        loadImageBoi(position);
            }
        });
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs2);
        tabLayout.setupWithViewPager(vpPager);

    }

    public static void loadImageBoi(int pos){

        if(pos==0) {
            postImage = thisUser1.getParseFile("ImageFile");
            tvNameHeader.setText(thisUser1.getString("fullName"));
            tvStatus.setText(status_main[pos]);
        }
        else {
            postImage = thisUser2.getParseFile("ImageFile");
            tvNameHeader.setText(thisUser2.getString("fullName"));
            tvStatus.setText(status_main[pos]);
        }
        String imageUrl = postImage.getUrl();//live url
        imageUri = Uri.parse(imageUrl);
        loadImage();

    }

    public static void loadImage(){
        Glide.with(context)
                .load(imageUri.toString())
                .into(ivProfileImage);

    }

}
