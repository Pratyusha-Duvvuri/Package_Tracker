package com.codepath.packagetwitter.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codepath.packagetwitter.Utils.ForgotPassword_Fragment;
import static com.codepath.packagetwitter.Utils.SignUp_Fragment;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static com.facebook.login.widget.LoginButton loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    public static SignUp_Fragment signUp_fragment;
    public ParseUser parseUser;
    private String TAG = "Google Places API";
    private static Button loginButtonn;
    CallbackManager callbackManager;
    public AccessToken accessToken;
    public AccessTokenTracker accessTokenTracker;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        signUp_fragment = new SignUp_Fragment();
        loginButtonn = (Button) view.findViewById(R.id.loginBtn);


        loginButton = (LoginButton) view.findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        return view;

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
        loginButtonn = (Button) view.findViewById(R.id.loginBtn);

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
        loginButtonn.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
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
                break;
        }

    }

    private void onFbLogin() {
        callbackManager = CallbackManager.Factory.create();

//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(getActivity(),"in LoginResult on success", Toast.LENGTH_LONG).show();
//
//                accessTokenTracker = new AccessTokenTracker() {
//                    @Override
//                    protected void onCurrentAccessTokenChanged(
//                            AccessToken oldAccessToken,
//                            AccessToken currentAccessToken) {
//                        // Set the access token using
//                        // currentAccessToken when it's loaded or set.
//                    }
//                };
//                // If the access token is available already assign it.
////                accessToken = AccessToken.getCurrentAccessToken();
//                accessToken = loginResult.getAccessToken();
//                Profile profile = Profile.getCurrentProfile();
//
//                // Facebook Email address
//                GraphRequest request = GraphRequest.newMeRequest(
//                        accessToken,
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                Log.v("LoginActivity Response ", response.toString());
//                                String Name,FEmail;
//
//                                try {
//                                    Name = object.getString("name");
//
//                                    FEmail = object.getString("email");
//                                    Log.v("Email = ", " " + FEmail);
//                                    Toast.makeText(getApplicationContext(), "Name " + Name, Toast.LENGTH_LONG).show();
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(getActivity(),"in LoginResult on cancel", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Toast.makeText(getActivity(),"in LoginResult on error", Toast.LENGTH_LONG).show();
//            }
//        });


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
               GraphRequest.Callback req = new GraphRequest.Callback() {

                   @Override
                   public void onCompleted( GraphResponse response) {
                       JSONObject object=response.getJSONObject();

                       Log.i("LoginActivity", response.toString());
                       // Get facebook data from login



                       Bundle bFacebookData = getFacebookData(object);
                   }
               };

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                new GraphRequest(loginResult.getAccessToken(),"/me",parameters, HttpMethod.GET,req).executeAsync();


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
                    Log.i("profile_pic", profile_pic + "");
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
            }
            catch(JSONException e) {
                Log.d(TAG,"Error parsing JSON");
            }
            return null;
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
    void getUserFromDatabase(){

        ParseUser.logInInBackground(emailid.getText().toString(),password.getText().toString() , new LogInCallback() {
            @Override
            public void done(ParseUser userrr, ParseException e) {
                if (userrr != null) {


                    parseUser = userrr;
                    if(parseUser.getParseFile("ImageFile")==null) {


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


                    Log.d("ParseApplication","bwahahaha");

                    Log.d("ParseApplication","Logged in successfully");
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

