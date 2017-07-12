package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.codepath.packagetwitter.R;

import org.parceler.Parcel;

import java.util.Random;

/**
 * Created by rafasj6 on 7/11/17.
 */

@Parcel
public class CourierModel  extends User{

    public boolean hasPickedUp = false;
    public boolean hasDelivered = false;
    public double weightAvailable;
    public int[] volumeAvailable;
    public String startAddress;
    public String endAddress;

    public CourierModel(String name, String handle, String phone, String tripStart, String tripEnd, double weight, int[] v, String sAddress, String eAddress){
        super(  name,  handle,  phone, tripStart, tripEnd);

        this.weightAvailable = weight;
        this.volumeAvailable = v;
        this.startAddress = sAddress;
        this.endAddress = eAddress;

    }
    public CourierModel(){}

    public static CourierModel getRandomCourrier(Context context){
        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_names);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactHandles = resources.obtainTypedArray(R.array.contact_handles);
        int handle = (int) (Math.random() * contactHandles.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_numbers);
        int number = (int) (Math.random() * contactNumbers.length());

        TypedArray locations = resources.obtainTypedArray(R.array.locations);
        int startLocation = (int) (Math.random() * locations.length());
        int endLocation = (int) (Math.random() * locations.length());

        Random rand = new Random();
        String startDay = String.valueOf(rand.nextInt(28));

        String startMonth = String.valueOf(rand.nextInt(12));

        String endDay = String.valueOf(rand.nextInt(28));

        String endMonth = String.valueOf(rand.nextInt(12));


        int[] volume = {rand.nextInt(2)+1,rand.nextInt(2)+1,rand.nextInt(2)+1};
        Double weight =  Math.random() * 50;

        String numb =  contactNumbers.getString(number);

        CourierModel courrier = new CourierModel(contactNames.getString(name), "@" + contactHandles.getString(handle), numb,
                startMonth+"/" + startDay ,endMonth + "/"+ endDay,weight, volume, locations.getString(startLocation), locations.getString(endLocation));
        return courrier;
    }

    public boolean isHasPickedUp() {
        return hasPickedUp;
    }

    public boolean isHasDelivered() {
        return hasDelivered;
    }

    public double getWeightAvailable() {
        return weightAvailable;
    }

    public int[] getVolumeAvailable() {
        return volumeAvailable;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }
}
