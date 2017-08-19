package com.codepath.packagetwitter.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.packagetwitter.CustomToast;
import com.codepath.packagetwitter.LoginActivity;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.Utils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.codepath.packagetwitter.Utils.Login_Fragment;


public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber,
            password, confirmPassword;
    public static TextView location;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private static FragmentManager fragmentManager;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 989;

    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {

        fragmentManager = getActivity().getSupportFragmentManager();
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (TextView) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.xml.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
//        loginButton2.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
        location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.location:

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

                break;

            case R.id.already_user:


                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frameContainer, new Login_Fragment(), Login_Fragment).commit();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else
            Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
                    .show();

        createNewParseUser();
        Intent i = new Intent(getContext(), LoginActivity.class);
//        userr = User.getRandomUser(getContext());
//        userr.hasPendingRequests= false;
//        i.putExtra("USER", Parcels.wrap(userr));
        startActivity(i);

    }

    void createNewParseUser() {
        ParseUser user = new ParseUser();
// Set core properties

        user.setUsername(emailId.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(emailId.getText().toString());
// Set custom properties
        user.put("tagline", "Tagline");
        user.put("location", location.getText().toString());
        user.put("mobileNumber", mobileNumber.getText().toString());
        user.put("fullName",fullName.getText().toString());

        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();
// Invoke signUpInBackground
    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                Log.d("ParseApplication", "Sign-UP succesful");
                new CustomToast().Show_Toast(getActivity(), view,
                        "SIGNNN.");
                // Hooray! Let them use the app now.
            } else {


                e.printStackTrace();                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
            }
        }
    });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                location.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("LoginActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }


}


//For future use


//    // Create a gravatar image based on the hash value obtained from userId
//    private static String getProfileUrl(final String userId) {
//        String hex = "";
//        try {
//            final MessageDigest digest = MessageDigest.getInstance("MD5");
//            final byte[] hash = digest.digest(userId.getBytes());
//            final BigInteger bigInt = new BigInteger(hash);
//            hex = bigInt.abs().toString(16);
////            hex = (""+ProfileActivity.parseUser.getObjectId()).toString(16);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
//    }