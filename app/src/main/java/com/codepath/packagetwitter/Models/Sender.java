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
public class Sender extends User{

    Boolean hasSent;


    public Boolean getHasSent() {
        return hasSent;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHasSent(Boolean hasSent) {
        this.hasSent = hasSent;
    }

    public Sender(User user, String tripStart, String tripEnd, boolean hasSent, String location) {
        super(user.getUserName(), user.getUserHandle(), user.getPhoneNum(), tripStart, tripEnd);
        this.hasSent = hasSent;
        this.location = location;

    }

    public Sender(User user, String tripStart, String tripEnd, String location) {
        super(user.getUserName(), user.getUserHandle(), user.getPhoneNum(), tripStart, tripEnd);
        this.location = location;

    }

    public Sender(User user)
    {
        this.setUserHandle(user.getUserHandle());
        this.setUserName(user.getUserName());
        this.setPhoneNum(user.getPhoneNum());

    }

    public Sender(){}
    public static Sender getRandomSender(Context context){
        User u = User.getRandomUser(context);
        Resources resources = context.getResources();


        Random rand = new Random();
        String startDay = String.valueOf(rand.nextInt(28));

        String startMonth = String.valueOf(rand.nextInt(12));

        String endDay = String.valueOf(rand.nextInt(28));

        String endMonth = String.valueOf(rand.nextInt(12));

        TypedArray locations = resources.obtainTypedArray(R.array.locations);
        int location = (int) (Math.random() * locations.length());


        Sender sender = new Sender(u, startMonth+"/" + startDay ,endMonth + "/"+ endDay , false, locations.getString(location));

        return sender;
    }

}
