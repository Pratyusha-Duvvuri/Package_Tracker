package com.codepath.packagetwitter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.codepath.packagetwitter.Fragments.Review_frag;
import com.codepath.packagetwitter.Fragments.Review_frag_three;
import com.codepath.packagetwitter.Fragments.Review_frag_two;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

public class ReviewActivity extends AppCompatActivity {

    String startAddress;
    String endAddress;

    String receiver;

    String startDate;
    String endDate;
    byte[] image;
    String title;
    String description;
    int volume;
    Double weight;
    String type;
    Boolean fragile;

    int page = 0;
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    Review_frag fragment_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FrameLayout fl = (FrameLayout)findViewById(R.id.flContainer);

        startAddress = getIntent().getStringExtra("startAddress");
        endAddress = getIntent().getStringExtra("endAddress");
        receiver = getIntent().getStringExtra("receiver");
        startDate   = getIntent().getStringExtra("startDate");
        endDate  = getIntent().getStringExtra("endDate");
        image  = getIntent().getByteArrayExtra("image");
        title  = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        volume = getIntent().getIntExtra("volume",-1);
        weight =   getIntent().getDoubleExtra("weight",-1);
        type = getIntent().getStringExtra("type");
        fragile = getIntent().getBooleanExtra("fragile",false);

        if (startAddress == null) {

        startAddress = "SEA";
        endAddress = "NYC";
        receiver = "booo@gmail.com";
        startDate = "05/02/17";
        endDate = "05/08/17";
         Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.arrival);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = stream.toByteArray();
        title = "Clothes and a Phone Case";
        description = "none of these things in the middle of the Amazon";
        volume = 5;
        weight = 20.4;
        type = "clothes";
        fragile = false;
        }


        fl.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if(page ==0){
                    page = 1;

                    ft = getSupportFragmentManager().beginTransaction();
                Review_frag_two fragment = Review_frag_two.newInstance(image,title,description);
                ft.replace(R.id.flContainer, fragment);
                ft.commit();
                }
                else{
                    page =2;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag_three fragment = Review_frag_three.newInstance(type,volume,weight,fragile);
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                }

            }
            @Override
            public void onSwipeLeft() {
                if(page ==2){
                    page =1;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag_two fragment =  Review_frag_two.newInstance(image,title,description);

                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();

                }
                else {
                    page = 0;
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag fragment =  Review_frag.newInstance(startAddress,endAddress,receiver,startDate,endDate);

                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();

                }
            }
        });



        fragment_one =  Review_frag.newInstance(startAddress,endAddress,receiver,startDate,endDate);

        ft.replace(R.id.flContainer,fragment_one);

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
        Date startDay = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
        Date endDay = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
        final ParselTransaction transaction = new ParselTransaction(receiver,parseUser.getUsername(),startAddress,startDay,endDay,type, description,weight,volume);
        transaction.saveEventually(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {

                i.putExtra("transID",transaction.getObjectId());
                startActivity(i);
            }
        });









    }




}
