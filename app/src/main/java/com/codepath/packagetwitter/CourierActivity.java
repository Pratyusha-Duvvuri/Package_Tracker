package com.codepath.packagetwitter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.PackageCreationPart1Activity.DIALOG_ID;
import static com.codepath.packagetwitter.PackageCreationPart1Activity.DIALOG_ID2;
import static com.codepath.packagetwitter.PackageCreationPart1Activity.idd;
import static com.codepath.packagetwitter.ProfileActivity.parseUser;

public class CourierActivity extends AppCompatActivity {



    LinearLayout llSizeTitles;

    TextView tvKey;
    TextView tvPhone;
    TextView tvBook;
    TextView tvFish;

    @BindView(R.id.etLocationStart)TextView startLocation;

    @BindView(R.id.etLocationEnd)TextView locationEnd;
    @BindView(R.id.displaySenderStart)TextView displaySenderStart;
    @BindView(R.id.displaySenderEnd)TextView displaySenderEnd;


    @BindView(R.id.imageView3)ImageView startDate;
    @BindView(R.id.imageView4)ImageView endDate;


    @BindView(R.id.next)Button btnNext;

    Button confirm;

    Double weightAvailable;
    String tripStart;
    String tripEnd;




    Integer year, month, date;
    int volumes;
    String startAddress;
    String endAddress;
    private View view1, view2;
    RadioGroup rgSize;

    Context context;
    String volume_string;

    int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_courier, null);
        view2 = getLayoutInflater().inflate(R.layout.activity_courier_2, null);
        setContentView(view1);
        ButterKnife.bind(this);
        context = this;
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);
        showDialogButtonClick();

        // Apply the adapter to the spinner

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                startAddress =  startLocation.getText().toString();
                endAddress =  locationEnd.getText().toString();

                setContentView(view2);

                llSizeTitles = view2.findViewById(R.id.llSizeTitles);
                tvKey = view2.findViewById(R.id.tvKey);
                tvPhone = view2.findViewById(R.id.tvKey);
                tvFish =view2.findViewById(R.id.tvFishBowl);
                rgSize = view2.findViewById(R.id.rgSize);







                confirm = (Button) findViewById(R.id.btConfirm);

                rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        //takes in id of currently clicked item
                        defaultTextColor = tvPhone.getTextColors().getDefaultColor();

                        switch (i) {
                            //switches bolded text based on currently selected item
                            case R.id.rbKey:

                                for (int j = 0; j < llSizeTitles.getChildCount(); j++) {
                                    if (llSizeTitles.getChildAt(j) == tvKey) {

                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                                    }
                                    else {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                                    }
                                }
                                break;



                            case R.id.rbPhone:

                                for (int j = 0; j < llSizeTitles.getChildCount(); j++) {
                                    if (llSizeTitles.getChildAt(j) == tvPhone) {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                                    }
                                    else {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                                    }
                                }
                                break;

                            case R.id.rbBook:

                                for (int j = 0; j < llSizeTitles.getChildCount(); j++) {
                                    if (llSizeTitles.getChildAt(j) == tvBook) {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                                    }
                                    else {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                                    }
                                }
                                break;

                            case R.id.rbFishBowl:

                                for (int j = 0; j < llSizeTitles.getChildCount(); j++) {
                                    if (llSizeTitles.getChildAt(j) == tvFish) {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                                    }
                                    else {
                                        ((TextView)llSizeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

                showDialogButtonClick();


                confirm.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){

                        try {
                            weightAvailable = Double.parseDouble(((EditText) findViewById(R.id.etWeight)).getText().toString());

                            onVerifyAction();

                            Intent i = new Intent(context, ProfileActivity.class);

                            setResult(RESULT_OK, i); // set result code and bundle data for response
                            finish(); // closes the activity, pass data to parent
                        }
                        catch(NumberFormatException e) {

                            Toast.makeText(CourierActivity.this, "You forgot some fields...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });




    }

    public void showDialogButtonClick() {
        startDate.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        showDialog(DIALOG_ID);
                    }
                }

        );
        endDate.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        showDialog(DIALOG_ID2);
                    }
                }

        );

    }

    @Override
    public Dialog onCreateDialog(int id) {

        if (id == DIALOG_ID)
        {      idd = 1;
            return new DatePickerDialog(this, dpickerListener, year, month, date);
        }
        if (id == DIALOG_ID2) {
            idd=2;
            return new DatePickerDialog(this, dpickerListener, year, month, date);
        }
        else return null;

    }


    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int Year, int Month, int Date) {

            year = Year;
            month = Month+1;
            date= Date;
            if(idd == 1){
                //start date

//                sendStart = s1 + "/" + s2+"/"+s3;
                tripStart = Month+"/"+Date+"/"+Year;

                displaySenderStart.setText(tripStart);

            }
            else{
                //end date
//                sendEnd= S1 + "/" + S2+"/"+S3;
                tripEnd = Month+"/"+Date+"/"+Year;
                displaySenderEnd.setText(tripEnd);

            }

        }
    };
    public void onVerifyAction() {



        //queries all pending transactions

        if (rgSize.getCheckedRadioButtonId() == R.id.rbKey) {
            volumes = 0;
            volume_string = "Key Sized";
        }
        else if (rgSize.getCheckedRadioButtonId() == R.id.rbPhone) {
            volumes = 1;
            volume_string = "Phone Sized";
        }
        else if (rgSize.getCheckedRadioButtonId() == R.id.rbBook) {
            volumes = 2;
            volume_string = "Book Sized";
        }
        else {
            volumes = 3;
            volume_string = "Fish Bowl Sized";
        }

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
                            courierStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(tripStart);
                            courierEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(tripEnd);

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