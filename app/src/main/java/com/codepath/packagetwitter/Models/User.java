package com.codepath.packagetwitter.Models;

import java.util.ArrayList;

/**
 * Created by michaunp on 7/11/17.
 */

public class User {
    String userName;
    String userHandle;
    int phoneNum;
    String tripStart;
    String tripEnd;



    public User(String name, String handle, int phone, String tripStart, String tripEnd){
            this.userHandle = handle;
            this.userName = name;
            this.phoneNum = phone;
            this.tripStart = tripStart;
            this.tripEnd = tripEnd;


        }

        public static ArrayList<Transaction> getTransactions () {
            //in  future would get transactions from web
            CourierModel courier = CourierModel.getRandomCourrier();
            Sender sender = Sender.getRandomSender();
            Receiver receiver = Receiver.getRandomSender();

            ArrayList<Transaction> transactions = new ArrayList<>();
            Transaction transaction = new Transaction(receiver, sender, courier, new Mail());
            transactions.add(transaction);
            transactions.add(transaction);
            return transactions;


        }

    public static User getRandomUser() {
        User user = new User("Bob", "@bobby", 911, "may 20th", "may 30th");
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public String getTripStart() {
        return tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }
}
