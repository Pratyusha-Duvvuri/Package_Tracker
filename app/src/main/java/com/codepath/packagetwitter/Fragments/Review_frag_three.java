package com.codepath.packagetwitter.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.codepath.packagetwitter.R;

/**
 * Created by rafasj6 on 7/26/17.
 */

public class Review_frag_three extends Fragment {

    /**
     * Created by rafasj6 on 7/3/17.
     */

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.review_fragment_three, container, false);
        TextView tvType = (TextView) v.findViewById(R.id.tvType);
        TextView tvSize = (TextView) v.findViewById(R.id.tvSize);
        TextView tvWeight = (TextView) v.findViewById(R.id.tvWeightTrue);
        ImageButton ibType = (ImageButton) v.findViewById(R.id.ibType);
        ImageButton ibSize = (ImageButton) v.findViewById(R.id.ibSize);
        TypedArray sizes = getResources().obtainTypedArray(R.array.sizes);
        TypedArray sizePics = getResources().obtainTypedArray(R.array.size_pics);
        TypedArray typePics = getResources().obtainTypedArray(R.array.size_pics);

        String type = getArguments().getString("type");
        int volume = getArguments().getInt("volume") ;
        tvType.setText(getArguments().getString("type"));

        ibSize.setImageDrawable(sizePics.getDrawable(volume));
        tvSize.setText(sizes.getString(volume));
        tvWeight.setText(String.valueOf(getArguments().getDouble("weight")));
        setFragile(v);

        ibType.setImageResource(getTypeId(type));
        return v;

    }
    public static Review_frag_three newInstance(String type, int volume, Double weight, boolean fragile) {

        Bundle args = new Bundle();
        args.putString("type", type);
        args.putDouble("weight", weight);
        args.putInt("volume", volume);
        args.putBoolean("fragile", fragile);
        Review_frag_three fragment = new Review_frag_three();
        fragment.setArguments(args);

        return fragment;
    }

    public int getTypeId(String type){
        if (type.equals( "Clothes")) {
            return  R.mipmap.ic_clothes_fill;
        }
        else if (type.equals("Electronics")) {
            return R.mipmap.ic_electronics_filled;
        }

        else if (type.equals( "Books")) {
            return R.mipmap.ic_books_filled;
        }
        else if (type.equals( "Games")) {
            return R.mipmap.ic_games_filled;
        }
        else if (type.equals( "Movies")) {
            return R.mipmap.ic_movies_filled;
        }
        else if (type.equals( "Food") ){
            return R.mipmap.ic_food_filled;
        }
        else if (type.equals( "Health")) {
            return R.mipmap.ic_health_filled;
        }
        else if (type.equals(  "Beauty")) {
            return  R.mipmap.ic_beauty_filled;
        }
        else {
            return R.mipmap.ic_other_filled;
        }


    }

    public void setFragile(View v){
        if(getArguments().getBoolean("fragile")) {
            RadioButton Yes = v.findViewById(R.id.rbYes);
            Yes.setChecked(true);
        }
        else{
            RadioButton No = v.findViewById(R.id.rbNo);
            No.setChecked(true);

        }

    }




}
