package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.codepath.packagetwitter.R;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaunp on 7/11/17.
 */

@Parcel
public class User {
    String userName;
    String userHandle;

    public String phoneNum;
    public String tripStart;
    public String tripEnd;
    public String location;

    public Boolean hasPendingRequests;



    public User(String name, String handle, String phone, String tripStart, String tripEnd) {
        this.userHandle = handle;
        this.userName = name;
        this.phoneNum = phone;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;

    }



    public User(String name, String handle, String phone) {
        this.userHandle = handle;
        this.userName = name;
        this.phoneNum = phone;


    }


    public User(){}

        public static ArrayList<Transaction> getTransactions(Context context){
            //in  future would get transactions from web
            Sender sender = Sender.getRandomSender(context);
            CourierModel courier = CourierModel.getRandomCourrier(context);

            Receiver receiver = Receiver.getRandomReceiver(context);

            ArrayList<Transaction> transactions = new ArrayList<>();
            Transaction transaction = new Transaction(receiver, sender, courier, new Mail());
            transactions.add(transaction);
            transactions.add(transaction);
            return transactions;


        }

    public static User getRandomUser(Context context) {
        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_names);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactHandles = resources.obtainTypedArray(R.array.contact_handles);
        int handle = (int) (Math.random() * contactNames.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_numbers);
        int number = (int) (Math.random() * contactNumbers.length());

        Random rand = new Random();

        User user = new User(contactNames.getString(name), "@" + contactHandles.getString(handle), contactNumbers.getString(number));
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getTripStart() {
        return tripStart;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getTripEnd() {
        return tripEnd;
    }
}
