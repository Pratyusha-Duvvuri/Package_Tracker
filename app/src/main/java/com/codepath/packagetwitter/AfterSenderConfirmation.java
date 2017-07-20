package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AfterSenderConfirmation extends AppCompatActivity{

    Receiver receiver;
    Sender sender;
    Mail mail;
    User receiverUser;
    User USER;


    //News things that receiver has to enter

    @BindView(R.id.tvSSenderName)TextView tvSenderName;
    @BindView(R.id.tvSSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvSStartDate)TextView tvStartDate;
    @BindView(R.id.tvSEndDate)TextView tvEndDate;
    //Didnt put one for packageDetailHeading
    @BindView(R.id.tvRPackageType)TextView type;
    @BindView(R.id.tvRWeight)TextView tvWeight;
    @BindView(R.id.tvRFragile)TextView fragile;
    @BindView(R.id.tvRHeight)TextView tvHeight;
    @BindView(R.id.tvRWidth)TextView tvWidth;
    @BindView(R.id.tvRLength)TextView tvLength;
    @BindView(R.id.tvRDescription)TextView tvDescription;
    @BindView(R.id.ivRImage)ImageView ivPackage;

    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
    @BindView(R.id.etRStartDate)EditText startDate;
    @BindView(R.id.etREndDate)EditText endDate;
    @BindView(R.id.myFABOkay)FloatingActionButton fabOkay;
    ParselTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //receiverUser= Parcels.unwrap(getIntent().getParcelableExtra("receiver"));
        //USER= Parcels.unwrap(getIntent().getParcelableExtra("USER"));
        //receiver = new Receiver(receiverUser);
        //sender = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
        //mail = Parcels.unwrap(getIntent().getParcelableExtra("mail"));
//        sender = Sender.getRandomSender(this);
//        mail = Mail.getRandomMail(this);
        setContentView(R.layout.activity_after_sender_confirmation);

        ButterKnife.bind(this);
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // Define our query conditions
        query.whereEqualTo("receiver", parseUser.getUsername());
        query.whereEqualTo("transactionState", 0);

        // Execute the find asynchronously
        query.findInBackground(new FindCallback<ParselTransaction>() {
            @Override
            public void done(List<ParselTransaction> issueList, ParseException e) {
                if (e == null) {
                    transaction =  issueList.get(0);
                    onSetLayout();


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });



        //set listener for confirmation

        fabOkay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                receiver.setTripStart(startDate.getText().toString());
//                receiver.setTripEnd(endDate.getText().toString());
//                receiver.setLocation((receiverLocation.getText().toString()));

                transaction.addReceiverInfo(transaction.getSenderStart(), transaction.getSenderEnd(), receiverLocation.getText().toString());
                transaction.saveEventually();
                //Call the modal to verify information
                onVerifyAction();

            }
        });
    }
    public void onVerifyAction(){
        //either pass to another activity or put in database

       Intent i = new Intent(this, ProfileActivity.class);
//        i.putExtra("receiver", Parcels.wrap(receiver));
//        i.putExtra("sender", Parcels.wrap(sender));
//        i.putExtra("mail", Parcels.wrap(mail));
//        i.putExtra("USER", Parcels.wrap(USER) );
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }



    public void onSetLayout(){

        fragile.setText(""+transaction.getIsFragile());
        type.setText(""+transaction.getMailType());
        tvSenderLocation.setText(""+transaction.getSenderLoc());
        tvStartDate.setText(""+transaction.getSenderStart());
        tvEndDate.setText(""+transaction.getSenderEnd());
        tvWeight.setText(""+transaction.getWeight());
        tvHeight.setText(""+transaction.getVolume());
        tvWidth.setText(""+transaction.getVolume());
        tvLength.setText(""+transaction.getVolume());
        tvDescription.setText(""+transaction.getMailDescription());
        ivPackage.setImageResource(R.drawable.ic_upload);


    }


}
