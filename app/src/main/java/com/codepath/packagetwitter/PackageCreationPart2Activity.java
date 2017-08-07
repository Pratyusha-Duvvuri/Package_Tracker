package com.codepath.packagetwitter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;


/**
 * Created by pratyusha98 on 7/25/17.
 */


public class PackageCreationPart2Activity extends AppCompatActivity {
    byte[] YOIMG;
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

    //Package image view
    @BindView(R.id.ivPackageImage) ImageView ivPackageImage;


    //default android text color
    int defaultTextColor;

    //data to be sent through intent
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    String title;
    String description;
    int volume;
    String volume_string;
    Double weight;
    String type;
    Boolean fragile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;

        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.package_creation_part2);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_white_plane);
        getSupportActionBar().setTitle("Package Details");
        toolbar.setBackgroundColor(getColor(R.color.colorPrimary));


        defaultTextColor = tvClothes.getTextColors().getDefaultColor();

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

        ivPackageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLaunchCamera(view);

            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = etTitle.getText().toString();
                description = etDescription.getText().toString();
                weight = Double.parseDouble(etWeight.getText().toString());
                int id = rgFragile.getCheckedRadioButtonId();


                if (rgFragile.getCheckedRadioButtonId() == R.id.rbYes) {
                    fragile = true;
                }
                else {
                    fragile = false;
                }


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

                //from previous activity
                review.putExtra("senderStartDate", strsenderStartDate);
                review.putExtra("senderEndDate", strsenderEndDate);
                review.putExtra("receiverHandle", getIntent().getStringExtra("receiverHandle"));
                review.putExtra("senderLocation", getIntent().getStringExtra("senderLocation"));
                review.putExtra("receiverLocation", getIntent().getStringExtra("receiverLocation"));

                //From current activity
                review.putExtra("title", title);
                review.putExtra("description", description);
                review.putExtra("weight", weight);
                review.putExtra("fragile", fragile);
                review.putExtra("type", type);
                review.putExtra("volume", volume);
                review.putExtra("volume_string", volume_string);
                review.putExtra("package_image", YOIMG);


                startActivityForResult(review, REVIEW_REQUEST);

            }
        });

    }
    @NeedsPermission(Manifest.permission.CAMERA	)
    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent takepicintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takepicintent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(takepicintent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REVIEW_REQUEST) {
            if (resultCode == RESULT_OK) {
                //finish the other activity lol
                onSubmit();
            }
        }

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                ivPackageImage.setImageBitmap(bitmap);
                ivPackageImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // Convert it to gibyte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                YOIMG = stream.toByteArray();


                // Show a simple toast message
                Toast.makeText(PackageCreationPart2Activity.this, "Image Saved",
                        Toast.LENGTH_SHORT).show();

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
