package com.codepath.packagetwitter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by pratyusha98 on 7/25/17.
 */

public class PackageCreationPart2Activity extends Activity {

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
                if (e == null) {
                } else {
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });
    }

}
