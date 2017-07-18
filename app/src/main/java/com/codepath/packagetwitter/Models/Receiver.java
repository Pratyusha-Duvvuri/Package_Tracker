package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.codepath.packagetwitter.R;

import org.parceler.Parcel;

import java.util.Random;

/**
 * Created by pratyusha98 on 7/11/17.
 */

@Parcel
public class Receiver extends User {


    Boolean hasReceived;

    public Receiver(User u, String tripStart, String tripEnd, boolean hasReceived, String location) {
        super(u.getUserName(), u.getUserHandle(), u.getPhoneNum(), tripStart, tripEnd);
        this.hasReceived = hasReceived;
        this.location = location;
    }
    public Receiver(User user)
    {
        this.setUserHandle(user.getUserHandle());
        this.setUserName(user.getUserName());
        this.setPhoneNum(user.getPhoneNum());

    }
    public Receiver(){}

    public static Receiver getRandomReceiver(Context context){
        User u = User.getRandomUser(context);
        Resources resources = context.getResources();
        TypedArray locations = resources.obtainTypedArray(R.array.locations);
        int location = (int) (Math.random() * locations.length());


        Random rand = new Random();
        String startDay = String.valueOf(rand.nextInt(28));

        String startMonth = String.valueOf(rand.nextInt(12));

        String endDay = String.valueOf(rand.nextInt(28));

        String endMonth = String.valueOf(rand.nextInt(12));



        Receiver receiver = new Receiver(u, startMonth+"/" + startDay ,endMonth + "/"+ endDay , false, locations.getString(location));


        return receiver;
    }

}
