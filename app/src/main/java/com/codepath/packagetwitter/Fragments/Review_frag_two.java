package com.codepath.packagetwitter.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.R;

/**
 * Created by rafasj6 on 7/26/17.
 */

public class Review_frag_two extends Fragment {

    /**
     * Created by rafasj6 on 7/3/17.
     */

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.review_fragment_two, container, false);

        //the following updates the display of this frag
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescription);

        tvTitle.setText(getArguments().getString("title"));
        tvDescription.setText(getArguments().getString("description"));

        setPackageImage(getArguments().getByteArray("image"), v);


        return v;

    }
    public static Review_frag_two newInstance(byte[] image, String title, String description) {

        Bundle args = new Bundle();
        args.putByteArray("image", image);
        args.putString("title", title);
        args.putString("description", description);




        Review_frag_two fragment = new Review_frag_two();
        fragment.setArguments(args);

        return fragment;
    }



    public void setPackageImage(byte[] byteArray, View v){
        //folowing converts byteArray to bitmpa then puts into image view
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) v.findViewById(R.id.ibPackageUpload);

        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                200, false));
    }


}
