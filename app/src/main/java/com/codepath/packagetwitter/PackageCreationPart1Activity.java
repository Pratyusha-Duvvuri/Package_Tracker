package com.codepath.packagetwitter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pratyusha98 on 7/24/17.
 */

public class PackageCreationPart1Activity extends AppCompatActivity {

    Context context;
    Integer year, month, date;
    static final int DIALOG_ID = 0;
    static final int DIALOG_ID2 = 10;
    public static int idd;
    public String s1,s2,s3,S1,S2,S3,sendStart,sendEnd;
    ParselTransaction transaction;
    Boolean proceed;
    public final int UPLOAD_IMAGE_CODE = 100;

    @BindView(R.id.etsenderLocationB) EditText senderLocationB;
    @BindView(R.id.etreceiverHandleB) EditText receiverHandle;
    @BindView(R.id.displaysenderend)TextView displaySenderEnd;
    @BindView(R.id.displaysenderstart)TextView displaySenderStart;

    @BindView(R.id.senderStartDateB) Button startDate;
    @BindView(R.id.senderEndDateB) Button endDate;
    @BindView(R.id.fbConfirmB)  FloatingActionButton fbConfirmB;
    @BindView(R.id.imageViewUp) ImageView package_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_creation_part1);
        ButterKnife.bind(this);
        transaction = new ParselTransaction();
        transaction.setTransactionState(20);
        transaction.saveInBackground();
        context = this;
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);
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
        package_image.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //save information
                        //set up an intent
                        Intent i = new Intent(PackageCreationPart1Activity.this, UploadPackageImageActivity.class);
                        i.putExtra("TRANSACTION", transaction.getObjectId());
                        startActivityForResult(i,UPLOAD_IMAGE_CODE);
                        proceed = true;


                    }
                }

        );

    }

    public void saveImage(){
     transaction = new ParselTransaction();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPLOAD_IMAGE_CODE && resultCode == RESULT_OK) // if a courier transaction occured
        {
            //load the image here
            ParseFile postImage = transaction.getParseFile("ImageFile");
            if (postImage != null) {
                String imageUrl = postImage.getUrl();//live url

                Uri imageUri = Uri.parse(imageUrl);
                Glide.with(PackageCreationPart1Activity.this).load(imageUri.toString()).into(package_image);
            } else {
                Glide.with(PackageCreationPart1Activity.this).load("http://i.imgur.com/zuG2bGQ.jpg").into(package_image);

            }
        }
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
//


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

