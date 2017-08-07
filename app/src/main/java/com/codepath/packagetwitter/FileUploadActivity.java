package com.codepath.packagetwitter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

@RuntimePermissions

/**
 * Created by pratyusha98 on 7/18/17.
 */

public class FileUploadActivity extends Activity {

    Button button;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public ImageView ivPreview;
    public Button btnUploadson;
    public Button GoBack;
    public EditText location;
    public EditText name;
    public EditText email;
    public ParseFile file;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE=90;
    public static Boolean newPictureTaken;
    public String otherString;
    public  byte[] updated_image;
    public ImageView closeButton;
    public ImageView update_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newPictureTaken=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_change_layout);
        location = (EditText) findViewById(R.id.et_location);
        name = (EditText) findViewById(R.id.et_fullName);
        email = (EditText) findViewById(R.id.et_email);
        GoBack = (Button) findViewById(R.id.btnBackToProfile);
        location.setText(parseUser.getString("location"));
        name.setText(parseUser.getString("fullName"));
        otherString = parseUser.getString("location");
        ivPreview = (ImageView) findViewById(R.id.iv_profile_image);
        update_pic = (ImageView) findViewById(R.id.iv_updatePic);
        closeButton = (ImageView)findViewById(R.id.close_button);

        location.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(FileUploadActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String fullName = name.getText().toString();
                if (!fullName.equals("")) {
                    parseUser.put("fullName", fullName);
                }


                String location_text = location.getText().toString();
                if (!location_text.equals("")) {
                    parseUser.put("location", otherString);

                }

                String email_text = email.getText().toString();
                if (!email_text.equals("")) {
                    parseUser.put("username", email_text);
                    parseUser.setEmail( email_text);

                }

                saveUser();


        setParametersOfView();


            }
        });

        update_pic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Locate the image in res > drawable-hdpi
                onLaunchCamera(view);

                // Show a simple toast message

            }
        });
        GoBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                    String fullName = name.getText().toString();
                    if (!fullName.equals("")) {
                        parseUser.put("fullName", fullName);
                    }


                    String location_text = location.getText().toString();
                    if (!location_text.equals("")) {
                        parseUser.put("location", otherString);

                    }

                String email_text = email.getText().toString();
                if (!email_text.equals("")) {
                    parseUser.put("username", email_text);
                    parseUser.setEmail( email_text);

                }

                    saveUser();

            }
        });
        setParametersOfView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    //Following functions are to launch the camera and take a photo
    @NeedsPermission(Manifest.permission.CAMERA	)
    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent takepicintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takepicintent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(takepicintent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public void setParametersOfView() {
        email.setText(parseUser.getString("email"));
        ParseFile postImage = ProfileActivity.parseUser.getParseFile("ImageFile");
        String imageUrl = postImage.getUrl();//live url
        Uri imageUri = Uri.parse(imageUrl);
        Glide.with(FileUploadActivity.this).load(imageUri.toString()).into(ivPreview);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                ivPreview.setImageBitmap(bitmap);

                // Convert it to gibyte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                 updated_image = stream.toByteArray();



                // Create the ParseFile
                file = new ParseFile(parseUser.getString("username"), updated_image);

                file.saveInBackground(new SaveCallback(){
                    @Override
                    public void done(ParseException e1) {
                        if (e1 == null) {
                            Toast.makeText(FileUploadActivity.this, "Image Saved",
                                    Toast.LENGTH_SHORT).show();

                            dunk();

                        } else {
                            Log.d("ParseApplicationError",e1.toString());
                            Toast.makeText(FileUploadActivity.this, "Image NOT Saved",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

            }

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(FileUploadActivity.this, data);
                otherString= place.getName().toString();
//                location.setText("Update your current location - "+otherString);
                location.setText(otherString);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(FileUploadActivity.this, data);
                // TODO: Handle the error.
                Log.i("LoginActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        }
    public void dunk(){
        newPictureTaken=true;
        parseUser.put("ImageFile", file);
    }

    public void saveUser(){
            parseUser.saveInBackground(new SaveCallback(){
                @Override
                public void done(ParseException e1) {
                    if (e1 == null) {
                        Log.d("FOR THE LOVE OF GOD","yass");

                        Intent i = new Intent(FileUploadActivity.this, ProfileActivity.class);
                        //here the old one is getting deplayed

                        setResult(RESULT_OK, i); // set result code and bundle data for response
                        finish(); // closes the activity, pass data to parent
                    } else {
                        Log.d("UMM","THEFISH");
                        Log.d("ParseApplicationError",e1.toString());
                        Toast.makeText(FileUploadActivity.this, "NOT Uploaded",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

}

