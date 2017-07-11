package com.codepath.packagetwitter.Models;

import android.graphics.Bitmap;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class Mail {
    Bitmap picture;// (url/bitmap/idk)
    String Type;// ( from a list of types)
    String Description;// (String )
    int weight;// (number)
    int[] volume;// - L*B*H -- ints
    boolean isFragile;///Not fragile (boolean)
    String specialRequirements;// (String)

    public Mail(){



    }

    public Bitmap getPicture() {
        return picture;
    }

    public String getType() {
        return Type;
    }

    public String getDescription() {
        return Description;
    }

    public int getWeight() {
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
