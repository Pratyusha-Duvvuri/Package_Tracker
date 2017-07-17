package com.codepath.packagetwitter.Models;

import android.content.Context;

import com.parse.ParseObject;

import org.parceler.Parcel;

/**
 * Created by rafasj6 on 7/11/17.
 */

@Parcel
//@ParseClassName("Transaction")
public class Transaction extends ParseObject{
    Mail mail;
    Receiver receiver;// - Access to location, has received
    Sender sender; //-  Access to location, has received
    CourierModel courier;// - Access to most details
    int transactionState;//int that represents transaction state
    long transactionID; //add a long that is unique to each transaction


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

        Transaction transaction = new Transaction(receiver, sender, courier, new Mail());

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

    public long getTransactionID() {
        return transactionID;
    }
}
