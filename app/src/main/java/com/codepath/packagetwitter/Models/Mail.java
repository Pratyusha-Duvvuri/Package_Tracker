package com.codepath.packagetwitter.Models;

import android.graphics.Bitmap;

import org.parceler.Parcel;

/**
 * Created by rafasj6 on 7/11/17.
 */

@Parcel
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

}
