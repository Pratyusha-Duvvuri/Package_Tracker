package com.codepath.packagetwitter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.LoginActivity.mylist;
import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by pratyusha98 on 7/24/17.
 */

public class PackageCreationPart1Activity extends AppCompatActivity {

    Context context;
    Integer year, month, date;
    public final int PART2 = 39;

    static final int DIALOG_ID = 0;
    static final int DIALOG_ID2 = 10;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_1 = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_2 = 2;
    private String TAG = "Google Places API";

    public static int idd;
    public String s1,s2,s3,S1,S2,S3,sendStart,sendEnd;
    ParselTransaction transaction;
    Boolean proceed;
    public final int UPLOAD_IMAGE_CODEn = 100;
    //these are for the locations
    @BindView(R.id.etsenderStartLocationB)
    TextView senderLocationB;
    @BindView(R.id.etreceiverEndLocationB) TextView receiverLocationB;
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
        getSupportActionBar().setTitle("Create A New Package");

        senderLocationB.setText(parseUser.getString("location"));
        context = this;
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);

        proceed=false;

// Get the string array
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist);
        receiverHandle.setAdapter(adapter);

        //create a new transaction here
        showDialogButtonClick();
        fbConfirmB.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //save information
                        saveInformation();
                    }
                }

        );


        senderLocationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(PackageCreationPart1Activity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
       }

        });

        receiverLocationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(PackageCreationPart1Activity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_2);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }

        });

    }

    public void saveInformation(){
        Date senderStartDate = null;
        Date senderEndDate = null;
        try {
            senderStartDate = new SimpleDateFormat("MM/dd/yy").parse(sendStart);
            senderEndDate = new SimpleDateFormat("MM/dd/yy").parse(sendEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String strsenderStartDate= senderStartDate.toString();
        String strsenderEndDate= senderEndDate.toString();

        Intent i = new Intent(PackageCreationPart1Activity.this, PackageCreationPart2Activity.class);

        i.putExtra("senderStartDate", sendStart);
        i.putExtra("senderEndDate", sendEnd);
        i.putExtra("receiverHandle", "Pamela");
        i.putExtra("senderLocation", "Tokyo");
        i.putExtra("receiverLocation", "Boston");

//        i.putExtra("receiverHandle", receiverHandle.getText().toString());
//        i.putExtra("senderLocation", senderLocationB.getText().toString());
//        i.putExtra("receiverLocation", receiverLocationB.getText().toString());
        startActivityForResult(i, PART2);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == PART2) {
            // Extract name value from result extras
           returnToProfileActivity();

        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                senderLocationB.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_2) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                receiverLocationB.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }

    public void returnToProfileActivity(){

        Intent data = new Intent();
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
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
        displaySenderStart.setOnClickListener(
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
        displaySenderEnd.setOnClickListener(
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

