package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Fragments.PackageConfirmation_Fragment;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;

public class AfterSenderConfirmation extends AppCompatActivity implements PackageConfirmation_Fragment.NextDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sender_confirmation);
    }

    @Override
    public void onNextEditDialog(Sender sender, Receiver receiver, Mail mail) {
        //Now populate stuff with this sender , receiver and mail
        //Get receiver location and start and end date and then send details for matching with appropriate courier

    }
}
