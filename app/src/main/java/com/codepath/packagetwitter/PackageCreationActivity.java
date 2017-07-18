package com.codepath.packagetwitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.packagetwitter.Fragments.PackageConfirmation_Fragment;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageCreationActivity extends AppCompatActivity implements PackageConfirmation_Fragment.SendDialogListener {

    String sender_handle;
    Context context;
    Receiver receiver;
    Sender sender;
    Mail mail;
    User user;
    User USER;

    @BindView(R.id.tvSenderLocation) TextView tvSenderLocation;
    @BindView(R.id.tvStartDate) TextView tvStartDate;
    @BindView(R.id.tvEndDate) TextView tvEndDate;
    @BindView(R.id.tvPackageDetailHeading) TextView tvPackageDetailHeading;
    @BindView(R.id.tvPackageType) TextView tvPackageType;
    @BindView(R.id.tvWeight) TextView tvWeight;
    @BindView(R.id.tvFragile) TextView tvFragile;
    @BindView(R.id.tvDimensionsHeading) TextView tvDimensionsHeading;
    @BindView(R.id.tvLength) TextView tvLength;
    @BindView(R.id.tvWidth) TextView tvWidth;
    @BindView(R.id.tvHeight) TextView tvHeight;
    @BindView(R.id.tvConfirm) TextView tvConfirm;
    @BindView(R.id.tvPackage) TextView tvPackage;
    @BindView(R.id.tvReceiverHandle) TextView tvReceiverHandle;
    @BindView(R.id.etSenderLocation) EditText etSenderLocation;
    @BindView(R.id.etStartDate) EditText etStartDate;
    @BindView(R.id.etEndDate) EditText etEndDate;
    @BindView(R.id.etWeight) EditText etWeight;
    @BindView(R.id.etLength) EditText etLength;
    @BindView(R.id.etWidth) EditText etWidth;
    @BindView(R.id.etHeight) EditText etHeight;
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.etReceiverHandle) TextView etReceiverHandle;
    @BindView(R.id.rbFragile) RadioButton rbFragile;
    @BindView(R.id.rbNotFragile) RadioButton rbNotFragile;
    @BindView(R.id.spPackageType) Spinner spPackageType;
    @BindView(R.id.ibUpload) ImageButton ibUpload;
    @BindView(R.id.flFAB) FrameLayout flFAB;
    @BindView(R.id.fbConfirm) FloatingActionButton fbConfirm;
    @BindView(R.id.card_view) CardView CardView;
    @BindView(R.id.llcardview) LinearLayout llCardView;
    @BindView(R.id.ivPackage) ImageView ivPackage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);
        ButterKnife.bind(this);
        mail = new Mail();
        context = this;
        USER = Parcels.unwrap(getIntent().getParcelableExtra("sender"));

        user = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
        sender = new Sender(user);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spPackageType.setAdapter(adapter);

        fbConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onVerifyAction();
//                mail.setDescription(etDescription.getText().toString());
//                mail.setFragile(true);//cbIsFragile.isChecked());
//                //have to set picture
//                mail.setWeight(Double.parseDouble(etWeight.getText().toString()));
//               // mail.setType(spPackageType.getSelectedItem().toString());
//                int []Arr={Integer.parseInt(etLength.getText().toString()),Integer.parseInt(etWidth.getText().toString()),Integer.parseInt(etHeight.getText().toString())};
//                mail.setVolume(Arr);
//                sender.setTripStart(etStartDate.getText().toString());
//                sender.setTripEnd(etEndDate.getText().toString());
//                sender.setLocation(Double.parseDouble(etSenderLocation.getText().toString()));
//                receiver = Receiver.getRandomReceiver(context);
//                //Call the modal to verify information
//                onVerifyAction();
            }
        });

    }


    public void onVerifyAction() {
        fake();
        FragmentManager fm = getSupportFragmentManager();
        PackageConfirmation_Fragment frag = PackageConfirmation_Fragment.newInstance(mail, sender, receiver);
        frag.show(fm, "fragment_package_confirmation");
    }

    public void fake(){
        sender = Sender.getRandomSender(this);
        receiver=Receiver.getRandomReceiver(this);
        mail = Mail.getRandomMail(this);

    }

    @Override
    public void onFinishEditDialog(Sender sender, Receiver receiver, Mail mail, Boolean bool) {
        //reset information and check if listener is called
        if (bool) {
            Intent i = new Intent(this, ProfileActivity.class);
            i.putExtra("receiver", Parcels.wrap(receiver));
            i.putExtra("sender", Parcels.wrap(sender));
            i.putExtra("mail", Parcels.wrap(mail));
            i.putExtra("USER", Parcels.wrap(USER));
            startActivity(i);
            setResult(RESULT_OK, i); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }

    }
}
