package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by rafasj6 on 7/17/17.
 */
@ParseClassName("ParselTransaction")
public class ParselTransaction extends ParseObject{
    Context context;


    public ParselTransaction() {
        super();
    }

    public ParselTransaction(String courier, String courierStartLoc, String courierEndLoc, Date courierStartDate, Date courierEndDate,
                             Double weightAvailable , int volumeAvailable){
        super();
        setCourier(courier);
        setCourierStart(courierStartDate);
        setCourierEnd(courierEndDate);
        setSenderLoc(courierStartLoc);
        setReceiverLoc(courierEndLoc);
        setVolume(volumeAvailable);
        setWeight(weightAvailable);
        setTransactionState(7);//courier has posted
        //for when courier creates the package
    }

    public ParselTransaction(String receiver, String sender, String senderLoc, String receiverLoc, Date sendStart, Date sendEnd
                             , String type, String description, Double weight,
                             int volume, Boolean fragile, String title) {
        //for when sender creates package
        super();
        setReceiver(receiver);
        setSender(sender);
        setSenderLoc(senderLoc);
        setSenderStart(sendStart);
        setSenderEnd(sendEnd);
        setReceiverLoc(receiverLoc);
        setMailType(type);
        setMailDescription(description);
        setWeight(weight);
        setVolume(volume);
        setTransactionState(0);
        setIsFragile(fragile);
        setTitle(title);


        //the following finds the receiver and sets its hasPendingRequests as true
        //changePendingRequestsStatus(true, receiver);

        String str= ParseUser.getCurrentUser().getString("username");
//        Toast.makeText( ,str, Toast.LENGTH_SHORT).show();
    Log.d("USER IS ", ParseUser.getCurrentUser().getString("username"));

    }

//    public static void changePendingRequestsStatus(final Boolean status, String receiver){
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.whereEqualTo("username", receiver); // find adults
//
//
//
//        query.findInBackground(new FindCallback<ParseUser>() {
//            public void done(List<ParseUser> objects, ParseException e) {
//                if (e == null) {
//                    // The query was successful.
//                    ParseUser userr = null;
//                    if(objects.size()!=0) {
//                        try {
//                            userr = logIn(objects.get(0).getString("username"), "x");
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//                        userr.put("hasPendingRequests", status); // attempt to change username
//                        userr.saveInBackground();
//                        try {
//                            userr = logIn(ProfileActivity.parseUser.getString("username"), "x");
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//
//                else {
//                    // Something went wrong.
//                    Log.d("ParseApplicationError","tf");
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    public void addReceiverInfo(Date receiverStart, Date receiverEnd){
        setReceiverStart(receiverStart);
        setReceiverEnd(receiverEnd);
        setTransactionState(1);
    }
    public void addCourierInfo(String courierId, Date courierStart, Date courierEnd){
        setCourier(courierId);
        setCourierStart(courierStart);
        setCourierEnd(courierEnd);
        setTransactionState(2);
    }

    public void setCourier(String courier) {
        put("courier",courier);
    }

    public void setCourierStart(Date courierStart) {
        put("courierStart",courierStart);

    }

    public void setCourierEnd(Date courierEnd) {
        put("courierEnd",courierEnd);

    }


    public void setSenderLoc(String senderLoc) {
        put("senderLoc",senderLoc);
    }

    public void setSenderStart(Date senderStart) {
        put("senderStart" ,senderStart);
    }

    public void setSenderEnd(Date senderEnd) {
        put("senderEnd" , senderEnd);
    }

    public void setReceiver(String receiver) {
        put("receiver", receiver);
    }

    public void setSender(String sender) {
        put("sender", sender);
    }

    public void setMailPic(Bitmap mailPic) {
        put("mailPic", mailPic);
    }

    public void setMailType(String mailType) {
        put("mailType",mailType);
    }

    public void setMailDescription(String mailDescription) {
        put("mailDescription", mailDescription);
    }
    public void setTitle(String title) {
        put("title",title);
    }


    public void setWeight(Double weight) {
        put("weight", weight);
    }

    public void setVolume(int volume) {
        put("volume" ,volume);
    }

    public void setReceiverLoc(String receiverLoc) {
        put("receiverLoc", receiverLoc);
    }

    public void setReceiverStart(Date receiverStart) {
        put("receiverStart",  receiverStart);
    }

    public void setReceiverEnd(Date receiverEnd) {
        put("receiverEnd",receiverEnd);
    }

    public void setTransactionState(int transactionState) {
        put("transactionState", transactionState);
    }

    public String getCourier() {
        return getString("courier");
    }

    public Date getCourierStart() {
        return getDate("courierStart");
    }

    public Date getCourierEnd() {
        return getDate("courierEnd");
    }

    public String getSenderLoc() {
        return getString("senderLoc");
    }

    public Date getSenderStart() {
        return getDate("senderStart");
    }

    public Date getSenderEnd() {
        return getDate("senderEnd");
    }

    public String getReceiver() {
        return getString("receiver");
    }

    public String getFullName() {
        return getString("fullName");
    }


    public String getSender() {
        return getString("sender");
    }
    public String getTitle() {
        return getString("title");
    }

//    public Bitmap getMailPic() {
//        return getParseFile("mailPic");
//    }

    public String getMailType() {
        return getString("mailType");
    }

    public boolean getIsFragile() {
        return getBoolean("isFragile");
    }

    public void setIsFragile(boolean fragile) {
         put("isFragile", fragile);
    }


    public String getMailDescription() {
        return getString("mailDescription");
    }

    public Double getWeight() {
        return getDouble("weight");
    }

    public int getVolume() {
        return getInt("volume");
    }

    public String getReceiverLoc() {
        return getString("receiverLoc");
    }

    public Date getReceiverStart() {
        return getDate("receiverStart");
    }

    public Date getReceiverEnd() {
        return getDate("receiverEnd");
    }
    public int getTransactionState() {
        return getInt("transactionState");
    }



    // Use getString and others to access fields
    public String getBody() {
        return getString("body");
    }

    // Use put to modify field values
    public void set(String value) {
        put("body", value);
    }

    // Get the user for this item
    public ParseUser getUser()  {
        return getParseUser("owner");
    }

    // Associate each item with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }
}
