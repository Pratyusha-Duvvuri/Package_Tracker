package com.codepath.packagetwitter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by pratyusha98 on 7/19/17.
 */

@RuntimePermissions

public class UploadPackageImageActivity extends Activity {
    Button button;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public ImageView ivPreview;
    public Button btnUploadson;
    public Button GoBack;
    public EditText caption;
    ParselTransaction trans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.activity_upload_image);
        String parselTransactionID = getIntent().getStringExtra("TRANSACTION");
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(parselTransactionID, new GetCallback<ParselTransaction>() {
            @Override
            public void done(ParselTransaction item, ParseException e) {
                trans = item;
                if (e == null) {}

                 else {
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });

        // Locate the button in activity_file_uploadvity_file_upload.xml
        //button = (Button) findViewById(R.id.uploadbtn);
        btnUploadson = (Button) findViewById(R.id.btnuploadson2);
        GoBack = (Button) findViewById(R.id.btnBackToProfile2);



        btnUploadson.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                // Locate the image in res > drawable-hdpi
                onLaunchCamera(view);

                // Show a simple toast message

            }
        });
        GoBack.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                // Locate the image in res > drawable-hdpi
                Intent i = new Intent(UploadPackageImageActivity.this, ProfileActivity.class);
                setResult(RESULT_OK, i); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent

            }
        });
        ivPreview = (ImageView) findViewById(R.id.ivPreview2);

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
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (takepicintent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(takepicintent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
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
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("photo", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                // Create a New Class called "ImageUpload" in Parse
                //ParseObject imgupload = new ParseObject("ImageUpload");

                // Create a column named "ImageName" and set the string

                // Create a column named "ImageFile" and insert the image
                trans.put("Package_Image", file);

                // Create the s and the columns
                trans.saveInBackground();

                // Show a simple toast message
                Toast.makeText(UploadPackageImageActivity.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();

            }
     }
    }

}