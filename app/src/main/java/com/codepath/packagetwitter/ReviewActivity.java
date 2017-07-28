package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codepath.packagetwitter.Fragments.Review_frag;
import com.codepath.packagetwitter.Fragments.Review_frag_three;
import com.codepath.packagetwitter.Fragments.Review_frag_two;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

public class ReviewActivity extends AppCompatActivity {

    String startAddress;
    String endAddress;
    ParseFile file;

    String receiver;

    String startDate;
    String endDate;
    byte[] image;
    String title;
    String description;
    int volume;
    String volume_string;
    Double weight;
    String type;
    Boolean fragile;
    public ParselTransaction transaction;
    int page = 0;
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    Review_frag fragment_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FrameLayout fl = (FrameLayout)findViewById(R.id.flContainer);

        startAddress = getIntent().getStringExtra("senderLocation");
        endAddress = getIntent().getStringExtra("receiverLocation");
        receiver = getIntent().getStringExtra("receiverHandle");
        startDate   = getIntent().getStringExtra("senderStartDate");
        endDate  = getIntent().getStringExtra("senderEndDate");
//        image  = getIntent().getByteArrayExtra("image");
        title  = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        volume = getIntent().getIntExtra("volume",-1);
        volume_string = getIntent().getStringExtra("volume_string");
        weight =   getIntent().getDoubleExtra("weight",-1);
        type = getIntent().getStringExtra("type");
        fragile = getIntent().getBooleanExtra("fragile",false);
        image = getIntent().getByteArrayExtra("package_image");



        fl.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {

                if(page == 0) {

                    page = 1;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag_two fragment =  Review_frag_two.newInstance(image,title,description);
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                }


                else {
                    page = 2;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag_three fragment = Review_frag_three.newInstance(type, volume, weight, fragile, volume_string);
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();

                }

            }

            @Override
            public void onSwipeRight() {
                if (page == 2) {

                    page = 1;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag_two fragment = Review_frag_two.newInstance(image, title, description);
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                } else {
                    page = 0;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag fragment =  Review_frag.newInstance(startAddress,endAddress,receiver,startDate,endDate);
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                }
            }
        });

        Review_frag ffrag=  Review_frag.newInstance(startAddress,endAddress,receiver,startDate,endDate);
        ft.replace(R.id.flContainer,ffrag);
        ft.commit();

    }

    public void cancel(View v){
        Intent i = new Intent(ReviewActivity.this, ProfileActivity.class ) ;
        i.putExtra("PARSEUSER", ParseUser.getCurrentUser().getObjectId());

        startActivity(i);

    }

    public void confirm(View v) throws ParseException {

        final Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("PARSEUSER", parseUser.getObjectId());

        i.putExtra("newPackage", true);
        Date startDay = new SimpleDateFormat("MM/dd/yy").parse(startDate);
        Date endDay = new SimpleDateFormat("MM/dd/yy").parse(endDate);
        transaction = new ParselTransaction(receiver,parseUser.getUsername(),startAddress,endAddress,startDay,endDay,type, description,weight,volume,fragile,title);


        file = new ParseFile("notimp", image);
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(ReviewActivity.this, "Image Saved",
                            Toast.LENGTH_SHORT).show();
                    dunk();

                } else {
                    Log.d("ParseApplicationError",e.toString());
                    Toast.makeText(ReviewActivity.this, "Image NOT Saved",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void dunk(){
        transaction.put("ImageFile", file);
        transaction.saveEventually(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {

                onSubmit();

            }
        });
    }

    public void onSubmit() {
//  return to part 1
        Intent data = new Intent();
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

}
