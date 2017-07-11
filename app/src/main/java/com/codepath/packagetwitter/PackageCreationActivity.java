package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;

public class PackageCreationActivity extends AppCompatActivity {

String sender_handle;
    String receiver_handle;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);
        sender_handle = getIntent().getStringExtra("sender_handle");
        receiver_handle = getIntent().getStringExtra("receiver_handle");


        Receiver receiver;// - Access to location, has received
        Sender sender; //-  Access to location, has received
        CourierModel courier;// - Access to most details
        int transactionState;//int that represents transaction state


    }

}
