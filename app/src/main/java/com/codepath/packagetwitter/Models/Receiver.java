package com.codepath.packagetwitter.Models;

/**
 * Created by pratyusha98 on 7/11/17.
 */

public class Receiver extends User{

    Boolean hasReceived;
    Double location;

    public Receiver(String name, String handle, int phone, String tripStart, String tripEnd, boolean hasReceived, Double location) {
        super(name, handle, phone, tripStart, tripEnd);
        this.hasReceived = hasReceived;
        this.location = location;

    }

    public static Receiver getRandomSender(){
        Receiver receiver = new Receiver("Bob", "@bobby",911, "may 20th", "may 30th", false, 21.0);
        return receiver;
    }

}
