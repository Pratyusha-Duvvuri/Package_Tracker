package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by rafasj6 on 7/11/17.
 */

@Parcel
//@ParseClassName("Transaction")
public class Transaction {
    Mail mail;
    Receiver receiver;// - Access to location, has received
    Sender sender; //-  Access to location, has received
    CourierModel courier;// - Access to most details
    int transactionState;//int that represents transaction state
    String transactionID; //add a String that is unique to each transaction



    public Transaction(){}
//    States of transaction
//            Sent
//    Received by Courier
//    In transit
//    Arrived in destination
//    Delivered to receiver

    public Transaction(Receiver r, Sender s, CourierModel c, Mail m){
        this.mail = m;
        this.receiver =r;
        this.sender = s;
        this.courier = c;
        this.transactionState = getTransactionState();
    }

    public Transaction(final ParselTransaction parselTransaction){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", parselTransaction.getReceiver());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    User u = new User(objects.get(0).getUsername(),objects.get(0).getString("handle"), objects.get(0).getString("phone"));
                     receiver = new Receiver(u,String.valueOf(parselTransaction.getReceiverStart()),
                            String.valueOf(parselTransaction.getReceiverEnd()),
                            parselTransaction.getReceiverLoc());
                } else {
                    Log.e("Parse application error", "Couldn't log to background");
                }
            }
        });//

        query = ParseUser.getQuery();
        query.whereEqualTo("username", parselTransaction.getSender());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    User u = new User(objects.get(0).getUsername(),objects.get(0).getString("handle"), objects.get(0).getString("phone"));
                     sender = new Sender(u,String.valueOf(parselTransaction.getSenderStart()),
                            String.valueOf(parselTransaction.getSenderEnd()),
                            parselTransaction.getSenderLoc());
                } else {
                    // Something went wrong.
                }
            }
        });//

        query = ParseUser.getQuery();
        query.whereEqualTo("username", parselTransaction.getCourier());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    User u = null;
                    try {
                        u = new User(objects.get(0).fetchIfNeeded().getString("username"),objects.get(0).fetchIfNeeded().getString("userHandle"), objects.get(0).getString("mobileNumber"));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

//                    User u = new User(objects.get(0).getUsername(),objects.get(0).getString("handle"), objects.get(0).getString("phone"));
                     courier = new CourierModel(u,String.valueOf(parselTransaction.getCourierStart()),
                            String.valueOf(parselTransaction.getCourierEnd()),
                            parselTransaction.getWeight(), parselTransaction.getVolume(),parselTransaction.getSenderLoc(), parselTransaction.getReceiverLoc());
                } else {
                    // Something went wrong.
                }
            }
        });//

        mail = new Mail(null, parselTransaction.getMailType(),
                parselTransaction.getMailDescription(),parselTransaction.getWeight(),
                parselTransaction.getVolume(), parselTransaction.getIsFragile());

        transactionID = parselTransaction.getObjectId();

    }


    private int getTransactionState(){

        return 0;// for now
    }

    public Mail getMail() {
        return mail;
    }

    public static   Transaction getRandomTransaction(Context context){
        //in  future would get transactions from web
        Sender sender = Sender.getRandomSender(context);
        CourierModel courier = CourierModel.getRandomCourrier(context);

        Receiver receiver = Receiver.getRandomReceiver(context);

        Mail mail = Mail.getRandomMail(context);

        Transaction transaction = new Transaction(receiver, sender, courier, mail);

        return transaction;


    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public CourierModel getCourier() {
        return courier;
    }

    public String getTransactionID() {
        return transactionID;
    }
}
