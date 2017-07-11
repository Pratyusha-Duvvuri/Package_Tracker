package com.codepath.packagetwitter.Models;

/**
 * Created by rafasj6 on 7/11/17.
 */


public class Transaction {
    Mail mail;
    Receiver receiver;// - Access to location, has received
    Sender sender; //-  Access to location, has received
    CourierModel courier;// - Access to most details
    int transactionState;//int that represents transaction state
    long transactionID; //add a long, Prat/I added this but you guys can remove it lol

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
}
