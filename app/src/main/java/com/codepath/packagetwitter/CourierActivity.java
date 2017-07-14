package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourierActivity extends AppCompatActivity {

    CourierModel courier;
    User u;

    @BindView(R.id.etWeight)EditText weight;
    @BindView(R.id.etVolume)EditText volume;
    @BindView(R.id.etLocationStart)EditText startLocation;
    @BindView(R.id.etStartDay)EditText startDay;
    @BindView(R.id.etMonthStart)EditText startMonth;
    @BindView(R.id.etDayEnd)EditText endDay;
    @BindView(R.id.etMonthEnd)EditText endMonth;
    @BindView(R.id.etLocationEnd)TextView locationEnd;
    @BindView(R.id.fbConfirm)FloatingActionButton btnNext;
    @BindView(R.id.ivTakeOff)ImageView ivTakeOff;
    @BindView(R.id.tvFirstDash)TextView tvFirstDash;
    @BindView(R.id.tvSecondDash)TextView tvSecondDash;
    @BindView(R.id.ivLanding)ImageView ivLanding;
    @BindView(R.id.flFAB)FrameLayout flFAB;
    @BindView(R.id.tvConfirm)TextView tvConfirm;
    @BindView(R.id.tvHeading)TextView tvHeading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        ButterKnife.bind(this);
        courier = new CourierModel();
        u = Parcels.unwrap(getIntent().getParcelableExtra("courier"));


        // Apply the adapter to the spinner

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
            //(String name, String handle, String phone, String tripStart, String tripEnd, double weight, int[] v, String sAddress, String eAddress);


                Double weightAvailable = Double.parseDouble(weight.getText().toString());
                String tripStart =  startMonth.getText().toString() + "/" + startDay.getText().toString();
                String tripEnd =  endMonth.getText().toString() + "/" + endDay.getText().toString();
                String line = volume.getText().toString();
                String[] numberStrs = line.split(",");
                int[] volumes = new int[numberStrs.length];
                for(int i = 0;i < numberStrs.length;i++)
                {
                    // Note that this is assuming valid input
                    // If you want to check then add a try/catch
                    // and another index for the numbers if to continue adding the others (see below)
                    volumes[i] = Integer.parseInt(numberStrs[i]);
                }                //Call the modal to verify information

                String startAddress =  startLocation.getText().toString();


                String endAddress =  locationEnd.getText().toString();

                courier = new CourierModel(u, tripStart,  tripEnd,  weightAvailable, volumes,  startAddress,  endAddress);


                //onVerifyAction();
            }
        });

    }


    public void onVerifyAction() {

        FragmentManager fm = getSupportFragmentManager();
        //PackageConfirmation_Fragment frag = PackageConfirmation_Fragment.newInstance(mail,sender,receiver);
        //frag.show(fm, "fragment_package_confirmation");
    }
    public void onFinishEditDialog(Sender sender, Receiver receiver, Mail mail){
        //reset information and check if listener is called
    }

}