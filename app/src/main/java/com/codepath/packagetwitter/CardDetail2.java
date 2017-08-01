package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by michaunp on 7/31/17.
 */

public class CardDetail2 extends Fragment{
    CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_detail_2, container, false);
        mCardView = (CardView) view.findViewById(R.id.card_view);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;

    }

    public CardView getCardView() {
        return mCardView;
    }
}
