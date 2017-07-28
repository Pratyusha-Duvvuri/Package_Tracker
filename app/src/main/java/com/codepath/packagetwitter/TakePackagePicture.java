package com.codepath.packagetwitter;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.User;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;

import permissions.dispatcher.NeedsPermission;

/**
 * Created by pratyusha98 on 7/28/17.
 */

public class TakePackagePicture extends AppCompatActivity {
    Button button;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public ImageView ivPreview;
    public Button btnUploadson;
    public Button GoBack;
    public EditText caption;
    public User u;



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
        u = Parcels.unwrap(getIntent().getParcelableExtra("USER"));


        btnUploadson.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Locate the image in res > drawable-hdpi
                onLaunchCamera(view);

                // Show a simple toast message

            }
        });
        GoBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Locate the image in res > drawable-hdpi
                Intent i = new Intent(TakePackagePicture.this, ProfileActivity.class);
//                startActivity(intent);
//                User u = User.getRandomUser(getContext());
                u.hasPendingRequests= true;
                i.putExtra("USER", Parcels.wrap(u));
                i.putExtra("PARSEUSER", ProfileActivity.parseUser.getObjectId());
                startActivity(i);

            }
        });
        ivPreview = (ImageView) findViewById(R.id.ivPreview);


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


                // Show a simple toast message
                Toast.makeText(TakePackagePicture.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();

                //return the byte array to the parent activity

            }
        }
    }


}
