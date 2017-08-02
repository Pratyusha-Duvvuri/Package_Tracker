package com.codepath.packagetwitter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by michaunp on 7/31/17.
 */

public class TransactionDetailTest extends AppCompatActivity implements VerticalStepperForm{

    ViewPager mViewPager;

    CardFragmentPagerAdapter mfragmentCardAdapter;
    ShadowTransformer mshadowTransformer;
    VerticalStepperFormLayout verticalStepperForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_activity);
        mViewPager = (ViewPager) findViewById(R.id.vpCards);

        mfragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this),this);
        mshadowTransformer = new ShadowTransformer(mViewPager, mfragmentCardAdapter);

        mViewPager.setAdapter(mfragmentCardAdapter);
        mViewPager.setPageTransformer(false, mshadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        String[] mySteps = {"Created", "Accepted", "Matched"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false) // It is true by default, so in this case this line is not necessary
                .init();
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
            //case 3:
                //view = createDeliveredStep();
        }
        return view;
    }

    //private View createDeliveredStep() {

   // }

    private View createMatchedStep() {
        TextView matched = new TextView(this);
        matched.setSingleLine(true);
        matched.setText("Courrier Has Been Matched To The Transaction");
        return matched;
    }

    private View createAcceptedStep() {
        TextView accepted = new TextView(this);
        accepted.setSingleLine(true);
        accepted.setText("Recipient Has Accepted The Request");
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

    }

    @Override
    public void sendData() {

    }
}
