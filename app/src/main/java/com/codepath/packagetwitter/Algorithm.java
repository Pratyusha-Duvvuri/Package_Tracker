package com.codepath.packagetwitter;

import android.content.Context;

import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rafasj6 on 7/14/17.
 */

public class Algorithm {

    public static ArrayList<Transaction> getPossibleMatches(Context context, Sender sender, Mail  mail){
        //returns a list of random transactions, all of wich are possible matches
        ArrayList<Transaction> transactions = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            CourierModel courier = CourierModel.getRandomCourrier(context);
            Receiver receiver = Receiver.getRandomReceiver(context);
            if (isPossibleMatch(sender, receiver, courier, mail)) {
                transactions.add(new Transaction(receiver, sender, courier, mail));

            }
        }

        return  transactions;
    }

    public static ArrayList<Transaction> getPossibleMatches(Context context, Receiver receiver){
        //returns a list of random transactions, all of wich are possible matches

        ArrayList<Transaction> transactions = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            CourierModel courier = CourierModel.getRandomCourrier(context);
            Sender sender = Sender.getRandomSender(context);
            Mail mail = Mail.getRandomMail(context);
            if (isPossibleMatch(sender, receiver, courier, mail)) {
                transactions.add(new Transaction(receiver, sender, courier, mail));

            }
        }
        return  transactions;
    }

    public static ArrayList<Transaction> getPossibleMatches(Context context, CourierModel courier){
        //returns a list of random transactions, all of wich are possible matches

        ArrayList<Transaction> transactions = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            Receiver receiver = Receiver.getRandomReceiver(context);
            Sender sender = Sender.getRandomSender(context);
            Mail mail = Mail.getRandomMail(context);
            if (isPossibleMatch(sender, receiver, courier, mail)) {
                transactions.add(new Transaction(receiver, sender, courier, mail));

            }
        }

        return  transactions;
    }

    public static ArrayList<Transaction> getPossibleMatches(Context context, Sender sender){

        ArrayList<Transaction> transactions = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            CourierModel courier = CourierModel.getRandomCourrier(context);
            Receiver receiver = Receiver.getRandomReceiver(context);
            Mail mail = Mail.getRandomMail(context);
            if (isPossibleMatch(sender, receiver, courier, mail)) {
                transactions.add(new Transaction(receiver, sender, courier, mail));

            }
        }

        return  transactions;
    }


    public static boolean isPossibleMatch(Sender sender, Receiver receiver, CourierModel courier, Mail mail) {
        Date senderStartDate = null;
        Date senderEndDate = null;
        Date courierStartDate = null;
        Date courierEndDate = null;
        Date receiverStartDate = null;
        Date receiverEndDate = null;
        try {
            senderStartDate = new SimpleDateFormat("MM/dd").parse(sender.getTripStart());
            senderEndDate = new SimpleDateFormat("MM/dd").parse(sender.getTripEnd());
            courierStartDate = new SimpleDateFormat("MM/dd").parse(courier.getTripStart());
            courierEndDate = new SimpleDateFormat("MM/dd").parse((courier.getTripEnd()));
            receiverStartDate = new SimpleDateFormat("MM/dd").parse(receiver.getTripStart());
            receiverEndDate = new SimpleDateFormat("MM/dd").parse(receiver.getTripEnd());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(courierStartDate.after(senderStartDate) && courierStartDate.before(senderEndDate) // if courier start date btwn sender start date and end date
            && courierEndDate.after(receiverStartDate) && courierEndDate.before(receiverEndDate) // if courier end date btwn receiver start date and end date
                && mail.getWeight() <= courier.getWeightAvailable() && mail.getVolume()[0] <= courier.getVolumeAvailable()[0]) {//TODO change volume into 0-5


            return true; // if everything is in order, returns true
        }
        else {
            return false; //otherwise, false
        }
}

}
