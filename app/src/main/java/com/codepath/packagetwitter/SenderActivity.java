package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SenderActivity extends AppCompatActivity {

    String receiver_handle;
    String sender_handle;
    EditText mEditText;
    boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        //get the sender from intent
         sender_handle = getIntent().getStringExtra("sender_handle");
        final Button btnSender = (Button) findViewById(R.id.btndone);
        btnSender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(exists) {
                    receiver_handle = mEditText.getText().toString();

                    onCreationSuccess();
                }
            }

        });

        //get reciever handle -- populated by an autocomplete from the database
        //change later
        mEditText = (EditText) findViewById(R.id.et_get_receiver_handle);
        exists = true;


        //in the future get approval from the receiver also and then only proceed
        //package the two and send them to the new activity to post a package

        //EDIT VIEW
        //create package
        //post package
    }

    public void onCreationSuccess() {

        // Intent i = new Intent(this, PhotosActivity.class);
        // startActivity(i);
        //this is just to show that we have reached this point
        //Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, PackageCreationActivity.class);
        i.putExtra("receiver_handle",receiver_handle);
        i.putExtra("sender_handle",sender_handle);
        startActivity(i);
    }
}
