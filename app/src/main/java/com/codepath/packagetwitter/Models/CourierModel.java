package com.codepath.packagetwitter.Models;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class CourierModel  extends User{

    private boolean hasPickedUp = false;
    private boolean hasDelivered = false;
    private double weightAvailable;
    private int[] volumeAvailable;
    private String startAddress;
    private String endAddress;

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


}
