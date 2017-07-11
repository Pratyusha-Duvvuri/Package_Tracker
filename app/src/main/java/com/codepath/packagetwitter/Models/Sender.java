package com.codepath.packagetwitter.Models;

/**
 * Created by pratyusha98 on 7/11/17.
 */

public class Sender extends User{

    Double location;
    Boolean hasSent;

    public Sender(String name, String handle, int phone, String tripStart, String tripEnd, boolean hasSent, Double location) {
        super( name, handle, phone, tripStart, tripEnd);
        this.hasSent = hasSent;
        this.location = location;

    }

    public static Sender getRandomSender(){
        Sender sender = new Sender("Bob", "@bobby",911, "may 20th", "may 30th", false, 21.0);
        return sender;
    }

}
