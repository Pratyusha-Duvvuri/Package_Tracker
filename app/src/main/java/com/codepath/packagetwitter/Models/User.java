package com.codepath.packagetwitter.Models;

import java.util.ArrayList;

/**
 * Created by michaunp on 7/11/17.
 */

public class User {
    String userName;
    String userHandle;
    String phoneNum;
    String tripStart;
    String tripEnd;
    ArrayList <Transaction> transactions;

    public User(){}
    public User (ArrayList<Transaction> transacts){
        transactions = transacts;


    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
