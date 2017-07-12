package com.codepath.packagetwitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;

import org.parceler.Parcels;

public class SenderActivity extends AppCompatActivity {

    Receiver receiver;
    Sender sender;
    EditText mEditText;
    boolean exists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        //get the sender from intent
         sender =  getIntent().getParcelableExtra("sender");
        final Context context = this;

        final Button btnSender = (Button) findViewById(R.id.btndone);
        btnSender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(exists) {
                    receiver = Receiver.getRandomReceiver(context);

                    onCreationSuccess();
                }
            }

        });

        //get receiver handle -- populated by an autocomplete from the database
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
        i.putExtra("receiver", Parcels.wrap(receiver));
        i.putExtra("sender",Parcels.wrap(sender));
        startActivity(i);
    }
}
