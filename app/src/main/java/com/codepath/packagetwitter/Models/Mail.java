package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.graphics.Bitmap;

import org.parceler.Parcel;

/**
 * Created by rafasj6 on 7/11/17.
 */

@Parcel
public class Mail {
    Bitmap picture;// (url/bitmap/idk)
    String type;// ( from a list of types)
    String description;// (String )
    Double weight;// (number)
    int[] volume;// - L*B*H -- ints
    boolean isFragile;///Not fragile (boolean)
    boolean receiverAccepted;
    public Mail(){



    }

    public Mail(Bitmap pic, String type, String description, double weight, int[] volume,boolean fragile){
        this.picture = pic;
        this.type = type;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.isFragile = fragile;

    }


    public Bitmap getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Double getWeight() {
        return weight;
    }

    public int[] getVolume() {
        return volume;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setVolume(int[] volume) {
        this.volume = volume;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public void setSpecialRequirements(String specialRequirements) {
    }

    public boolean isFragile() {
        return isFragile;
    }

    public boolean isReceiverAccepted() {
        return receiverAccepted;
    }

    public void setReceiverAccepted(boolean receiverAccepted) {
        this.receiverAccepted = receiverAccepted;
    }



    public static Mail getRandomMail(Context context){



        //this.picture = pic;
        String description = "Random description woohoo";
        Double wt = 100.0;
        Bitmap pic = null;
        int[] volume = {1,2,3};// - L*B*H -- ints
        Boolean fragile = false;
        String type = "doggo";
        Mail mail = new Mail(pic, type, description, wt,  volume, fragile);
        return mail;
    }

}
