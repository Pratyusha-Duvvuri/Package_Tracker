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
import com.codepath.packagetwitter.Fragments.RejectedRequestFragment;
import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.User;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by michaunp on 7/13/17.
 */

public class ProfileActivity extends AppCompatActivity implements PendingRequest_Fragment.SendResultListener , LogoutFragment.SendDialogListener, RejectedRequestFragment.SendResultListener {

    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;
    User user;
    public TextView tvUsername;
    public TextView tvTagline;
    public ImageView ivProfileImage;
    public TextView tvHandle;
    public static ParseUser parseUser;
    public final int COURRIER_REQUEST_CODE = 20;
    public final int SENDER_REQUEST_CODE = 30;
    public static ParselTransaction currentRejected;
    public static ParselTransaction currentReceive;

    public final int IMAGE_REQUEST_CODE =40;

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
        tvHandle = (TextView) findViewById(R.id.tvTagline);
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
                    actOnRequests();
                    checkForRejection();


//                    if (parseUser.getBoolean("hasPendingRequests") ){
//                        //the following check if the person has any other pending requests
//                        ParseQuery<ParselTransaction> q = ParseQuery.getQuery(ParselTransaction.class);
//                        q.whereEqualTo("receiver", parseUser.getUsername());
//                        q.whereEqualTo("transactionState", 0);
//                        // Execute the find asynchronously
//                        q.findInBackground(new FindCallback<ParselTransaction>() {
//                            @Override
//                            public void done(List<ParselTransaction> issueList, ParseException e) {
//                                if (e == null) {
//                                    if (issueList.size()<=1){//if the current equest is the only pending request
//
//                                        parseUser.put("hasPendingRequests", false);
//                                        parseUser.saveEventually();
//                                    }
//
//                                } else {
//                                    Log.d("score", "Error: " + e.getMessage());
//                                }
//                            }
//                        });
//                        parseUser.put("hasPendingRequests", false);
//                        parseUser.saveInBackground();
//                        actOnRequests();
//
//                    }



                    tvUsername.setText(parseUser.getString("fullName"));
                    tvHandle.setText(parseUser.getUsername());


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
        floatingActionButton4 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_demo);

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
//        For image profie view
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ReviewActivity.class);

                startActivity(i);
            }
        });
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, FileUploadActivity.class);


                startActivityForResult(i,IMAGE_REQUEST_CODE);
            }
        });
    }

    public void checkForRejection(){
        //if the sender has been rejected
        //query all requests with this user as sender and rejected id
        //if the query is not null loop over each of these requests and handle them

        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 9); //pending transaction state
        query.whereEqualTo("sender", parseUser.getString("username")); //pending transaction state
        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        //for every parsel transaction
                        currentRejected = itemList.get(i);


                        FragmentManager fm = getSupportFragmentManager();
                        //creating random sender and mail object here and checking flow from this
                        // point till last activity before transaction activity creation.

                        RejectedRequestFragment rejectedRequestFragment = new RejectedRequestFragment();
                        rejectedRequestFragment.show(fm, "fragment_rejected_request");

                    }
                    //deletes all these transactions once sender has been notified
                    for (int i = 0; i < itemList.size(); i++) {
                        //for every parsel transaction
                        itemList.get(i).deleteInBackground();

                    }


                } else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
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
//    public void actOnRequests() {
//        FragmentManager fm = getSupportFragmentManager();
//        //creating random sender and mail object here and checking flow from this
//        // point till last activity before transaction activity creation.
//
//        PendingRequest_Fragment pendingRequest_fragment = new  PendingRequest_Fragment();
//        pendingRequest_fragment.show(fm, "fragment_pending_request");
//    }


    public void actOnRequests() {


        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 0); //pending transaction state
        query.whereEqualTo("receiver", parseUser.getString("username")); //pending transaction state
        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        //for every parsel transaction
                        currentReceive= itemList.get(i);

                        FragmentManager fm = getSupportFragmentManager();
                        //creating random sender and mail object here and checking flow from this
                        // point till last activity before transaction activity creation.
                        PendingRequest_Fragment pendingRequest_fragment = new  PendingRequest_Fragment();
                        pendingRequest_fragment.show(fm, "fragment_pending_request");

                    }


                } else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
            }
        });


    }




    @Override
    public void onFinishEditDialog( Boolean proceed) {
        if(proceed){
        Intent i = new Intent(this, AfterSenderConfirmation.class);
            startActivity(i);
        }
        else{

        }
    }

    public void onFinishEditDialog(){
        ParseUser.logOut();
        Intent i= new Intent(this, LoginActivity.class);
        startActivityForResult(i,0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        pagerAdapter.pendingTransactionFragment.populateTimeline();
        pagerAdapter.currentTransactionFragment.populateTimeline();
        pagerAdapter.oldTransactionFragment.populateTimeline();

//        if (requestCode == IMAGE_REQUEST_CODE){
//            setParametersOfView();
//
//            CourierModel courier = (CourierModel) Parcels.unwrap(data.getParcelableExtra(COURIER_KEY));
//            //add the new (incomplete) transaction to current transactions
//            Transaction transaction = new Transaction(new Receiver(), new Sender(), courier, new Mail());
//            //pagerAdapter.pendingTransactionFragment.addItems(transaction);
//        }

        if (requestCode == IMAGE_REQUEST_CODE) {
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

    @Override
    public void onFinishEditDialogThis() {

    }
}
