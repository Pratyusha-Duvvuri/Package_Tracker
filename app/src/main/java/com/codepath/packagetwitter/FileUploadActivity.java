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

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

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
    public EditText caption;
    public ParseFile file;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.activity_file_upload);

        // Locate the button in activity_file_uploadvity_file_upload.xml
        //button = (Button) findViewById(R.id.uploadbtn);
        btnUploadson = (Button) findViewById(R.id.btnuploadson);
        caption = (EditText) findViewById(R.id.et_imagecaption);
        GoBack = (Button) findViewById(R.id.btnBackToProfile);

        // Capture button clicks
//        button.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View arg0) {
//                // Locate the image in res > drawable-hdpi
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                        R.drawable.ic_like);
//                // Convert it to gibyte
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                // Compress image to lower quality scale 1 - 100
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] image = stream.toByteArray();
//
//                // Create the ParseFile
//                ParseFile file = new ParseFile("androidbegin.png", image);
//                // Upload the image into Parse Cloud
//                file.saveInBackground();
//
//                // Create a New Class called "ImageUpload" in Parse
//                //ParseObject imgupload = new ParseObject("ImageUpload");
//
//                // Create a column named "ImageName" and set the string
//                ProfileActivity.parseUser.put("ImageName", "Like Logo");
//
//                // Create a column named "ImageFile" and insert the image
//                ProfileActivity.parseUser.put("ImageFile", file);
//
//                // Create the class and the columns
//                ProfileActivity.parseUser.saveInBackground();
//
//                // Show a simple toast message
//                Toast.makeText(FileUploadActivity.this, "Image Uploaded",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });


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
                Intent i = new Intent(FileUploadActivity.this, ProfileActivity.class);
                i.putExtra("PARSEUSER", ProfileActivity.parseUser.getObjectId());
                startActivity(i);

            }
        });
        ivPreview = (ImageView) findViewById(R.id.ivPreview);


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
//        takepicintent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

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
//                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
//                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                ivPreview.setImageBitmap(takenImage);
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                ivPreview.setImageBitmap(bitmap);

                // Convert it to gibyte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile(parseUser.getString("username"), image);

                file.saveInBackground(new SaveCallback(){
                    @Override
                    public void done(ParseException e1) {
                        if (e1 == null) {
                            Log.d("FOR THE LOVE OF GOD","yass");
                            Toast.makeText(FileUploadActivity.this, "Image Saved",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("UMM","THEFISH");
                            Log.d("ParseApplicationError",e1.toString());
                            Toast.makeText(FileUploadActivity.this, "NOT Saved",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Create a New Class called "ImageUpload" in Parse
                //ParseObject imgupload = new ParseObject("ImageUpload");

                // Create a column named "ImageName" and set the string
                parseUser.put("ImageName", "Like Logo");

                // Create a column named "ImageFile" and insert the image
                parseUser.put("ImageFile", file);

                parseUser.saveInBackground(new SaveCallback(){
                    @Override
                    public void done(ParseException e1) {
                        if (e1 == null) {
                            Log.d("FOR THE LOVE OF GOD","yass");
                            Toast.makeText(FileUploadActivity.this, "Image Uploaded",
                                    Toast.LENGTH_SHORT).show();
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
        }
    }

