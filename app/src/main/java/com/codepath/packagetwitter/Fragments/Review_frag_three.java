package com.codepath.packagetwitter.Fragments;

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

        String type = getArguments().getString("type");
        int volume = getArguments().getInt("volume") ;
        tvType.setText(getArguments().getString("type"));
        tvSize.setText(String.valueOf(getArguments().getString("volume_string")));
        tvWeight.setText(String.valueOf(getArguments().getDouble("weight")));
        setFragile(v);
        if (type == "Clothes")
            ibType.setImageResource(R.mipmap.ic_clothes_fill);
        return v;

    }
    public static Review_frag_three newInstance(String type, int volume, Double weight, boolean fragile, String volume_string) {

        Bundle args = new Bundle();
        args.putString("type", type);
        args.putDouble("weight", weight);
        args.putInt("volume", volume);
        args.putString("volume_string", volume_string);
        args.putBoolean("fragile", fragile);
        Review_frag_three fragment = new Review_frag_three();
        fragment.setArguments(args);

        return fragment;
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
