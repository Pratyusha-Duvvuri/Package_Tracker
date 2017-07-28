package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        tvType.setText(getArguments().getString("type"));
        tvSize.setText(String.valueOf(getArguments().getInt("volume")));
        tvWeight.setText(String.valueOf(getArguments().getDouble("weight")));
        setFragile(v);
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
