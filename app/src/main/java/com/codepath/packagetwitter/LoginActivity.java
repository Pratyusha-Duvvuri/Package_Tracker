package com.codepath.packagetwitter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.codepath.packagetwitter.Fragments.Login_Fragment;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static com.codepath.packagetwitter.Fragments.Login_Fragment.signUp_fragment;


public class LoginActivity extends AppCompatActivity {
    public static String[] userListMain;
    public static ArrayList<String> mylist = new ArrayList<String>();
    public static Dictionary dictionary_populate;
    public static Dictionary dictionary_name;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 989;
    Login_Fragment login_fragment;
    public static com.facebook.login.widget.LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M ) {
            checkPermission();
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        AppEventsLogger.activateApp(this);


        setContentView(R.layout.activity_login);
        userListMain = getResources().getStringArray(R.array.users_array);

        //query all the Full names from the database and store it in a string
        getTheRightString();
        login_fragment = new Login_Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameContainer, login_fragment);
        transaction.commit();

    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    public void getTheRightString(){

        dictionary_populate = new Hashtable();
        dictionary_name = new Hashtable();

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> itemList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        String name = itemList.get(i).getString("fullName");
                        String email = itemList.get(i).getString("username");

                        mylist.add(name);
                        dictionary_populate.put(name, email);
                        dictionary_name.put(email,name);
                    }

                    }


                 else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
               signUp_fragment.location.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("LoginActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult( requestCode,  permissions,  grantResults);

        login_fragment.onRequestPermissionsResult( requestCode,  permissions,  grantResults);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        loginButton.setVisibility(View.VISIBLE);
//        overridePendingTransition(R.anim.fadein_animation, R.anim.fadeout_animation);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.fadein_animation, R.anim.fadeout_animation);

//        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

}
