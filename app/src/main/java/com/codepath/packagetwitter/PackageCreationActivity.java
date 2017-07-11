package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PackageCreationActivity extends AppCompatActivity {

String sender_handle;
    String receiver_handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);
        sender_handle = getIntent().getStringExtra("sender_handle");
        receiver_handle = getIntent().getStringExtra("receiver_handle");

        //Different instances when creating a package

        //Here we need to create Sender object, Receiver object
        //Here we also need to create the package attributes
        //Make everything parcelable




    }

}
