package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class SenderActivity extends AppCompatActivity {

    String receiver_handle;
    String sender_handle;
    EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        //get the sender from intent
         sender_handle = getIntent().getStringExtra("username");

        //get reciever handle -- populated by an autocomplete from the database
        //change later
        mEditText = (EditText) findViewById(R.id.et_get_receiver_handle);

        //in the future get approval from the receiver also and then only proceed
        //package the two and send them to the new activity to post a package




        //EDIT VIEW
        //create package
        //post package
    }
}
