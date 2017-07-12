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
    Double location;

    public Receiver(String name, String handle, String phone, String tripStart, String tripEnd, boolean hasReceived, Double location) {
        super(name, handle, phone, tripStart, tripEnd);
        this.hasReceived = hasReceived;
        this.location = location;

    }
    public Receiver(){}

    public static Receiver getRandomReceiver(Context context){
        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_names);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactHandles = resources.obtainTypedArray(R.array.contact_handles);
        int handle = (int) (Math.random() * contactNames.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_numbers);
        int number = (int) (Math.random() * contactNumbers.length());

        Random rand = new Random();
        String startDay = String.valueOf(rand.nextInt(28));

        String startMonth = String.valueOf(rand.nextInt(12));

        String endDay = String.valueOf(rand.nextInt(28));

        String endMonth = String.valueOf(rand.nextInt(12));

        Double weight = Math.random() * 50;


        Receiver receiver = new Receiver(contactNames.getString(name), "@" + contactHandles.getString(handle),
                contactNumbers.getString(number), startMonth+"/" + startDay ,endMonth + "/"+ endDay , false, weight);

        return receiver;
    }

}
