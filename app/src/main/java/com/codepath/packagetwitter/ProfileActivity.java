package com.codepath.packagetwitter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Fragments.LogoutFragment;
import com.codepath.packagetwitter.Fragments.PendingRequest_Fragment;
import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;
import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

/**
 * Created by michaunp on 7/13/17.
 */

public class ProfileActivity extends AppCompatActivity implements PendingRequest_Fragment.SendResultListener , LogoutFragment.SendDialogListener {

    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;
    User user;
    public TextView tvUsername;
    public TextView tvTagline;
    public ImageView ivProfileImage;
    public static ParseUser parseUser;
    public final int COURRIER_REQUEST_CODE = 20;
    public final int SENDER_REQUEST_CODE = 30;
    public final int IMAGE_REQUEST_CODE = 40;
    public final static String COURIER_KEY = "courier";
    public final static String SENDER_KEY = "sender";
    public final static String MAIL_KEY = "mail";
    public final static String RECEIVER_KEY = "receiver";
    public FloatingActionButton floatingActionButton1;
    public FloatingActionButton floatingActionButton2;
    public com.github.clans.fab.FloatingActionButton floatingActionButton3;
    public com.github.clans.fab.FloatingActionButton floatingActionButton4;
    public Boolean ignore;
    public Boolean reload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ignore = true;
        setContentView(R.layout.activity_profile);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton floatingActionButton1;
        com.github.clans.fab.FloatingActionButton floatingActionButton2;
        //user = User.getRandomUser(this);
        tvUsername = (TextView) findViewById(R.id.tvName);
        tvTagline = (TextView) findViewById(R.id.tvTagline);

        String parseUserId = getIntent().getStringExtra("PARSEUSER");
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(parseUserId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser item, ParseException e) {
                parseUser = item;
                if (e == null) {
                    ignore = false;
                    reload = true;
                    setParametersOfView();

                    if (parseUser.getBoolean("hasPendingRequests") ){
                        parseUser.put("hasPendingRequests", false);
                        parseUser.saveInBackground();
                        actOnRequests();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Can't access user",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });


        //this code is to set up the transactions for the three tabs
        pagerAdapter = new TransactionsPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);


        //Floating action button code
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_sender);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_courier);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_image);

        //modal code (work on later)

        //FOR SENDER
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ignore) {
                    Intent i = new Intent(ProfileActivity.this, PackageCreationActivity.class);
                    startActivityForResult(i, SENDER_REQUEST_CODE);
                }

            }
        });
        //For Courier
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ignore) {
                    Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                    i.putExtra("courier", Parcels.wrap(user));
                    i.putExtra("USER", Parcels.wrap(user));

                    startActivityForResult(i, COURRIER_REQUEST_CODE);
                }
            }
        });
        //For image profie view
//        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(ProfileActivity.this, FileUploadActivity.class);
//
//                startActivityForResult(i,IMAGE_REQUEST_CODE);
//            }
//        });
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, FileUploadActivity.class);

                startActivityForResult(i,IMAGE_REQUEST_CODE);
            }
        });
    }


    public void setParametersOfView() {
        tvTagline.setText(parseUser.getString("tagline"));
        tvUsername.setText(parseUser.getString("username"));
        ParseFile postImage = ProfileActivity.parseUser.getParseFile("ImageFile");
        if(postImage!=null) {
            String imageUrl = postImage.getUrl()
        ;//live url

        Uri imageUri = Uri.parse(imageUrl);
        Glide.with(ProfileActivity.this).load(imageUri.toString()).into(ivProfileImage);}
        else {
            Glide.with(ProfileActivity.this).load("http://i.imgur.com/zuG2bGQ.jpg").into(ivProfileImage);

        }
    }


//if the person has any pending requests
    public void actOnRequests() {
        FragmentManager fm = getSupportFragmentManager();
        //creating random sender and mail object here and checking flow from this
        // point till last activity before transaction activity creation.

        PendingRequest_Fragment pendingRequest_fragment = new  PendingRequest_Fragment();
        pendingRequest_fragment.show(fm, "fragment_pending_request");
    }

    @Override
    public void onFinishEditDialog(Sender senderr, Mail maill, Boolean proceed) {
        if(proceed){
        Intent i = new Intent(this, AfterSenderConfirmation.class);
            startActivity(i);
        }
    }

    public void onFinishEditDialog(){
        ParseUser.logOut();
        Intent i= new Intent(this, LoginActivity.class);
        startActivityForResult(i,0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COURRIER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            CourierModel courier = (CourierModel) Parcels.unwrap(data.getParcelableExtra(COURIER_KEY));
            //add the new (incomplete) transaction to current transactions
            Transaction transaction = new Transaction(new Receiver(), new Sender(), courier, new Mail());
            //pagerAdapter.pendingTransactionFragment.addItems(transaction);
        }

        if (requestCode == SENDER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            //add the new (incomplete) transaction to current transactions
            //pagerAdapter.pendingTransactionFragment.addItems(transaction);
            pagerAdapter.pendingTransactionFragment.populateTimeline();
        }
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            setParametersOfView();
            }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    public void onLogoutAction(MenuItem mi) {
        FragmentManager fm = getSupportFragmentManager();
        LogoutFragment logoutFragment = LogoutFragment.newInstance();
        logoutFragment.show(fm, "fragment_logout");
    }

}
