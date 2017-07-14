package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AfterSenderConfirmation extends AppCompatActivity{

    Receiver receiver;
    Sender sender;
    Mail mail;
    User receiverUser;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiverUser= Parcels.unwrap(getIntent().getParcelableExtra("receiver"));
        receiver = new Receiver(receiverUser);
        sender = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
        mail = Parcels.unwrap(getIntent().getParcelableExtra("mail"));
//        sender = Sender.getRandomSender(this);
//        mail = Mail.getRandomMail(this);
        setContentView(R.layout.activity_after_sender_confirmation);

        ButterKnife.bind(this);
        onSetLayout();


        //set listener for confirmation

        fabOkay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                receiver.setTripStart(startDate.getText().toString());
                receiver.setTripEnd(endDate.getText().toString());
                receiver.setLocation(Double.parseDouble(receiverLocation.getText().toString()));

                //Call the modal to verify information
                onVerifyAction();

            }
        });
    }
    public void onVerifyAction(){
        //either pass to another activity or put in database

       Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("receiver", Parcels.wrap(receiver));
        i.putExtra("sender", Parcels.wrap(sender));
        i.putExtra("mail", Parcels.wrap(mail));
       startActivity(i);

    }



    public void onSetLayout(){

       fragile.setText(""+mail.isFragile());
       type.setText(""+mail.getType());
        tvSenderLocation.setText(""+sender.getLocation());
        tvStartDate.setText(sender.getTripStart());
        tvEndDate.setText(sender.getTripEnd());
        tvWeight.setText(""+mail.getWeight());
        tvHeight.setText(""+mail.getVolume()[2]);
        tvWidth.setText(""+mail.getVolume()[1]);
        tvLength.setText(""+mail.getVolume()[0]);
        tvDescription.setText(""+mail.getDescription());
        ivPackage.setImageResource(R.drawable.ic_upload);


    }


}
