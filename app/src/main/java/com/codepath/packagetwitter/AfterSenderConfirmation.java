package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.codepath.packagetwitter.Fragments.Review_frag;
import com.codepath.packagetwitter.Fragments.Review_frag_two;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.LoginActivity.dictionary_name;

public class AfterSenderConfirmation extends AppCompatActivity{

    public ParseUser parseUser;
    Boolean accepted = true;

    @BindView(R.id.confirm)Button confirm;
    ImageView page1;
    ImageView page2;
    ParselTransaction transaction;
    View view1;
    View view2;
    Boolean match = false;
    int page = 0;
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_receiver, null);
        view2 = getLayoutInflater().inflate(R.layout.activity_after_sender_confirmation, null);

        setContentView(view1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_white_plane);
        getSupportActionBar().setTitle("Review Information");
        toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
        page1 = (ImageView) findViewById(R.id.page1);
        page2  = (ImageView)findViewById(R.id.page2);

        ButterKnife.bind(this);
        transaction= ProfileActivity.currentReceive;
        confirm.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           try {
                                               confirm(view);
                                           } catch (ParseException e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   }
        );

        FrameLayout fl = (FrameLayout)findViewById(R.id.flContainer);

        //swipe between two fragments based on user gestures
        fl.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeRight() {


                    page = 0;
                    DateFormat df = new SimpleDateFormat("MM/dd/yy");

                    String startDay  = df.format(transaction.getSenderStart());
                    String endDay = df.format(transaction.getSenderEnd());
                    ft = getSupportFragmentManager().beginTransaction();
                    Review_frag fragment =  Review_frag.newInstance(transaction.getSenderLoc(),
                            transaction.getReceiverLoc(), (String) dictionary_name.get(transaction.getSender()),startDay,endDay);

                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                page2.setImageDrawable(getDrawable(R.drawable.dot_filled));
                page1.setImageDrawable(getDrawable(R.drawable.dot_unfilled));
            }

            @Override
            public void onSwipeLeft() {

                    page = 1;

                    ft = getSupportFragmentManager().beginTransaction();
                    ParseFile postImage = transaction.getParseFile("ImageFile");
                    String imageUrl = "";
                    if(postImage!=null) {
                        imageUrl = postImage.getUrl();//live url
                    }
                    Review_frag_two fragment = Review_frag_two.newInstance(imageUrl,
                            transaction.getString("title"),transaction.getMailDescription(), transaction.getMailType(),
                            transaction.getVolume(), transaction.getWeight(), transaction.getIsFragile());
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                    page2.setImageDrawable(getDrawable(R.drawable.dot_unfilled));
                    page1.setImageDrawable(getDrawable(R.drawable.dot_filled));

            }
        });

        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        String startDay  = df.format(transaction.getSenderStart());
        String endDay = df.format(transaction.getSenderEnd());
        Review_frag fragment =  Review_frag.newInstance(transaction.getSenderLoc(),
                transaction.getReceiverLoc(), (String) dictionary_name.get(transaction.getSender()),startDay,endDay);
        ft.replace(R.id.flContainer,fragment);
        ft.commit();
    }

    public void onVerifyAction(){
        parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 7); //courier in limbo state

        //run through the algorithm, which takes in two transactions
        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {
                if (e == null) {
                    //access parsel transactions here
                    for (int i = 0; i < itemList.size(); i++){
                        //for every parsel transaction
                        ParselTransaction courierTransaction = itemList.get(i);
                        //if it's a match
                        if (Algorithm.isPossibleMatch(courierTransaction, transaction)){
                            match = true;
                            //find courier's username, his start date, her end date, change state
                            transaction.addCourierInfo(courierTransaction.getCourier(), courierTransaction.getCourierStart(), courierTransaction.getCourierEnd());
                            transaction.setTransactionState(2);
                            transaction.saveEventually(new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
                                    if (e == null) {
                                        try {
                                            parseUser = ParseUser.getCurrentUser().fetch();
                                        } catch (com.parse.ParseException e1) {
                                        }
                                        finishIntent();
                                    }
                                    else{
                                        Log.e("Saving Image: ", "ParseSaveFileError: " + e.toString());
                                    }
                                }
                            });
                            // change state of courier transaction to dead
                            courierTransaction.setTransactionState(8);
                            courierTransaction.saveEventually();
                            break;
                        }
                        else{
                            //no match was found, so new entry into database as awaiting match
                            transaction.setTransactionState(1);
                        }
                    } //for loop ends here
                    finishIntent();
                } else {
                    Log.d("ParseApplicationError",e.toString());
                }
            }
        });


    }

    public void finishIntent(){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("matched", match);
        i.putExtra("accepted", accepted);
        i.putExtra("transaction", transaction.getObjectId());
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }



    public void confirm(View v) throws ParseException {

        transaction.addReceiverInfo(transaction.getSenderStart(), transaction.getSenderEnd());
                transaction.saveEventually();
                //Call the modal to verify information
               onVerifyAction();
    }

    public void cancel(View v) throws ParseException {

        transaction.setTransactionState(9);
        transaction.saveEventually();
        accepted = false;
                //Call the modal to verify information
        onVerifyAction();
    }
}