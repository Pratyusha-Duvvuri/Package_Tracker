package com.codepath.packagetwitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.ByteArrayInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by pratyusha98 on 7/25/17.
 */

public class PackageCreationPart2Activity extends Activity {

    ParselTransaction trans;
    public static final int REVIEW_REQUEST=90;

    //Binds Views to the layout file
    @BindView(R.id.etTitle) EditText etTitle;
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.etWeight) EditText etWeight;
    @BindView(R.id.rgFragile) RadioGroup rgFragile;
    @BindView(R.id.rbYes) RadioButton rbYes;
    @BindView(R.id.rbNo) RadioButton rbNo;

    //radio groups-type and size
    @BindView(R.id.rgType) RadioGroup rgType;
    @BindView(R.id.rgSize) RadioGroup rgSize;

    //radio buttons for the type attribute
    @BindView(R.id.rbClothes) RadioButton rbClothes;
    @BindView(R.id.rbElectronics) RadioButton rbElectronics;
    @BindView(R.id.rbBooks) RadioButton rbBooks;
    @BindView(R.id.rbGames) RadioButton rbGames;
    @BindView(R.id.rbMovies) RadioButton rbMovies;
    @BindView(R.id.rbFood) RadioButton rbFood;
    @BindView(R.id.rbHealth) RadioButton rbHealth;
    @BindView(R.id.rbBeauty) RadioButton rbBeauty;
    @BindView(R.id.rbOther) RadioButton rbOther;

    //radio buttons for size attribute
    @BindView(R.id.rbKey) RadioButton rbKey;
    @BindView(R.id.rbPhone) RadioButton rbPhone;
    @BindView(R.id.rbBook) RadioButton rbBook;
    @BindView(R.id.rbFishBowl) RadioButton rbFishBowl;

    //Linear Layouts for type and size card views
    @BindView(R.id.llType) LinearLayout llType;
    @BindView(R.id.llTypeTitles) LinearLayout llTypeTitles;
    @BindView(R.id.llSize) LinearLayout llSize;
    @BindView(R.id.llSizeTitles) LinearLayout llSizeTitles;
    @BindView(R.id.cvPackageType) CardView cvPackageType;

    //text views for type attribute
    @BindView(R.id.tvClothes) TextView tvClothes;
    @BindView(R.id.tvElectronics) TextView tvElectronics;
    @BindView(R.id.tvBooks) TextView tvBooks;
    @BindView(R.id.tvGames) TextView tvGames;
    @BindView(R.id.tvMovies) TextView tvMovies;
    @BindView(R.id.tvFood) TextView tvFood;
    @BindView(R.id.tvHealth) TextView tvHealth;
    @BindView(R.id.tvBeauty) TextView tvBeauty;
    @BindView(R.id.tvOther) TextView tvOther;

    //text views for size attribute
    @BindView(R.id.tvKey) TextView tvKey;
    @BindView(R.id.tvPhone) TextView tvPhone;
    @BindView(R.id.tvBook) TextView tvBook;
    @BindView(R.id.tvFishBowl) TextView tvFish;

    //confirm button
    @BindView(R.id.btnConfirm) Button btnConfirm;


    //default android text color
    int defaultTextColor;

    //data to be sent through intent
    ByteArrayInputStream image;
    String title;
    String description;
    int volume;
    String volume_string;
    Double weight;
    String type;
    Boolean fragile;

    String startAddress;
    String endAddress;
    String startDate;
    String endDate;
    String receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.package_creation_part2);
        ButterKnife.bind(this);
        defaultTextColor = tvClothes.getTextColors().getDefaultColor();
        /*
        //get attributes from part 1 of form
        String startAddress = getIntent().getStringExtra("startAddress");
        String endAddress = getIntent().getStringExtra("endAddress");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        String receiver = getIntent().getStringExtra("receiver");
        */
        //listener for when the selected radio button is changed, nothing happens if same one is clicked
        rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                //takes in id of currently clicked item
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



        //listener for when the selected radio button is changed, nothing happens if same one is clicked
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                //takes in id of currently clicked item
                switch (i) {
                    //switches bolded text based on currently selected item
                    case R.id.rbClothes:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvClothes) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;



                    case R.id.rbElectronics:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvElectronics) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbBooks:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvBooks) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbGames:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvGames) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbMovies:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvMovies) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;
                    case R.id.rbFood:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvFood) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbHealth:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvHealth) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbBeauty:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvBeauty) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    case R.id.rbOther:

                        for (int j = 0; j < llTypeTitles.getChildCount(); j++) {
                            if (llTypeTitles.getChildAt(j) == tvOther) {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(Color.BLACK);
                            }
                            else {
                                ((TextView)llTypeTitles.getChildAt(j)).setTextColor(defaultTextColor);
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = etTitle.getText().toString();
                description = etDescription.getText().toString();
                weight = Double.parseDouble(etWeight.getText().toString());

                if (rgFragile.getCheckedRadioButtonId() == R.id.Yes) {
                    fragile = true;
                }
                else if (rgFragile.getCheckedRadioButtonId() == R.id.No)
                    fragile = false;


                if (rgType.getCheckedRadioButtonId() == R.id.rbClothes) {
                    type = "Clothes";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbElectronics) {
                    type = "Electronics";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbBooks) {
                    type = "Books";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbGames) {
                    type = "Games";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbMovies) {
                    type = "Movies";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbFood) {
                    type = "Food";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbHealth) {
                    type = "Health";
                }
                else if (rgType.getCheckedRadioButtonId() == R.id.rbBeauty) {
                    type = "Beauty";
                }
                else
                    type = "Other";


                if (rgSize.getCheckedRadioButtonId() == R.id.rbKey) {
                    volume = 0;
                    volume_string = "Key Sized";
                }
                else if (rgSize.getCheckedRadioButtonId() == R.id.rbPhone) {
                    volume = 1;
                    volume_string = "Phone Sized";
                }
                else if (rgSize.getCheckedRadioButtonId() == R.id.rbBook) {
                    volume = 2;
                    volume_string = "Book Sized";
                }
                else {
                    volume = 3;
                    volume_string = "Fish Bowl Sized";
                }

                //pass all of the data from part 1 and 2 of the form to review activity
                Intent review = new Intent(PackageCreationPart2Activity.this, ReviewActivity.class);

                String strsenderStartDate= getIntent().getStringExtra("senderStartDate");
                String strsenderEndDate= getIntent().getStringExtra("senderEndDate");


                review.putExtra("senderStartDate", strsenderStartDate);
                review.putExtra("senderEndDate", strsenderEndDate);
                review.putExtra("receiverHandle", getIntent().getStringExtra("receiverHandle"));
                review.putExtra("senderLocation", getIntent().getStringExtra("senderLocation"));
                review.putExtra("receiverLocation", getIntent().getStringExtra("receiverLocation"));


                review.putExtra("startAddress", startAddress);
                review.putExtra("endAddress", endAddress);
                review.putExtra("startDate", startDate);
                review.putExtra("endDate", endDate);
                review.putExtra("receiver", receiver);
                review.putExtra("title", title);
                review.putExtra("description", description);
                review.putExtra("weight", weight);
                review.putExtra("fragile", fragile);
                review.putExtra("type", type);
                review.putExtra("volume", volume);
                review.putExtra("volume_string", volume_string);

                startActivityForResult(review, REVIEW_REQUEST);

            }
        });

        //not sure if we're still going to use this
        String parselTransactionID = getIntent().getStringExtra("TRANSACTION");
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(parselTransactionID, new GetCallback<ParselTransaction>() {
            @Override
            public void done(ParselTransaction item, ParseException e) {
                trans = item;
                if (e == null) {
                    Log.d("WORK", "inside query");
                    trans.put("title", etTitle.toString());
                    trans.setMailDescription(etDescription.toString());
                    trans.setWeight(Double.parseDouble(etWeight.toString()));

                }

                else {
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REVIEW_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //finsih the other activity lol
                onSubmit();
            }
        }
    }

    public void onSubmit() {
//  return to part 1
        Intent data = new Intent();
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }




}
