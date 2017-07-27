package com.codepath.packagetwitter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by pratyusha98 on 7/25/17.
 */

public class PackageCreationPart2Activity extends Activity implements View.OnClickListener {

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
    @BindView(R.id.llType) LinearLayout llType;
    @BindView(R.id.llSize) LinearLayout llSize;
    @BindView(R.id.cvPackageType) CardView cvPackageType;
    @BindView(R.id.tvClothes) TextView tvClothes;
    @BindView(R.id.tvElectronics) TextView tvElectronics;
    @BindView(R.id.tvBooks) TextView tvBooks;
    @BindView(R.id.tvGames) TextView tvGames;
    @BindView(R.id.tvMovies) TextView tvMovies;
    @BindView(R.id.tvFood) TextView tvFood;
    @BindView(R.id.tvHealth) TextView tvHealth;
    @BindView(R.id.tvBeauty) TextView tvBeauty;
    @BindView(R.id.tvOther) TextView tvOther;


    boolean clothes_outline = true;
    int icon;
    int color;
    int defaultTextColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        // Get the view from activity_file_upload.xml_file_upload.xml
        setContentView(R.layout.package_creation_part2);
        ButterKnife.bind(this);
        defaultTextColor = tvClothes.getTextColors().getDefaultColor();

        /*
        ibClothes.setOnClickListener(new View.OnClickListener() {
            boolean clothes_outline = true;
            int icon;
            int color;
            int defaultTextColor = tvClothes.getTextColors().getDefaultColor();
            @Override
            public void onClick(View view) {
                if (clothes_outline) {
                    clothes_outline = false;
                    icon = R.mipmap.ic_clothes_fill;
                    color = Color.BLACK;
                }
                else {
                    clothes_outline = true;
                    icon = R.mipmap.ic_clothes;
                    color = defaultTextColor;
                }
                ibClothes.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                tvClothes.setTextColor(color);
            }
        });
        */

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
    public void onClick(View v) {
        Log.d("WORK-ID", String.valueOf(v.getId()));
        Log.d("WORK-ibClothesID", String.valueOf(v.getId()));
        switch (v.getId()){
            case R.id.ibClothes :
                if (clothes_outline) {
                    clothes_outline = false;
                    icon = R.mipmap.ic_clothes_fill;
                    color = Color.BLACK;
                }
                else {
                    clothes_outline = true;
                    icon = R.mipmap.ic_clothes;
                    color = defaultTextColor;
                }
                ibClothes.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                tvClothes.setTextColor(color);
                break;

            default:
                break;
        }
    }
}
