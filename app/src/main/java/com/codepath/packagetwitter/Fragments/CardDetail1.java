package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.packagetwitter.CardAdapter;
import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by michaunp on 7/31/17.
 */

public class CardDetail1 extends Fragment {

    TextView Title;
    TextView Description;
    CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_detail_1, container, false);

        Title = (TextView) view.findViewById(R.id.Title);
        Description = (TextView) view.findViewById(R.id.Description);
        mCardView = (CardView) view.findViewById(R.id.card_view);
        mCardView.setMaxCardElevation(mCardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        String parselID = getArguments().getString("ParselID");
        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselID, new GetCallback<ParselTransaction>() {
            @Override
            public void done(ParselTransaction transaction, ParseException e) {
                if (e == null) {
                    Title.setText(transaction.getTitle());
                    Description.setText(transaction.getMailDescription());
                }
            }
        });




        return view;

    }

    public static CardDetail1 newInstance(String parselID){

        Bundle transactionID = new Bundle();
        transactionID.putString("ParselID", parselID);
        CardDetail1 cardDetail1 = new CardDetail1();
        cardDetail1.setArguments(transactionID);

        return cardDetail1;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
