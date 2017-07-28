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
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;
import static com.codepath.packagetwitter.ProfileActivity.updated_image;
import static com.codepath.packagetwitter.ProfileActivity.updated_tagline;
import static com.codepath.packagetwitter.ProfileActivity.updated_username;

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
    public EditText caption;
    public EditText name;
    public ParseFile file;
    public static Boolean newPictureTaken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newPictureTaken=false;
        super.onCreate(savedInstanceState);
        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.activity_file_upload);
        btnUploadson = (Button) findViewById(R.id.btnuploadson);
        caption = (EditText) findViewById(R.id.et_imagecaption);
        name = (EditText) findViewById(R.id.et_username);
        GoBack = (Button) findViewById(R.id.btnBackToProfile);
        caption.setText(parseUser.getString("tagline"));
        name.setText(parseUser.getString("fullName"));




        btnUploadson.setOnClickListener(new View.OnClickListener() {

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
                        updated_username = fullName;
                    } else
                        updated_username = parseUser.getString("fullName");

                    String tagline = caption.getText().toString();
                    if (!tagline.equals("")) {
                        parseUser.put("tagline", tagline);
                        updated_tagline=tagline;

                    } else {
                        updated_tagline = parseUser.getString("tagline");
                    }



                    doIT();




            }
        });
        ivPreview = (ImageView) findViewById(R.id.ivPreview);
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


        }
    public void dunk(){
        newPictureTaken=true;
        parseUser.put("ImageFile", file);
    }


    public void doIT(){

            parseUser.saveInBackground(new SaveCallback(){
                @Override
                public void done(ParseException e1) {
                    if (e1 == null) {
                        Log.d("FOR THE LOVE OF GOD","yass");

                        Intent i = new Intent(FileUploadActivity.this, ProfileActivity.class);
                        String tagline_is = parseUser.getString("tagline");
                        //here the old one is getting deplayed
                        Toast.makeText(FileUploadActivity.this, tagline_is, Toast.LENGTH_SHORT).show();
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

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

