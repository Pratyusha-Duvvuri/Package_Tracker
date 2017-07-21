package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

public class CourierActivity extends AppCompatActivity {


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

    Double weightAvailable;
    String tripStart;
    String tripEnd;

    int volumes;
    String startAddress;
    String endAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        ButterKnife.bind(this);


        // Apply the adapter to the spinner

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                weightAvailable = Double.parseDouble(weight.getText().toString());
                tripStart =  startMonth.getText().toString() + "/" + startDay.getText().toString();
                tripEnd =  endMonth.getText().toString() + "/" + endDay.getText().toString();
                volumes = Integer.valueOf((volume.getText().toString()));
                startAddress =  startLocation.getText().toString();

                endAddress =  locationEnd.getText().toString();

                onVerifyAction();
            }
        });

    }


    public void onVerifyAction() {


        //queries all pending transactions

        parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 1); //pending transaction state
        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {
                Date courierEndDate= null;

                Date courierStartDate = null;
                if (e == null) {
                    //access parsel transactions here
                    for (int i = 0; i < itemList.size(); i++){
                        //for every parsel transaction
                        ParselTransaction parselTransaction = itemList.get(i);
                        try {
                            courierStartDate = new SimpleDateFormat("MM/dd").parse(tripStart);
                            courierEndDate = new SimpleDateFormat("MM/dd").parse(tripEnd);

                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }

                        //if it's a match:
                        if (Algorithm.isPossibleMatch(parselTransaction, tripStart,tripEnd,weightAvailable,volumes, startAddress, endAddress)){





                            //if its a match
                            parselTransaction.addCourierInfo(parseUser.getUsername(), courierStartDate, courierEndDate);
                            parselTransaction.saveEventually();
                            break;


                        }
                        else{
                            //if it's not a match:
                            ParselTransaction courierParsel  = new ParselTransaction(parseUser.getUsername(), startAddress,endAddress,courierStartDate,
                                    courierEndDate,weightAvailable,volumes); //makes a parsel transaction
                            courierParsel.saveEventually(); // saves it
                        }

                    }

                } else {
                    // something went wrong
                }
            }
        });

        Intent i = new Intent(this, ProfileActivity.class);
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent


    }

}