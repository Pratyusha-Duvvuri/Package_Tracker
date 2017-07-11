package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

public class NewtransactionActivity extends AppCompatActivity {


    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("user");
        setContentView(R.layout.activity_newtransaction);
        //make on click listeners for all of them
        //also get intent - user
        //make everything parcelable
        //create three buttons for sender receiver and carrier

        final Button btnSender = (Button) findViewById(R.id.ntbtSender);
        btnSender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSenderSuccess();
            }

        });


        final Button btnReceiver = (Button) findViewById(R.id.ntbtReceiver);
        btnReceiver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onReceiverSuccess();
            }

        });


        final Button btnCourier = (Button) findViewById(R.id.ntbtCourier);
        btnCourier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCourierSuccess();
            }

        });


    }

    public void onReceiverSuccess() {

        // Intent i = new Intent(this, PhotosActivity.class);
        // startActivity(i);
        //this is just to show that we have reached this point
        //Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, SenderActivity.class);

        i.putExtra("receiver",Parcels.wrap(user) );
        startActivity(i);
    }

    public void onSenderSuccess() {

        // Intent i = new Intent(this, PhotosActivity.class);
        // startActivity(i);
        //this is just to show that we have reached this point
        //Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ReceiverActivity.class);
        i.putExtra("sender", Parcels.wrap(user) );

        startActivity(i);
    }

    public void onCourierSuccess() {

        // Intent i = new Intent(this, PhotosActivity.class);
        // startActivity(i);
        //this is just to show that we have reached this point
        //Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, CourierActivity.class);
        i.putExtra("courier",Parcels.wrap(user) );

        startActivity(i);

    }

}
