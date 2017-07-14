package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.codepath.packagetwitter.R;

import org.parceler.Parcel;

import java.util.Random;

import static com.codepath.packagetwitter.R.drawable.phone;

/**
 * Created by pratyusha98 on 7/11/17.
 */

@Parcel
public class Receiver extends User {


    Boolean hasReceived;

    public Receiver(User u, String tripStart, String tripEnd, boolean hasReceived, Double location) {
        super(u.getUserName(), u.getUserHandle(), u.getPhoneNum(), tripStart, tripEnd);
        this.hasReceived = hasReceived;
        this.location = location;
    }
    public Receiver(){}

    public static Receiver getRandomReceiver(Context context){
        User u = User.getRandomUser(context);

        Random rand = new Random();
        String startDay = String.valueOf(rand.nextInt(28));

        String startMonth = String.valueOf(rand.nextInt(12));

        String endDay = String.valueOf(rand.nextInt(28));

        String endMonth = String.valueOf(rand.nextInt(12));

        Double weight = Math.random() * 50;


        Receiver receiver = new Receiver(u, startMonth+"/" + startDay ,endMonth + "/"+ endDay , false, weight);

        return receiver;
    }

}
