package com.codepath.packagetwitter.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.codepath.packagetwitter.R;

import org.parceler.Parcel;

import java.util.Random;

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

    public static Mail getRandomMail(Context context){
        Mail mail;

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.trousers);

        Random rand = new Random();

        int[] volume = {rand.nextInt(2)+1,rand.nextInt(2)+1,rand.nextInt(2)+1};
        Double weight = Math.random() * 50;
        boolean isFragile =     rand.nextBoolean();

        mail = new Mail (icon, "Clothes", "2 pants and 1 polo", weight,volume,isFragile,"dont rip it!!!" );
        return mail;
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
}
