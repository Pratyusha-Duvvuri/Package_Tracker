package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Fragments.PackageConfirmation_Fragment;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;

import butterknife.BindView;

public class AfterSenderConfirmation extends AppCompatActivity implements PackageConfirmation_Fragment.NextDialogListener{


    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
    @BindView(R.id.etRStartDate)EditText startDate;
    @BindView(R.id.etREndDate)EditText endDate;
    @BindView(R.id.tvFragile)TextView fragile;
    @BindView(R.id.tvRPackageType)TextView type;
    @BindView(R.id.tvSSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvSStartDate)TextView tvStartDate;
    @BindView(R.id.tvSEndDate)TextView tvEndDate;
    @BindView(R.id.tvRWeight)TextView tvWeight;
    @BindView(R.id.tvRHeight)TextView tvHeight;
    @BindView(R.id.fbRConfirm)FloatingActionButton btnNext;
    @BindView(R.id.tvRWidth)TextView tvWidth;
    @BindView(R.id.tvRLength)TextView tvLength;
    @BindView(R.id.tvRDescription)TextView tvDescription;
    @BindView(R.id.ivPackageImage)ImageView ivImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sender_confirmation);
        //set layout here for all the views


    }

    @Override
    public void onNextEditDialog(Sender sender, Receiver receiver, Mail mail) {
        //Now populate stuff with this sender , receiver and mail
        //Get receiver location and start and end date and then send details for matching with appropriate courier


    }
}
