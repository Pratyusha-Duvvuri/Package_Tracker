package com.codepath.packagetwitter.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.packagetwitter.CustomToast;
import com.codepath.packagetwitter.ProfileActivity;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codepath.packagetwitter.LoginActivity.loginButton;
import static com.codepath.packagetwitter.Utils.ForgotPassword_Fragment;
import static com.codepath.packagetwitter.Utils.SignUp_Fragment;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int CODE = 2;
    private static EditText emailid, password;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    public static SignUp_Fragment signUp_fragment;
    public ParseUser parseUser;
    public static Boolean throughFacebook;
    private String TAG = "Google Places API";
    private static Button regular_login_button;
    CallbackManager callbackManager;
    public Bundle bFacebookData;
    public Geocoder geocoder;
    public String locality;
    public static Location user_location;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        throughFacebook = false;
        initViews();
        setListeners();
        signUp_fragment = new SignUp_Fragment();
        regular_login_button = (Button) view.findViewById(R.id.loginBtn);


        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE);

        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                user_location = location;
                            }
                        }
                    });

        }

        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CODE: {

                if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        user_location = location;
                                    }
                                }
                            });
                }


            }

        }
    }


    private String doTheLocationThing(double latitude, double longitude) {

        Toast.makeText(getActivity(),"IN HERE AYY",Toast.LENGTH_SHORT).show();

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            locality = addresses.get(0).getLocality();
            return locality;

        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (com.facebook.login.widget.LoginButton) view.findViewById(R.id.login_button);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        regular_login_button = (Button) view.findViewById(R.id.loginBtn);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews

        XmlResourceParser xrp = getResources().getXml(R.xml.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }

    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        regular_login_button.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is check then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, signUp_fragment, SignUp_Fragment).commit();
                break;

            case R.id.login_button:
                onFbLogin();
                throughFacebook = true;
//                loginButton.setVisibility(View.INVISIBLE);

                break;
        }

    }

    private void onFbLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.Callback req = new GraphRequest.Callback() {

                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject object = response.getJSONObject();

                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login

                        bFacebookData = getFacebookData(object);
                        newUserorCurrentUser();
                    }
                };

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, location{location}");
                new GraphRequest(loginResult.getAccessToken(), "/me", parameters, HttpMethod.GET, req).executeAsync();

            }


            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic.toString());
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d("Facebook Login", "Error parsing JSON");
        }
        return null;
    }

    public void newUser() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();

        ParseUser user = new ParseUser();
// Set core properties

        user.setUsername(bFacebookData.getString("email"));
        user.setPassword("x");
        user.setEmail(bFacebookData.getString("email"));
        user.put("fullName", bFacebookData.getString("first_name") + " " + bFacebookData.getString("last_name"));
        user.put("hasPendingRequests", false);

        user.put("location",doTheLocationThing(user_location.getLatitude(), user_location.getLongitude()));
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    ParseUser userrr = ParseUser.getCurrentUser();

                    parseUser = userrr;
                    if (parseUser.getParseFile("ImageFile") == null) {


                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.error);
                        // Convert it to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        // Compress image to lower quality scale 1 - 100
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] image = stream.toByteArray();

                        ParseFile file = new ParseFile("Default", image);
                        file.saveInBackground();

                        parseUser.put("ImageFile", file);

                        parseUser.saveInBackground();

                    }

                    Log.d("ParseApplication", "Logged in successfully");
                    // Hooray! The user is logged in.
                    Intent i = new Intent(Login_Fragment.this.getContext(), ProfileActivity.class);
                    i.putExtra("PARSEUSER", parseUser.getObjectId());
                    startActivity(i);


                    // Hooray! Let them use the app now.
                } else {


                    e.printStackTrace();                // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });


    }

    public void newUserorCurrentUser() {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", bFacebookData.getString("email"));
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> itemList, ParseException e) {
                if (e == null) {
                    if (itemList.size() == 0) {
                        newUser();
                    } else {
                        returningUser();
                    }

                } else {
                    Log.d("ParseApplicationError", e.toString());
                }
            }
        });
    }


    public void returningUser() {

        ParseUser currentUser = ParseUser.getCurrentUser();
//        currentUser.logOut();

        ParseUser.logInInBackground(bFacebookData.getString("email"), "x", new LogInCallback() {
            @Override
            public void done(ParseUser userrr, ParseException e) {
                if (userrr != null) {


                    parseUser = userrr;
                    if (parseUser.getParseFile("ImageFile") == null) {


                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.error);
                        // Convert it to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        // Compress image to lower quality scale 1 - 100
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] image = stream.toByteArray();

                        ParseFile file = new ParseFile("Default", image);
                        file.saveInBackground();

                        parseUser.put("ImageFile", file);

                        parseUser.saveInBackground();

                    }

                    Log.d("ParseApplication", "Logged in successfully");
                    // Hooray! The user is logged in.
                    Intent i = new Intent(Login_Fragment.this.getContext(), ProfileActivity.class);
                    i.putExtra("PARSEUSER", parseUser.getObjectId());
                    startActivity(i);

                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Log.d("ParseApplication", "Error: " + e.toString());

                }
            }
        });


    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            // Else do login and do your stuff
        else {
            Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();
            //get parse user from database, now the default username is the same as the email id of the person
            getUserFromDatabase();

        }

    }

    void getUserFromDatabase() {

        ParseUser.logInInBackground(emailid.getText().toString(), password.getText().toString(), new LogInCallback() {
            //        ParseUser.logInInBackground(emailid.getText().toString(),"x" , new LogInCallback() {
            @Override
            public void done(ParseUser userrr, ParseException e) {
                if (userrr != null) {


                    parseUser = userrr;
                    if (parseUser.getParseFile("ImageFile") == null) {


                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.error);
                        // Convert it to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        // Compress image to lower quality scale 1 - 100
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] image = stream.toByteArray();

                        ParseFile file = new ParseFile("Default", image);
                        file.saveInBackground();

                        parseUser.put("ImageFile", file);

                        parseUser.saveInBackground();

                    }

                    Log.d("ParseApplication", "Logged in successfully");
                    // Hooray! The user is logged in.
                    Intent i = new Intent(Login_Fragment.this.getContext(), ProfileActivity.class);
                    i.putExtra("PARSEUSER", parseUser.getObjectId());
                    startActivity(i);

                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Log.d("ParseApplication", "Error: " + e.toString());

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}

