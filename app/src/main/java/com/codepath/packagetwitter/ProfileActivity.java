package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.codepath.packagetwitter.Fragments.PendingRequest_Fragment;
import com.codepath.packagetwitter.Fragments.TransactionsPagerAdapter;
import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;
import com.github.clans.fab.FloatingActionMenu;

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
    public final int COURRIER_REQUEST_CODE = 20;
    public final int SENDER_REQUEST_CODE = 30;
    public final static String COURIER_KEY = "courier";
    public final static String SENDER_KEY = "sender";
    public final static String MAIL_KEY = "mail";
    public final static String RECEIVER_KEY = "receiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton floatingActionButton1;
        com.github.clans.fab.FloatingActionButton floatingActionButton2;
        //user = User.getRandomUser(this);

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


        tvUsername = (TextView) findViewById(R.id.tvName);
        tvUsername.setText(user.getUserName());


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, PackageCreationActivity.class);
                i.putExtra("sender", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));

                startActivityForResult(i, SENDER_REQUEST_CODE);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                i.putExtra("courier", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));

                startActivityForResult(i, COURRIER_REQUEST_CODE);
            }
        });
        if (user.hasPendingRequests) {
            user.hasPendingRequests = false;
            actOnRequests();
        }

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
        if (proceed) {
            Intent i = new Intent(this, AfterSenderConfirmation.class);
            i.putExtra("receiver", Parcels.wrap(user));
            i.putExtra("sender", Parcels.wrap(senderr));
            i.putExtra("mail", Parcels.wrap(maill));
            i.putExtra("USER", Parcels.wrap(user));

            startActivity(i);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COURRIER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            CourierModel courier = (CourierModel) Parcels.unwrap(data.getParcelableExtra(COURIER_KEY));
            //add the new (incomplete) transaction to current transactions
            Transaction transaction = new Transaction(new Receiver(), new Sender(), courier, new Mail());
            pagerAdapter.currentTransactionFragment.addItems(transaction);
        }

        if (requestCode == SENDER_REQUEST_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {

            Sender sender =  Parcels.unwrap(data.getParcelableExtra(SENDER_KEY));
            Mail mail =  Parcels.unwrap(data.getParcelableExtra(MAIL_KEY));
            Receiver receiver =  Parcels.unwrap(data.getParcelableExtra(RECEIVER_KEY));

            //add the new (incomplete) transaction to current transactions
            Transaction transaction = new Transaction(receiver, sender, new CourierModel(), mail);
            pagerAdapter.currentTransactionFragment.addItems(transaction);
        }
    }



}
