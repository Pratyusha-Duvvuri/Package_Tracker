package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;

public class PackageCreationActivity extends AppCompatActivity {

String sender_handle;
    Receiver receiver;
    Sender sender;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);

        receiver = getIntent().getParcelableExtra("receiver");
        sender = getIntent().getParcelableExtra("sender");


    }

}
