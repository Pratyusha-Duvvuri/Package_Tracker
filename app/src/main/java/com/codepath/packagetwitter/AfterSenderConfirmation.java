package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Fragments.Review_frag;
import com.codepath.packagetwitter.Fragments.Review_frag_two;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.User;
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

import static com.codepath.packagetwitter.LoginActivity.dictionary_name;

public class AfterSenderConfirmation extends AppCompatActivity{

    Receiver receiver;
    Sender sender;
    Mail mail;
    User receiverUser;
    User USER;
    public ParseUser parseUser;


    //News things that receiver has to enter

    @BindView(R.id.tvSSenderName)TextView tvSenderName;
    @BindView(R.id.tvSSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvSStartDate)TextView tvStartDate;
    @BindView(R.id.tvSEndDate)TextView tvEndDate;
    //Didnt put one for packageDetailHeading
    @BindView(R.id.tvRPackageType)TextView type;
    @BindView(R.id.tvRWeight)TextView tvWeight;
    @BindView(R.id.tvRFragile)TextView fragile;
    @BindView(R.id.tvRHeight)TextView tvHeight;
    @BindView(R.id.tvRWidth)TextView tvWidth;
    @BindView(R.id.tvRLength)TextView tvLength;
    @BindView(R.id.tvRDescription)TextView tvDescription;
    @BindView(R.id.ivRImage)ImageView ivPackage;



    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
    @BindView(R.id.etRStartDate)EditText startDate;
    @BindView(R.id.etREndDate)EditText endDate;
    @BindView(R.id.myFABOkay)FloatingActionButton fabOkay;
    @BindView(R.id.myFABReject)FloatingActionButton fabReject;
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
        page1 = (ImageView) findViewById(R.id.page1);
        page2  = (ImageView)findViewById(R.id.page2);

       // ButterKnife.bind(this);
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);

        transaction= ProfileActivity.currentReceive;

        FrameLayout fl = (FrameLayout)findViewById(R.id.flContainer);
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


        //set listener for confirmation

//        fabOkay.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//
//                //TODO - get the dates from the form in receiver
//                transaction.addReceiverInfo(transaction.getSenderStart(), transaction.getSenderEnd(), receiverLocation.getText().toString());
//                transaction.saveEventually();
//                //Call the modal to verify information
//                onVerifyAction();
//
//            }
//        });
//
//        fabReject.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                //set transaction state to seven and delete transaction
//                transaction.setTransactionState(9);
//                transaction.saveInBackground();
//                finishIntent();
//
//            }
//        });
    }
    public void onVerifyAction(){


        //else keep running through stuff and if not found then just ignore

        parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        query.whereEqualTo("transactionState", 7); //courier in limbo state

        //run through the algorithm that takes in two transactions


        query.findInBackground(new FindCallback<ParselTransaction>() {
            public void done(List<ParselTransaction> itemList, ParseException e) {
                if (e == null) {
                    //access parsel transactions here
                    for (int i = 0; i < itemList.size(); i++){
                        //for every parsel transaction
                        ParselTransaction courierTransaction = itemList.get(i);
                        //if it's a match:
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
                            });                            // change state of courier transaction to dead
                            courierTransaction.setTransactionState(8);
                            courierTransaction.saveEventually();
                            // break
                            break;

                        }
                        else{
                            //if it's not a match:
                        }


                    } //for loop ends here

                } else {
                    Log.d("ParseApplicationError",e.toString());
                }
            }
        });



    }

    public void finishIntent(){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("matched", match);
        setResult(RESULT_OK, i); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }

    public void onSetLayout(){

//        Michaun's code from earlier package_confirmation
//        flForm = view.findViewById(R.id.flForm);
//        receiverLocation = view.findViewById(R.id.etReceiverLocation);
//        receiverEndDate = view.findViewById(R.id.etReceiverEnd);
//        llRequest = view.findViewById(R.id.llRequest);
//
//        //initialize framelayout inflater that loads package creation activity
//        LayoutInflater frameInflater = LayoutInflater.from(getContext());
//        //inflates package creation activity
//        View form = frameInflater.inflate(R.layout.activity_package_creation,null, false);
//        //adds package creation activity to the frame layout
//        flForm.addView(form);
//
//        EditText e;
//        e = (EditText)flForm.findViewById(R.id.etWeight);
//        //Get fields from activity package creation


        fragile.setText(""+transaction.getIsFragile());
        type.setText(""+transaction.getMailType());
        tvSenderLocation.setText(""+transaction.getSenderLoc());
        tvStartDate.setText(""+transaction.getSenderStart());
        tvEndDate.setText(""+transaction.getSenderEnd());
        tvWeight.setText(""+transaction.getWeight());
        tvHeight.setText(""+transaction.getVolume());
        tvWidth.setText(""+transaction.getVolume());
        tvLength.setText(""+transaction.getVolume());
        tvDescription.setText(""+transaction.getMailDescription());
        ivPackage.setImageResource(R.drawable.ic_upload);


    }




    public void confirm(View v) throws ParseException {

        transaction.addReceiverInfo(transaction.getSenderStart(), transaction.getSenderEnd());
                transaction.saveEventually();
//                //Call the modal to verify information
               onVerifyAction();
    }
}

