package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

import butterknife.BindView;

public class AfterSenderConfirmation extends AppCompatActivity{

    Receiver receiver;
    Sender sender;
    Mail mail;
    User receiverUser;


    //News things that receiver has to enter
    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
    @BindView(R.id.etRStartDate)EditText startDate;
    @BindView(R.id.etREndDate)EditText endDate;

    //Types from previous activity
    @BindView(R.id.tvFragile)TextView fragile;
    @BindView(R.id.tvRPackageType)TextView type;
    @BindView(R.id.tvSSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvSStartDate)TextView tvStartDate;
    @BindView(R.id.tvSEndDate)TextView tvEndDate;
    @BindView(R.id.tvRWeight)TextView tvWeight;
    @BindView(R.id.tvRHeight)TextView tvHeight;
    @BindView(R.id.tvRWidth)TextView tvWidth;
    @BindView(R.id.tvRLength)TextView tvLength;
    @BindView(R.id.tvRDescription)TextView tvDescription;

//    @BindView(R.id.fbRConfirm)FloatingActionButton btnNext;
    @BindView(R.id.ivPackageImage)ImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiverUser= Parcels.unwrap(getIntent().getParcelableExtra("receiver"));
        receiver = new Receiver(receiverUser);
        sender = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
        mail = Parcels.unwrap(getIntent().getParcelableExtra("mail"));
        setContentView(R.layout.activity_after_sender_confirmation);
        //onSetLayout();

//
//        //set listener for confirmation
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                receiver.setTripStart(startDate.getText().toString());
//                receiver.setTripEnd(endDate.getText().toString());
//                receiver.setLocation(Double.parseDouble(receiverLocation.getText().toString()));
//        /*fragile.setText(""+mail.isFragile());
//        type.setText(""+mail.getType());
//        tvSenderLocation.setText(""+sender.getLocation());
//        tvStartDate.setText(sender.getTripStart());
//        tvEndDate.setText(sender.getTripEnd());
//        tvWeight.setText(""+mail.getWeight());
//        tvHeight.setText(""+mail.getVolume()[2]);
//        tvWidth.setText(""+mail.getVolume()[1]);
//        tvLength.setText(""+mail.getVolume()[0]);
//        tvDescription.setText(""+mail.getDescription());
//        */ivImage.setImageResource(R.drawable.ic_upload);
//                //Call the modal to verify information
//                onVerifyAction();
//
//            }
//        });
//    }
//    public void onVerifyAction(){
////       Intent i = new Intent(this, PackageCreationActivity.class);
////        i.putExtra("receiver", Parcels.wrap(receiver));
////        i.putExtra("sender", Parcels.wrap(sender));
////        i.putExtra("mail", Parcels.wrap(mail));
////       startActivity(i);
//
//    }
//
//
//
//    public void onSetLayout(){
//
////       fragile.setText(""+mail.isFragile());
////       type.setText(""+mail.getType());
////        tvSenderLocation.setText(""+sender.getLocation());
////        tvStartDate.setText(sender.getTripStart());
////        tvEndDate.setText(sender.getTripEnd());
////        tvWeight.setText(""+mail.getWeight());
////        tvHeight.setText(""+mail.getVolume()[2]);
////        tvWidth.setText(""+mail.getVolume()[1]);
////        tvLength.setText(""+mail.getVolume()[0]);
////        tvDescription.setText(""+mail.getDescription());
////        ivImage.setImageResource(R.drawable.ic_upload);
    }


}
