package com.codepath.packagetwitter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class TransactionDetailActivity extends AppCompatActivity implements VerticalStepperForm{
    Handler handler;

    public ParseUser parseUser;
    Context context = getBaseContext();
    ParseFile image_file;

    @BindView(R.id.ivPackageImage) ImageView ivPackageImage;
    @BindView(R.id.tvFrom) TextView tvFrom;
    @BindView(R.id.tvTo) TextView tvTo;
    @BindView(R.id.vpCards) ViewPager mViewPager;

    CardFragmentPagerAdapter mfragmentCardAdapter;
    ShadowTransformer mshadowTransformer;
    VerticalStepperFormLayout verticalStepperForm;
    public SharedPreferences sharedPref;
    int transaction_state;
    public Integer parselTransactionState;
    String receiverID;
    String currentUserID;
    String parselTransactionId;

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
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putString("ParselID", parselTransactionId);
        //commits your edits
        editor.commit();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mfragmentCardAdapter = new CardFragmentPagerAdapter(fragmentManager, dpToPixels(2, this),this);
        mViewPager.setAdapter(mfragmentCardAdapter);
        mshadowTransformer = new ShadowTransformer(mViewPager, mfragmentCardAdapter);

        mViewPager.setPageTransformer(false, mshadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselTransactionId, new GetCallback<ParselTransaction>() {
                    public void done(ParselTransaction transaction, ParseException e) {
                        if (e == null) {
                            // item was found

                            tvFrom.setText("From: " + transaction.getSenderLoc());
                            tvTo.setText("To: " + transaction.getReceiverLoc());
                            transaction_state = transaction.getTransactionState();
                            Log.d("WORK", String.valueOf(transaction_state));
                            image_file = transaction.getParseFile("ImageFile");
                            image_file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ivPackageImage.setImageBitmap(bitmap);
                                    }
                                }
                            });


                        }
                    }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TransactionDetailActivity.this, OtherChatActivity.class);
                i.putExtra("ParselTransactionId",parselTransactionId);
                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );
                //materialDesignFAM.close(true);
                startActivity(i);

            }
        });

        String[] mySteps = {"Created", "Accepted", "Matched", "Delivered"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)// It is true by default, so in this case this line is not necessary
                .showVerticalLineWhenStepsAreCollapsed(true) // false by default
                .init();

        ScrollView stepperFormScroll = (ScrollView) verticalStepperForm.findViewById(R.id.steps_scroll);

        LinearLayout stepperFormList = (LinearLayout) stepperFormScroll.findViewById(R.id.content);

        int stepCount = stepperFormList.getChildCount();
        stepperFormList.getChildAt(4).setVisibility(View.GONE);
        for (int i = 0; i < stepCount-2; i++) {
            stepperFormList.getChildAt(i).findViewById(R.id.next_step).setVisibility(View.GONE);

        }

        if (currentUserID.equals(receiverID)) {
            ((Button) stepperFormList.getChildAt(3).findViewById(R.id.next_step)).setText("CONFIRM DELIVERY");
        }
        else
            ((Button) stepperFormList.getChildAt(3).findViewById(R.id.next_step)).setVisibility(View.GONE);
//        verticalStepperForm.setStepAsCompleted(0);
//        verticalStepperForm.setStepAsCompleted(1);
//        verticalStepperForm.goToStep(2, false);


    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
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

    @Override
    public void onStepOpening(int stepNumber) {
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
        else if (stepNumber == 3){
            verticalStepperForm.setStepAsCompleted(0);
            verticalStepperForm.setStepAsCompleted(1);
            verticalStepperForm.setStepAsCompleted(2);
            stepNumber = 4;
            confirmDeliveredState();
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
            stepNumber = 3;
            verticalStepperForm.goToStep(3, false);
        }

    }

    private void confirmDeliveredState(){

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
        confirmationDialog();
        verticalStepperForm.setStepAsCompleted(3);
    }

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

    }

}
