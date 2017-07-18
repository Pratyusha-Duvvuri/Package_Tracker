package com.codepath.packagetwitter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by pratyusha98 on 7/18/17.
 */

public class TestDownloadActivity extends Activity {
    Button button;
    private ProgressDialog progressDialog;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_test_download);
        // Show progress dialog

        // Locate the button in main.xml
        button = (Button) findViewById(R.id.button);

        // Capture button clicks
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                progressDialog = ProgressDialog.show(TestDownloadActivity.this, "",
                        "Downloading Image...", true);

                // Locate the class table named "ImageUpload" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "ImageUpload");

                // Locate the objectId from the class
                query.getInBackground("svjq1hEE48",
                        new GetCallback<ParseObject>() {

                            public void done(ParseObject object,
                                             ParseException e) {
                                // TODO Auto-generated method stub

                                // Locate the column named "ImageName" and set
                                // the string
                                ParseFile fileObject = (ParseFile) object
                                        .get("ImageFile");
                                fileObject
                                        .getDataInBackground(new GetDataCallback() {

                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    Log.d("test",
                                                            "We've got data in data.");
                                                    // Decode the Byte[] into
                                                    // Bitmap
                                                    Bitmap bmp = BitmapFactory
                                                            .decodeByteArray(
                                                                    data, 0,
                                                                    data.length);

                                                    // Get the ImageView from
                                                    // main.xml
                                                    ImageView image = (ImageView) findViewById(R.id.image);

                                                    // Set the Bitmap into the
                                                    // ImageView
                                                    image.setImageBitmap(bmp);

                                                    // Close progress dialog
                                                    progressDialog.dismiss();

                                                } else {
                                                    Log.d("test",
                                                            "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                            }
                        });
            }

        });
    }
}