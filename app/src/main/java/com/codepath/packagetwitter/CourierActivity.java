package com.codepath.packagetwitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



    @BindView(R.id.etLocationStart)EditText startLocation;

    @BindView(R.id.etLocationEnd)TextView locationEnd;
    @BindView(R.id.next)Button btnNext;
Button confirm;

    Double weightAvailable;
    String tripStart;
    String tripEnd;

    int volumes;
    String startAddress;
    String endAddress;
    private View view1, view2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_courier, null);
        view2 = getLayoutInflater().inflate(R.layout.activity_courier_2, null);
        setContentView(view1);
        ButterKnife.bind(this);
        context = this;



        // Apply the adapter to the spinner

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                startAddress =  startLocation.getText().toString();
                endAddress =  locationEnd.getText().toString();

                setContentView(view2);
                confirm = (Button) findViewById(R.id.btConfirm);

                confirm.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){

                        Intent i = new Intent(context, ProfileActivity.class);
                        setResult(RESULT_OK, i); // set result code and bundle data for response
                        finish(); // closes the activity, pass data to parent
                    }
                });



//                weightAvailable = Double.parseDouble(weight.getText().toString());
//                tripStart =  startMonth.getText().toString() + "/" + startDay.getText().toString();
//                tripEnd =  endMonth.getText().toString() + "/" + endDay.getText().toString();
//                volumes = Integer.valueOf((volume.getText().toString()));
//                startAddress =  startLocation.getText().toString();
//
//                endAddress =  locationEnd.getText().toString();

                //onVerifyAction();
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
                boolean mark= false;

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

                            mark = true;



                            //if its a match
                            parselTransaction.addCourierInfo(parseUser.getUsername(), courierStartDate, courierEndDate);
                            parselTransaction.saveEventually();
                            break;


                        }
                        else{
                            //if it's not a match:

                        }

                    }
                    if (! mark){
                        ParselTransaction courierParsel  = new ParselTransaction(parseUser.getUsername(), startAddress,endAddress,courierStartDate,
                                courierEndDate,weightAvailable,volumes); //makes a parsel transaction
                        courierParsel.saveEventually(); // saves it}
                    }

                } else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
            }
        });

        Intent i = new Intent(this, ProfileActivity.class);
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent


    }

}