//package com.codepath.packagetwitter;
//
//import android.support.v7.app.AppCompatActivity;
//
//public class AfterSenderConfirmation extends AppCompatActivity{
///*
//    Receiver receiver;
//    Sender sender;
//    Mail mail;
//    User receiverUser;
//    User USER;
//    public ParseUser parseUser;
//
//
//    //News things that receiver has to enter
//
//    @BindView(R.id.tvSSenderName)TextView tvSenderName;
//    @BindView(R.id.tvSSenderLocation)TextView tvSenderLocation;
//    @BindView(R.id.tvSStartDate)TextView tvStartDate;
//    @BindView(R.id.tvSEndDate)TextView tvEndDate;
//    //Didnt put one for packageDetailHeading
//    @BindView(R.id.tvRPackageType)TextView type;
//    @BindView(R.id.tvRWeight)TextView tvWeight;
//    @BindView(R.id.tvRFragile)TextView fragile;
//    @BindView(R.id.tvRHeight)TextView tvHeight;
//    @BindView(R.id.tvRWidth)TextView tvWidth;
//    @BindView(R.id.tvRLength)TextView tvLength;
//    @BindView(R.id.tvRDescription)TextView tvDescription;
//    @BindView(R.id.ivRImage)ImageView ivPackage;
//
//    @BindView(R.id.etReceiverLocation)EditText receiverLocation;
//    @BindView(R.id.etRStartDate)EditText startDate;
//    @BindView(R.id.etREndDate)EditText endDate;
//    @BindView(R.id.myFABOkay)FloatingActionButton fabOkay;
//    @BindView(R.id.myFABReject)FloatingActionButton fabReject;
//    ParselTransaction transaction;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //receiverUser= Parcels.unwrap(getIntent().getParcelableExtra("receiver"));
//        //USER= Parcels.unwrap(getIntent().getParcelableExtra("USER"));
//        //receiver = new Receiver(receiverUser);
//        //sender = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
//        //mail = Parcels.unwrap(getIntent().getParcelableExtra("mail"));
////        sender = Sender.getRandomSender(this);
////        mail = Mail.getRandomMail(this);
//        setContentView(R.layout.activity_after_sender_confirmation);
//
//        ButterKnife.bind(this);
//        ParseUser parseUser = ParseUser.getCurrentUser();
//        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
//        // Define our query conditions
//        query.whereEqualTo("receiver", parseUser.getUsername());
//        query.whereEqualTo("transactionState", 0);
//
//        // Execute the find asynchronously
//        query.findInBackground(new FindCallback<ParselTransaction>() {
//            @Override
//            public void done(List<ParselTransaction> issueList, ParseException e) {
//                if (e == null) {
//                    if (issueList.size()>0) {
//                        transaction = issueList.get(0);
//
//                        onSetLayout();
//                    }
//
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//
//
//
//        //set listener for confirmation
//
//        fabOkay.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
////                receiver.setTripStart(startDate.getText().toString());
////                receiver.setTripEnd(endDate.getText().toString());
////                receiver.setLocation((receiverLocation.getText().toString()));
//
//                //TODO - get the dates from the form in receiver
//                transaction.addReceiverInfo(transaction.getSenderStart(), transaction.getSenderEnd(), receiverLocation.getText().toString());
//                transaction.saveEventually();
//                //Call the modal to verify information
//                onVerifyAction();
//
//            }
//        });
//
//        fabReject.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            //set transaction state to seven and delete transaction
//                transaction.setTransactionState(9);
//                transaction.saveInBackground();
//                finishIntent();
//
//            }
//        });
//    }
//    public void onVerifyAction(){
//
//
//        //else keep running through stuff and if not found then just ignore
//
//        parseUser = ParseUser.getCurrentUser();
//        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
//        query.whereEqualTo("transactionState", 7); //courier in limbo state
//
//        //run through the algorithm that takes in two transactions
//
//
//        query.findInBackground(new FindCallback<ParselTransaction>() {
//            public void done(List<ParselTransaction> itemList, ParseException e) {
//                if (e == null) {
//                    //access parsel transactions here
//                    for (int i = 0; i < itemList.size(); i++){
//                        //for every parsel transaction
//                        ParselTransaction courierTransaction = itemList.get(i);
//                        //if it's a match:
//                        if (Algorithm.isPossibleMatch(courierTransaction, transaction)){
//
//                            //find courier's username, his start date, her end date, change state
//                            transaction.addCourierInfo(courierTransaction.getCourier(), courierTransaction.getCourierStart(), courierTransaction.getCourierEnd());
//                            transaction.setTransactionState(2);
//                            transaction.saveEventually();
//                            // change state of courier transaction to dead
//                            courierTransaction.setTransactionState(8);
//                            courierTransaction.saveEventually();
//                            // break
//                            break;
//
//                        }
//                        else{
//                            //if it's not a match:
//                        }
//
//                    } //for loop ends here
//
//                } else {
//                    Log.d("ParseApplicationError",e.toString());
//                }
//            }
//        });
//
//        finishIntent();
//
//
//    }
//
//
//
//    public void finishIntent(){
//        Intent i = new Intent(this, ProfileActivity.class);
//        setResult(RESULT_OK, i); // set result code and bundle data for response
//        finish(); // closes the activity, pass data to parent
//
//    }
//
//    public void onSetLayout(){
//
////        Michaun's code from earlier package_confirmation
////        flForm = view.findViewById(R.id.flForm);
////        receiverLocation = view.findViewById(R.id.etReceiverLocation);
////        receiverEndDate = view.findViewById(R.id.etReceiverEnd);
////        llRequest = view.findViewById(R.id.llRequest);
////
////        //initialize framelayout inflater that loads package creation activity
////        LayoutInflater frameInflater = LayoutInflater.from(getContext());
////        //inflates package creation activity
////        View form = frameInflater.inflate(R.layout.activity_package_creation,null, false);
////        //adds package creation activity to the frame layout
////        flForm.addView(form);
////
////        EditText e;
////        e = (EditText)flForm.findViewById(R.id.etWeight);
////        //Get fields from activity package creation
//
//        tvSenderName.setText(tvSenderName.getText()+transaction.getSender());
//        fragile.setText(fragile.getText()+ "" +transaction.getIsFragile());
//        type.setText(type.getText()+""+transaction.getMailType());
//        tvSenderLocation.setText(tvSenderLocation.getText()+""+transaction.getSenderLoc());
//        tvStartDate.setText(tvStartDate.getText()+""+transaction.getSenderStart());
//        tvEndDate.setText(tvEndDate.getText()+""+transaction.getSenderEnd());
//        tvWeight.setText(tvWeight.getText()+""+transaction.getWeight());
//        tvHeight.setText(tvHeight.getText()+""+transaction.getVolume());
//        tvDescription.setText(tvDescription.getText()+""+transaction.getMailDescription());
//        //ivPackage.setImageResource(R.drawable.ic_upload);
//
//
//    }
//
//*/
//}
