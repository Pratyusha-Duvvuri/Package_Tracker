package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.packagetwitter.R;

/**
 * Created by rafasj6 on 7/26/17.
 */

public class Review_frag extends Fragment {

    /**
     * Created by rafasj6 on 7/3/17.
     */

    View v;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.review_fragment_one, container, false);
            TextView tvStartAdd = (TextView) v.findViewById(R.id.tvFrom);
            TextView tvEndAdd = (TextView) v.findViewById(R.id.tvTo);
            TextView tvHandle = (TextView) v.findViewById(R.id.tvHandle);
            TextView tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
            TextView tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);

            tvStartAdd.setText(getArguments().getString("startAddress"));
            tvEndAdd.setText(getArguments().getString("endAddress"));
            tvHandle.setText(getArguments().getString("receiver"));
            tvStartDate.setText(getArguments().getString("startDate"));
            tvEndDate.setText(getArguments().getString("endDate"));


            return v;

        }
    public static Review_frag newInstance(String startAddress, String endAddress, String receiver, String startDate, String endDate) {

        Bundle args = new Bundle();
        args.putString("startAddress", startAddress);
        args.putString("endAddress", endAddress);
        args.putString("startDate", startDate);
        args.putString("endDate", endDate);
        args.putString("receiver", receiver);

        Review_frag fragment = new Review_frag();
        fragment.setArguments(args);

        return fragment;
    }







}
