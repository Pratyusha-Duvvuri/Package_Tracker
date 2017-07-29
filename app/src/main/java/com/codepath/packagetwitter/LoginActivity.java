package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.packagetwitter.Fragments.Login_Fragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    public static String[] userListMain;
    public static ArrayList<String> mylist = new ArrayList<String>();
    public static Dictionary dictionary_populate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userListMain = getResources().getStringArray(R.array.users_array);

        //query all the Full names from the database and store it in a string
        getTheRightString();

        Login_Fragment login_fragment = new Login_Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameContainer, login_fragment);
        transaction.commit();

    }

    public void getTheRightString(){

         dictionary_populate = new Hashtable();

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> itemList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < itemList.size(); i++) {
                        String name = itemList.get(i).getString("fullName");
                        String email = itemList.get(i).getString("username");

                        mylist.add(name);
                        dictionary_populate.put(name, email);
                    }

                    }


                 else {
                    Log.d("ParseApplicationError",e.toString());
                    // something went wrong
                }
            }
        });

    }

}
