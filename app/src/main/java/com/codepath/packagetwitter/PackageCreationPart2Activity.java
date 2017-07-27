package com.codepath.packagetwitter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import butterknife.BindView;

/**
 * Created by pratyusha98 on 7/25/17.
 */

public class PackageCreationPart2Activity extends Activity {

    ParselTransaction trans;
    @BindView(R.id.etTitle) EditText etTitle;
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.etWeight) EditText etWeight;
    @BindView(R.id.rgFragile) RadioGroup rgFragile;
    @BindView(R.id.rbYes) RadioButton rbYes;
    @BindView(R.id.rbNo) RadioButton rbNo;
    @BindView(R.id.ibClothes) ImageButton ibClothes;
    @BindView(R.id.ibElectronics) ImageButton ibElectronics;
    @BindView(R.id.ibBooks) ImageButton ibBooks;
    @BindView(R.id.ibGames) ImageButton ibGames;
    @BindView(R.id.ibMovies) ImageButton ibMovies;
    @BindView(R.id.ibFood) ImageButton ibFood;
    @BindView(R.id.ibHealth) ImageButton ibHealth;
    @BindView(R.id.ibBeauty) ImageButton ibBeauty;
    @BindView(R.id.ibOther) ImageButton ibOther;
    @BindView(R.id.ibKey) ImageButton ibKey;
    @BindView(R.id.ibPhone) ImageButton ibPhone;
    @BindView(R.id.ibBookSized) ImageButton ibBookSized;
    @BindView(R.id.ibFishBowl) ImageButton ibFishBowl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up actionbar

        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.package_creation_part2);
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


                }

                else {
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });
    }

}
