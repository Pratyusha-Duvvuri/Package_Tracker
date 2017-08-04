package com.codepath.packagetwitter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class TransactionDetailActivity extends AppCompatActivity implements VerticalStepperForm{
    Handler handler;

    public ParseUser parseUser;
    Context context = getBaseContext();
    ParseFile image_file;

    @BindView(R.id.ibPackageUpload) ImageView ivPackageImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvType) TextView tvType;
    @BindView(R.id.ibSize) ImageView ibSize;
    @BindView(R.id.tvSize) TextView tvSize;
    @BindView(R.id.ibType) ImageView ibType;
    @BindView(R.id.tvWeight) TextView tvWeight;
    @BindView(R.id.ivSender) ImageView ivSender;
    @BindView(R.id.ivCourier) ImageView ivCourier;
    @BindView(R.id.ivReceiver) ImageView ivReceiver;
    @BindView(R.id.tvSender) TextView tvSender;
    @BindView(R.id.tvCourier) TextView tvCourier;
    @BindView(R.id.tvReceiver) TextView tvReceiver;
    @BindView(R.id.tvFrom) TextView tvFrom;

    @BindView(R.id.tvTo) TextView tvTo;

    @BindView(R.id.tvCourierTitle) TextView tvCourierTitle;







//    @BindView(R.id.vpCards) ViewPager mViewPager;

    CardFragmentPagerAdapter mfragmentCardAdapter;
    ShadowTransformer mshadowTransformer;
    VerticalStepperFormLayout verticalStepperForm;
    public SharedPreferences sharedPref;
    int transaction_state;
    public Integer parselTransactionState;
    String receiverID;
    String currentUserID;
    String parselTransactionId;
    Button confirm_button;
    boolean alreadyExecuted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_activity);
        final com.github.clans.fab.FloatingActionButton chatButton;
        ButterKnife.bind(this);

        chatButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_chat);
        parselTransactionId = getIntent().getStringExtra("ParselTransactionId");
        parselTransactionState = getIntent().getIntExtra("ParselTransactionState",0);
        receiverID = getIntent().getStringExtra("ParselTransactionReceiver");
        currentUserID = getIntent().getStringExtra("ParselTransactionUser");

        //To pass parsel ID to view pager
        // Create object of SharedPreferences.
