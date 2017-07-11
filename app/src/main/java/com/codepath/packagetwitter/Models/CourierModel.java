package com.codepath.packagetwitter.Models;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class CourierModel  extends User{

    private boolean hasPickedUp = false;
    private boolean hasDelivered = false;
    private float weightAvailable;
    private int[] volumeAvailable;
    private String startAddress;
    private String endAddress;

    public void CourierModel(float weight, int[] v, String sAddress, String eAddress){


        weightAvailable = weight;
        volumeAvailable = v;
        startAddress = sAddress;
        endAddress = eAddress;

    }

    public void picked_up(){
        hasPickedUp = true;

    }

}
