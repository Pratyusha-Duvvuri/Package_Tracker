package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;

import butterknife.BindView;

import static com.codepath.packagetwitter.R.id.btnNext;

public class PackageCreationActivity extends AppCompatActivity {

String sender_handle;
    Receiver receiver;
    Sender sender;
    Mail mail;
    @BindView(R.id.etWeight)EditText weight;
    @BindView(R.id.etLength)EditText lenght;
    @BindView(R.id.etBreadth)EditText breadth;
    @BindView(R.id.etHeight)EditText height;
    @BindView(R.id.etDescription)EditText description;
    @BindView(R.id.etSpecialRequirements)EditText specialRequirements;
    @BindView(R.id.etSenderLocation)EditText senderLocation;
    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
    @BindView(R.id.rbIsFragile)RadioButton isFragile;
    @BindView(R.id.spType)Spinner type;
    @BindView(R.id.tvSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvReceiverLocation)TextView tvReceiverLocation;
    @BindView(R.id.tvWeight)TextView tvWeight;
    @BindView(R.id.tvHeight)TextView tvHeight;
    @BindView(R.id.tvBreadth)TextView tvBreadth;
    @BindView(R.id.tvDescription)TextView tvDescription;
    @BindView(R.id.tvSpecialRequirements)TextView tvSpecialRequirements;
    @BindView(btnNext)TextView next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);

        receiver = getIntent().getParcelableExtra("receiver");
        sender = getIntent().getParcelableExtra("sender");

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                    sender = Sender.getRandomSender();
//
//                }
//            }
//
//        );


    }

}