//        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        //now get Editor
//        SharedPreferences.Editor editor = sharedPref.edit();
//        //put your value
//        editor.putString("ParselID", parselTransactionId);
//        //commits your edits
//        editor.commit();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        mfragmentCardAdapter = new CardFragmentPagerAdapter(fragmentManager, dpToPixels(2, this),this);
//        mViewPager.setAdapter(mfragmentCardAdapter);
//        mshadowTransformer = new ShadowTransformer(mViewPager, mfragmentCardAdapter);
//
//        mViewPager.setPageTransformer(false, mshadowTransformer);
//        mViewPager.setOffscreenPageLimit(3);

        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselTransactionId, new GetCallback<ParselTransaction>() {
            public void done(ParselTransaction transaction, ParseException e) {
                if (e == null) {
                    // item was found
                    String title = transaction.getString("title").toString();
                    tvTitle.setText(title);
                    tvFrom.setText(transaction.getSenderLoc());
                    tvTo.setText(transaction.getReceiverLoc());
                    String description = transaction.getMailDescription().toString();
                    tvDescription.setText(description);
                    setFragile(transaction);
                    tvType.setText(transaction.getMailType());
                    TypedArray sizes = getResources().obtainTypedArray(R.array.sizes);
                    TypedArray sizePics = getResources().obtainTypedArray(R.array.size_pics);
                    if (transaction.getTransactionState() >= 2){
                        tvCourierTitle.setVisibility(View.VISIBLE);
                    }
                    //sets up sender:
                    setUpPerson(transaction.getSender(),ivSender,tvSender);
                    //sets up courier:
                    setUpPerson(transaction.getCourier(),ivCourier,tvCourier);
                    //sets up receiver:
                    setUpPerson(transaction.getReceiver(),ivReceiver,tvReceiver);

                    ibSize.setImageDrawable(sizePics.getDrawable(transaction.getVolume()));
                    tvSize.setText(sizes.getString(transaction.getVolume()));
                    tvWeight.setText(String.valueOf(transaction.getWeight()));

                    ibType.setImageResource(getTypeId(transaction.getMailType()));
//                            tvFrom.setText("From: " + transaction.getSenderLoc());
//                            tvTo.setText("To: " + transaction.getReceiverLoc());
                    transaction_state = transaction.getTransactionState();
                    Log.d("WORK", String.valueOf(transaction_state));
                    image_file = transaction.getParseFile("ImageFile");
                    image_file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                ivPackageImage.setImageBitmap(bitmap);
                                ivPackageImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        }
                    });



                }
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TransactionDetailActivity.this, ChatActivity.class);
                i.putExtra("ParselTransactionId",parselTransactionId);
                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );
                //materialDesignFAM.close(true);
                startActivity(i);

            }
        });

        String[] mySteps = {"Create", "Accept", "Match", "Deliver"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)// It is true by default, so in this case this line is not necessary
                .showVerticalLineWhenStepsAreCollapsed(false) // false by default
                .init();

        ScrollView stepperFormScroll = (ScrollView) verticalStepperForm.findViewById(R.id.steps_scroll);

        LinearLayout stepperFormList = (LinearLayout) stepperFormScroll.findViewById(R.id.content);

        int stepCount = stepperFormList.getChildCount();
        stepperFormList.getChildAt(4).setVisibility(View.GONE);
        for (int i = 0; i < stepCount-2; i++) {
            stepperFormList.getChildAt(i).findViewById(R.id.next_step).setVisibility(View.GONE);

        }

        confirm_button = ((Button) stepperFormList.getChildAt(3).findViewById(R.id.next_step));

        if (currentUserID.equals(receiverID) && parselTransactionState == 2) {
            confirm_button.setText("CONFIRM DELIVERY");
        }
        else
            confirm_button.setVisibility(View.GONE);

        if (parselTransactionState == 4){
            alreadyExecuted = true;
            switch(verticalStepperForm.getActiveStepNumber()){
                case 0:
                    verticalStepperForm.setStepAsCompleted(1);
                    verticalStepperForm.setStepAsCompleted(2);
                    verticalStepperForm.setStepAsCompleted(3);
                    verticalStepperForm.goToStep(1, false);
                    verticalStepperForm.setStepAsCompleted(0);
                    break;
                case 1:
                    verticalStepperForm.setStepAsCompleted(0);
                    verticalStepperForm.setStepAsCompleted(2);
                    verticalStepperForm.setStepAsCompleted(3);
                    verticalStepperForm.goToStep(2, false);
                    verticalStepperForm.setStepAsCompleted(1);
                    break;
                case 2:
                    verticalStepperForm.setStepAsCompleted(0);
                    verticalStepperForm.setStepAsCompleted(1);
                    verticalStepperForm.setStepAsCompleted(3);
                    verticalStepperForm.goToStep(3, false);
                    verticalStepperForm.setStepAsCompleted(2);
                    break;
                case 3:
                    verticalStepperForm.setStepAsCompleted(0);
                    verticalStepperForm.setStepAsCompleted(1);
                    verticalStepperForm.setStepAsCompleted(2);
                    verticalStepperForm.goToStep(4, false);
                    verticalStepperForm.setStepAsCompleted(3);
                    break;
                default:
                    break;

            }
//            verticalStepperForm.setStepAsCompleted(0);
//            verticalStepperForm.setStepAsCompleted(1);
//            verticalStepperForm.setStepAsCompleted(2);
//            verticalStepperForm.setStepAsCompleted(3);
            alreadyExecuted = true;
        }
        else{
            onStepOpening(0);
        }
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verticalStepperForm.setStepAsCompleted(0);
                verticalStepperForm.setStepAsCompleted(1);
                verticalStepperForm.setStepAsCompleted(2);

                if (verticalStepperForm.getActiveStepNumber() != 3){
                    //verticalStepperForm.goToStep(4, false);
                    verticalStepperForm.setStepAsCompleted(3);
                }
                sendData();
                confirm_button.setVisibility(View.GONE);

            }
        });

    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public void setUpPerson(String username, final ImageView ivPerson, final TextView name){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username); // finds person w that username


        query.findInBackground(new FindCallback<ParseUser>() {

            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size()>0) {
                        // The query was successful.
                        name.setVisibility(View.VISIBLE);
                        name.setText(objects.get(0).getString("fullName"));

                        //image
                        ParseFile postImage = objects.get(0).getParseFile("ImageFile");
                        if (postImage != null) {
                            String imageUrl = postImage.getUrl();//live url

                            Uri imageUri = Uri.parse(imageUrl);
                            Glide.with(TransactionDetailActivity.this).load(imageUri.toString()).into(ivPerson);
                        } else {
                            Glide.with(TransactionDetailActivity.this).load("http://i.imgur.com/zuG2bGQ.jpg").into(ivPerson);

                        }
                    }
                }




                else {
                    // Something went wrong.
                    Log.d("ParseApplicationError","tf");
                    e.printStackTrace();
                }
            }
        });

    }


    public void setFragile(ParselTransaction parselTransaction){
        TextView frag = (TextView) findViewById(R.id.tvFragile);

        if(parselTransaction.getIsFragile())frag.setText("Yes");
        else {frag.setText("No");}
    }
    @Override
    public View createStepContentView(int stepNumber) {

        View view = null;
        switch (stepNumber) {
            case 0:
                view = createCreatedStep();
                break;
            case 1:
                view = createAcceptedStep();
                break;
            case 2:
                view = createMatchedStep();
                break;
            case 3:
                view = createDeliveredStep();
        }
        return view;
    }

    private View createDeliveredStep() {
        TextView delivered = new TextView(this);
        delivered.setSingleLine(true);
        delivered.setText("Awaiting Confirmation of Package Delivery");
        return delivered;
    }

    private View createMatchedStep() {
        TextView matched = new TextView(this);
        matched.setSingleLine(true);
        matched.setText("Awaiting A Courrier To Be Matched");
        return matched;
    }

    private View createAcceptedStep() {
        TextView accepted = new TextView(this);
        accepted.setSingleLine(true);
        accepted.setText("Awaiting Recipient To Accept The Request");
        return accepted;
    }

    private View createCreatedStep() {
        TextView created = new TextView(this);
        created.setSingleLine(true);
        created.setText("Package Has Been Created");
        return created;
    }

    public final void onStepOpening(int stepNumber) {

        if (parselTransactionState == 4)
            alreadyExecuted = true;
        if (!alreadyExecuted){
            switch (stepNumber){
                case 0:
                    checkTransactionState(0);
                    break;
                case 1:
                    checkTransactionState(1);
                    break;
                case 2:
                    checkTransactionState(2);
                    break;
                case 3:
                    checkTransactionState(3);
                    break;
                default:
                    break;
            }
        }
    }

    private int checkTransactionState(int stepNumber){

        if(parselTransactionState == 0){
            verticalStepperForm.setStepAsCompleted(0);
            stepNumber = 1;
            verticalStepperForm.goToStep(1, false);
        }
        else if (parselTransactionState == 1){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            stepNumber = 2;
            verticalStepperForm.goToStep(2, false);
        }
        else if (parselTransactionState == 2 && stepNumber == 3){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepAsCompleted(2);
            stepNumber = 4;
        }
        else if (parselTransactionState == 2){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepAsCompleted(2);
            stepNumber = 3;
            verticalStepperForm.goToStep(3, false);
        }
        else if (parselTransactionState == 4){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepAsCompleted(2);
            verticalStepperForm.setStepAsCompleted(3);
            stepNumber = 4;
        }
        alreadyExecuted = true;
        return stepNumber;
    }

    /*
    private void confirmDeliveredState(){
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
                // First try to find from the cache and only then go to network
                transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
                // Execute the query to find the object with ID
                transactionQuery.getInBackground(parselTransactionId, new GetCallback<ParselTransaction>() {
                    public void done(ParselTransaction transaction, ParseException e) {
                        if (e == null) {
                            // item was found
                            transaction.setTransactionState(4);
                            transaction.saveEventually();
                        }
                    }
                });
                verticalStepperForm.setStepAsCompleted(3);
                confirmationDialog();
            }
        });
    }
    */

    private void confirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDetailActivity.this);
        builder.setMessage("Delivery Of Package Confirmed");    //set message
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //when click on DELETE
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}
        }).show();
    }

    private void checkMatchedState() {

        if (parselTransactionState >= 2) {
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepAsCompleted(2);
        }
    }

    private void checkAcceptedState() {
        if (parselTransactionState >= 1){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);

        }
    }

    private void checkCreatedState() {
        if (parselTransactionState >= 0)
            verticalStepperForm.setStepAsCompleted(0);

    }


    @Override
    public void sendData() {
        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselTransactionId, new GetCallback<ParselTransaction>() {
            public void done(ParselTransaction transaction, ParseException e) {
                if (e == null) {
                    // item was found
                    transaction.setTransactionState(4);
                    transaction.saveEventually();
                }
            }
        });
        verticalStepperForm.setStepAsCompleted(3);
        confirmationDialog();
    }

    public int getTypeId(String type){

        if (type.equals( "Clothes")) {
            return  R.mipmap.ic_clothes_fill;
        }
        else if (type.equals("Electronics")) {
            return R.mipmap.ic_electronics_filled;
        }

        else if (type.equals( "Books")) {
            return R.mipmap.ic_books_filled;
        }
        else if (type.equals( "Games")) {
            return R.mipmap.ic_games_filled;
        }
        else if (type.equals( "Movies")) {
            return R.mipmap.ic_movies_filled;
        }
        else if (type.equals( "Food") ){
            return R.mipmap.ic_food_filled;
        }
        else if (type.equals( "Health")) {
            return R.mipmap.ic_health_filled;
        }
        else if (type.equals(  "Beauty")) {
            return  R.mipmap.ic_beauty_filled;
        }
        else {
            return R.mipmap.ic_other_filled;
        }


    }



}