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

import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.ProfileActivity.COURIER_KEY;
import static com.codepath.packagetwitter.ProfileActivity.parseUser;

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

                courier = new CourierModel(u, tripStart,  tripEnd,  weightAvailable, volumes[0],  startAddress,  endAddress);


                onVerifyAction();
            }
        });

    }


    public void onVerifyAction() {
        //queries all pending transactions
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 1); //pending transaction state
        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {
                if (e == null) {
                    //access parsel transactions here
                    for (int i = 0; i < itemList.size(); i++){
                        //for every parsel transaction
                        ParselTransaction parselTransaction = itemList.get(i);
                        if (Algorithm.isPossibleMatch(parselTransaction, courier)){
                            Date courierEndDate= null;

                            Date courierStartDate = null;

                            try {
                                courierStartDate = new SimpleDateFormat("MM/dd").parse(courier.getTripStart());
                                courierEndDate = new SimpleDateFormat("MM/dd").parse((courier.getTripEnd()));

                            } catch (java.text.ParseException e1) {
                                e1.printStackTrace();
                            }



                            //if its a match
                            parselTransaction.addCourierInfo(parseUser.getUsername(), courierStartDate, courierEndDate);


                        }

                    }

                } else {
                    // something went wrong
                }
            }
        });

        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra(COURIER_KEY, Parcels.wrap(courier));
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent


    }

}