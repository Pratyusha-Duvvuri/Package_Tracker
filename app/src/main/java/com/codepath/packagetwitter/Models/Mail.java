package com.codepath.packagetwitter.Models;

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

    public Mail(Bitmap pic, String type, String description, double weight, int[] volume,boolean fragile, String reqs){
        this.picture = pic;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.isFragile = fragile;

    }

  /*  public static Mail getRandomMail(){
        Mail mail;

        int [] v = {2,3,4};
        mail = new Mail (R.drawable.trousers, "Clothes", "2 pants and a polo", 2.0,v,false,"dont rip it!!!" );
        return mail;
    }*/

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
}
