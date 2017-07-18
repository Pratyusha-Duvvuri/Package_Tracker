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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class ProfileActivity extends AppCompatActivity implements PendingRequest_Fragment.SendResultListener {

    TransactionsPagerAdapter pagerAdapter;
    ViewPager vpPager;
    User user;
    Mail mail;
    Sender sender;
    public TextView tvUsername;
    public ImageView ivProfileImage;
    public static ParseUser parseUser;
    public String meh;
    public final int COURRIER_REQUEST_CODE = 20;
    public final int SENDER_REQUEST_CODE = 30;
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
        user = Parcels.unwrap(getIntent().getParcelableExtra("USER"));

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
                            Toast.makeText(ProfileActivity.this,"HALP"+parseUser.getString("email"),
                                    Toast.LENGTH_SHORT).show();

                            ignore = false;
                            reload = true;
                            setParametersOfView();
                        }
                        else{
                            Toast.makeText(ProfileActivity.this,"NOPE",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("ParseApplicationError",e.toString());
                        }
                    }
            });


        tvUsername =  (TextView) findViewById(R.id.tvName);
        tvUsername.setText(user.getUserName());

        user = Parcels.unwrap(getIntent().getParcelableExtra("USER"));

        pagerAdapter = new TransactionsPagerAdapter(getSupportFragmentManager(), this);
        //get the View pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);
        //setup tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_sender);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_courier);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_image);
        floatingActionButton4 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_receive_image);


        tvUsername = (TextView) findViewById(R.id.tvName);
        tvUsername.setText(user.getUserName());

        if (user.hasPendingRequests) {
            user.hasPendingRequests = false;
            actOnRequests();
        }
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!ignore){


                    Intent i = new Intent(ProfileActivity.this, PackageCreationActivity.class);
                i.putExtra("sender", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));
//                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );

                startActivityForResult(i, SENDER_REQUEST_CODE);}

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!ignore){
                Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                i.putExtra("courier", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));

                startActivityForResult(i, COURRIER_REQUEST_CODE);}
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, FileUploadActivity.class);
//
//                    i.putExtra("courier", Parcels.wrap(user));
//                    i.putExtra("USER", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));

                startActivity(i);
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, TestDownloadActivity.class);
//
//                    i.putExtra("courier", Parcels.wrap(user));
//                    i.putExtra("USER", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));

                startActivity(i);
            }
        });

    }


    public void setParametersOfView(){
        ParseFile postImage = ProfileActivity.parseUser.getParseFile("ImageFile");
        if(postImage!=null) {
            String imageUrl = postImage.getUrl();//live url
            Uri imageUri = Uri.parse(imageUrl);

            Glide.with(ProfileActivity.this).load(imageUri.toString()).into(ivProfileImage);
        }
        else {
            Glide.with(ProfileActivity.this).load("http://www.clipartpanda.com/clipart_images/happy-face-clip-art-1573801").into(ivProfileImage);
        }
            tvUsername.setText(parseUser.getString("fullName"));

    }




    public void actOnRequests() {
        FragmentManager fm = getSupportFragmentManager();
        //creating random sender and mail object here and checking flow from this
        // point till last activity before transaction activity creation.

        sender = Sender.getRandomSender(this);
        mail = Mail.getRandomMail(this);

        PendingRequest_Fragment pendingRequest_fragment =
                PendingRequest_Fragment.newInstance(mail, sender);
        pendingRequest_fragment.show(fm, "fragment_pending_request");

    }

    @Override
    public void onFinishEditDialog(Sender senderr, Mail maill, Boolean proceed) {
        if(proceed){
        Intent i = new Intent(this, AfterSenderConfirmation.class);
        i.putExtra("receiver", Parcels.wrap(user));
        i.putExtra("sender", Parcels.wrap(senderr));
        i.putExtra("mail", Parcels.wrap(maill));i.putExtra("USER", Parcels.wrap(user) );
            i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );
            startActivity(i);}

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COURRIER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            CourierModel courier = (CourierModel) Parcels.unwrap(data.getParcelableExtra(COURIER_KEY));
            //add the new (incomplete) transaction to current transactions
            Transaction transaction = new Transaction(new Receiver(), new Sender(), courier, new Mail());
            pagerAdapter.pendingTransactionFragment.addItems(transaction);
        }

        if (requestCode == SENDER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            Sender sender =  Parcels.unwrap(data.getParcelableExtra(SENDER_KEY));
            Mail mail =  Parcels.unwrap(data.getParcelableExtra(MAIL_KEY));
            Receiver receiver =  Parcels.unwrap(data.getParcelableExtra(RECEIVER_KEY));

            //add the new (incomplete) transaction to current transactions
            Transaction transaction = new Transaction(receiver, sender, new CourierModel(), mail);
            pagerAdapter.pendingTransactionFragment.addItems(transaction);
        }
    }

}
