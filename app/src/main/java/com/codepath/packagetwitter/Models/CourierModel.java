package com.codepath.packagetwitter.Models;

import org.parceler.Parcel;

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

    public CourierModel(){}
    public CourierModel(String name, String handle, int phone, String tripStart, String tripEnd, double weight, int[] v, String sAddress, String eAddress){
        super(  name,  handle,  phone, tripStart, tripEnd);

        this.weightAvailable = weight;
        this.volumeAvailable = v;
        this.startAddress = sAddress;
        this.endAddress = eAddress;

    }

    public static CourierModel getRandomCourrier(){
        int[] volume = {2,3};
        CourierModel courrier = new CourierModel("Bob", "@bobby",911, "may 20th", "may 30th", 15.0, volume, "here", "there");
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
