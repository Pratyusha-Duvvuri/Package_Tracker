package com.codepath.packagetwitter.Models;

import android.graphics.Bitmap;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class Mail {
    Bitmap picture;// (url/bitmap/idk)
    String type;// ( from a list of types)
    String description;// (String )
    Double weight;// (number)
    int[] volume;// - L*B*H -- ints
    boolean isFragile;///Not fragile (boolean)
    String specialRequirements;// (String)

    public Mail(){



    }

    public Mail(Bitmap pic, String type, String description, double weight, int[] volume,boolean fragile, String reqs){
        this.picture = pic;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.isFragile = fragile;
        this.specialRequirements = reqs;

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

    public boolean isFragile() {
        return isFragile;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }
}
