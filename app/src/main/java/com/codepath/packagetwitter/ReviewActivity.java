package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.codepath.packagetwitter.Fragments.Review_frag;
import com.codepath.packagetwitter.Fragments.Review_frag_two;

public class ReviewActivity extends AppCompatActivity {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FrameLayout fl = (FrameLayout)findViewById(R.id.flContainer);



        fl.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                ft = getSupportFragmentManager().beginTransaction();
                Review_frag_two fragment = new Review_frag_two();
                ft.replace(R.id.flContainer, fragment);
                ft.commit();

                }
            @Override
            public void onSwipeLeft() {
                ft = getSupportFragmentManager().beginTransaction();
                Review_frag fragment = new Review_frag();
                ft.replace(R.id.flContainer, fragment);
                ft.commit();

            }
        });


        Review_frag fragment =  new Review_frag();


        ft.replace(R.id.flContainer,fragment);

        ft.commit();
    }


    public void cancel(){


    }

    public void confirm(){


    }

    public void swipe(){

    }
}
