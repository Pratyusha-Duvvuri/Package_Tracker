package com.codepath.packagetwitter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pratyusha98 on 7/24/17.
 */

public class PackageCreationPart1Activity extends AppCompatActivity {

    Context context;
    Integer year, month, date;
    public final int PART2 = 39;

    static final int DIALOG_ID = 0;
    static final int DIALOG_ID2 = 10;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static int idd;
    public String s1,s2,s3,S1,S2,S3,sendStart,sendEnd;
    ParselTransaction transaction;
    Boolean proceed;
    public final int UPLOAD_IMAGE_CODE = 100;
    //these are for the locations
    @BindView(R.id.etsenderStartLocationB) EditText senderLocationB;
    @BindView(R.id.etreceiverEndLocationB) EditText receiverLocationB;
    @BindView(R.id.et_receiverHandle)
    AutoCompleteTextView receiverHandle;
    //these are supposed to be the text views
    @BindView(R.id.senderEndDateB)TextView displaySenderEnd;
    @BindView(R.id.senderStartDateB)TextView displaySenderStart;

    @BindView(R.id.startCalendar) ImageView startDate;
    @BindView(R.id.endCalendar) ImageView endDate;

    @BindView(R.id.next)  Button fbConfirmB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packagecreationaa);

        ButterKnife.bind(this);

        transaction = new ParselTransaction();
        transaction.setTransactionState(20);
        transaction.saveInBackground();
        context = this;
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);

        proceed=false;

// Get the string array
        String[] countries = getResources().getStringArray(R.array.users_array);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        receiverHandle.setAdapter(adapter);

        //create a new transaction here
        showDialogButtonClick();
        fbConfirmB.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //save information
                        if(proceed)
                        saveInformation();
                    }
                }

        );
        senderLocationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }

        });



    }


    public void saveInformation(){
        Date senderStartDate = null;
        Date senderEndDate = null;
        try {
            senderStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(sendStart);
            senderEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(sendEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        //creating new transaction parse object

        transaction.setSenderStart(senderStartDate);
        transaction.setSenderEnd(senderEndDate);
        transaction.setReceiver(receiverHandle.getText().toString());
        transaction.setSenderLoc(senderLocationB.getText().toString());
        transaction.saveEventually();
        //parseUser.add("pendingTransactions", transaction);
        transaction.saveEventually();
        Intent i = new Intent(PackageCreationPart1Activity.this, PackageCreationPart2Activity.class);
        i.putExtra("TRANSACTION", transaction.getObjectId());
        startActivityForResult(i, PART2);//

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
                sendStart = Month+"/"+Date+"/"+Year;

                displaySenderStart.setText(sendStart);

            }
            else{
                //end date
//                sendEnd= S1 + "/" + S2+"/"+S3;
                sendEnd = Month+"/"+Date+"/"+Year;
                displaySenderEnd.setText(sendEnd);

            }

        }
    };



}

