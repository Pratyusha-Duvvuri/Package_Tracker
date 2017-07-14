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
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Sender;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton floatingActionButton1;
        com.github.clans.fab.FloatingActionButton floatingActionButton2;
        //user = User.getRandomUser(this);
        user = Parcels.unwrap(getIntent().getParcelableExtra("USER"));
        user.hasPendingRequests=true;

        tvUsername =  (TextView) findViewById(R.id.tvName);

        tvUsername.setText(user.getUserName());


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

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent( ProfileActivity.this,PackageCreationActivity.class);
                i.putExtra("sender", Parcels.wrap(user) );
                i.putExtra("mainUser", Parcels.wrap(user) );

                startActivity(i);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                i.putExtra("courier",Parcels.wrap(user) );

                startActivity(i);
            }
        });
        if(user.hasPendingRequests){actOnRequests();}

    }

    public void actOnRequests(){
        FragmentManager fm = getSupportFragmentManager();
        //creating random sender and mail object here and checking flow from this
        // point till last activity before transaction activity creation.

        sender = Sender.getRandomSender(this);
        mail = Mail.getRandomMail(this);

        PendingRequest_Fragment pendingRequest_fragment =
                        PendingRequest_Fragment.newInstance(mail,sender);
        pendingRequest_fragment.show(fm,"fragment_pending_request");

    }


    @Override
    public void onFinishEditDialog(Sender senderr, Mail maill, Boolean proceed) {
        if(proceed){
        Intent i = new Intent(this, AfterSenderConfirmation.class);
        i.putExtra("receiver", Parcels.wrap(user));
        i.putExtra("sender", Parcels.wrap(senderr));
        i.putExtra("mail", Parcels.wrap(maill));
        startActivity(i);}
    }
}
