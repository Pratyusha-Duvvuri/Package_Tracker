package com.codepath.packagetwitter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Fragments.LogoutFragment;
import com.codepath.packagetwitter.Fragments.PendingRequest_Fragment;
import com.codepath.packagetwitter.Fragments.RejectedRequestFragment;
import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.User;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

import static com.codepath.packagetwitter.Fragments.Login_Fragment.throughFacebook;

/**
 * Created by michaunp on 7/13/17.
 */

public class ProfileActivity extends AppCompatActivity implements PendingRequest_Fragment.SendResultListener , LogoutFragment.SendDialogListener, RejectedRequestFragment.SendResultListener {

    private static final int PACKAGE_CREATION =76 ;
    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;
    User user;
    public TextView tvUsername;
    public TextView tvTagline;
    public ImageView ivProfileImage;
    public TextView tvHandle;
    public static ParseUser parseUser;
    public final int COURRIER_REQUEST_CODE = 20;
    public final int RECEIVER_CODE = 30;
    public final static int TRANSACTION_DETAIL_CODE = 50;
    public static ParselTransaction currentRejected;
    public static ParselTransaction currentReceive;
    private static com.facebook.login.widget.LoginButton loginButtonProfile;
    public TextView tvTransaction;


    public final int IMAGE_REQUEST_CODE =40;

    public FloatingActionButton floatingActionButton1;
    public FloatingActionButton floatingActionButton2;
    public com.github.clans.fab.FloatingActionButton floatingActionButton3;
    public com.github.clans.fab.FloatingActionButton floatingActionButton4;
    public Boolean ignore;
    public Boolean reload;
    private final int DELAY = 5000;

    FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ignore = true;
        setContentView(R.layout.activity_profile);
        ivProfileImage = (ImageButton) findViewById(R.id.ivProfileImage);
        ivProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton floatingActionButton1;
        com.github.clans.fab.FloatingActionButton floatingActionButton2;
        //user = User.getRandomUser(this);
        tvUsername = (TextView) findViewById(R.id.tvName);
        tvHandle = (TextView) findViewById(R.id.tvTagline);
        tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvTransaction = (TextView) findViewById(R.id.tvTransaction);



        fm = getSupportFragmentManager();
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


         //Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        //setupDivider(tabLayout);
        //if (((PendingTransactionFragment)pagerAdapter.getItem(vpPager.getCurrentItem())).getTransactions().size() == 0)
        //Floating action button code
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_sender);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_courier);


        //For Courier
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!ignore) {
                    Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                    i.putExtra("courier", Parcels.wrap(user));
                    i.putExtra("USER", Parcels.wrap(user));

                    startActivityForResult(i, COURRIER_REQUEST_CODE);
                    materialDesignFAM.close(true);

                }
            }
        });
//        For image profie view
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, PackageCreationPart1Activity.class);


                startActivityForResult(i,PACKAGE_CREATION);
                materialDesignFAM.close(true);

            }
        });
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, FileUploadActivity.class);


                startActivityForResult(i,IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onFinishEditDialogThis() {

    }

    public  void setupDivider(TabLayout tabLayout){

        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.LTGRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
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


                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                        builder.setMessage("Your package was rejected");    //set message


                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //when click on DELETE
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;}
                        }).show();
                        try {
                            itemList.get(i).delete();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        itemList.get(i).saveInBackground();


                    }

                } else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
            }
        });


    }



    public void setParametersOfView() {
        tvTagline.setText("Current Location: "+parseUser.getString("location"));
        tvUsername.setText(parseUser.getString("fullName"));
        ParseFile postImage = parseUser.getParseFile("ImageFile");
        if(postImage!=null) {
            String imageUrl = postImage.getUrl()
        ;//live url

        Uri imageUri = Uri.parse(imageUrl);
        Glide.with(ProfileActivity.this).load(imageUri.toString()).into(ivProfileImage);}
        else {
            Glide.with(ProfileActivity.this).load("http://i.imgur.com/zuG2bGQ.jpg").into(ivProfileImage);

        }
    }




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

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                        builder.setMessage("Someone wants to send you a package");    //set message


                        builder.setPositiveButton("View now", new DialogInterface.OnClickListener() { //when click on DELETE
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ProfileActivity.this, AfterSenderConfirmation.class);
                                startActivityForResult(i, RECEIVER_CODE);
                                return;
                            }
                                }).show();
                        break;
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
            startActivityForResult(i, RECEIVER_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {



        if (requestCode == PACKAGE_CREATION && resultCode == RESULT_OK){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

            builder.setMessage("Package was created.\n A request was sent to the recipient");    //set message


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //when click on DELETE
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;}
            }).show();
        }

        if (requestCode == COURRIER_REQUEST_CODE && resultCode == RESULT_OK){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            if (data.getBooleanExtra("matched", false)== true){
                builder.setMessage("Trip was entered.\nA match was found!");    //set message
                builder.setNegativeButton("View Now", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfileActivity.this, TransactionDetailActivity.class);
                        String transactionId = data.getStringExtra("transaction");
                        intent.putExtra("ParselTransactionId", transactionId);

                        startActivityForResult(intent, TRANSACTION_DETAIL_CODE);
                        return;}
                });
            }
            else{
                builder.setMessage("Trip was entered.");    //set message

            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //when click on DELETE
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;}
            }).show();

        }


        if (requestCode == RECEIVER_CODE && resultCode == RESULT_OK){

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            if (data.getBooleanExtra("accepted", true) == false){ // if rejected
                builder.setMessage("Package rejected");    //set message

            }
            else if (data.getBooleanExtra("matched", false)){
                builder.setMessage("Package was confirmed.\nA match was found!");    //set message
                builder.setNegativeButton("View Now", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfileActivity.this, TransactionDetailActivity.class);
                        String transactionId = data.getStringExtra("transaction");
                        intent.putExtra("ParselTransactionId", transactionId);

                        startActivityForResult(intent, TRANSACTION_DETAIL_CODE);
                        return;}
                });
            }
            else{
                builder.setMessage("Package was confirmed.");    //set message

            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //when click on DELETE
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    actOnRequests();
                    return;}
            }).show();
        }

        if (requestCode == IMAGE_REQUEST_CODE){
            setParametersOfView();


        }

        pagerAdapter.pendingTransactionFragment.populateTimeline();
        pagerAdapter.oldTransactionFragment.populateTimeline();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.timeline_menu, menu);

        return true;
    }


    public void onLogoutAction(MenuItem mi) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //alert for confirm to delete
        builder.setMessage("Are you sure you want to logout?");    //set message

        builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() { //when click on DELETE
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    if(throughFacebook)
                    {LoginManager.getInstance().logOut();}
                }
                catch(NullPointerException e){}
                ParseUser.logOut();
                Intent i= new Intent(ProfileActivity.this, LoginActivity.class);
                startActivityForResult(i,0);

                return;
            }

        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
            @Override
            public void onClick(DialogInterface dialog, int which) {
                                return;
            }
        }).show();
    }


}
