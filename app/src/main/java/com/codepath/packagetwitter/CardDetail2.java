package com.codepath.packagetwitter;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import butterknife.ButterKnife;

/**
 * Created by michaunp on 7/31/17.
 */

public class CardDetail2 extends Fragment{

    TextView tvType;
    TextView tvSize;
    TextView tvWeight;
    ImageView ivType;
    ImageView ivSize;
    RadioButton rbYep;
    RadioButton rbNope;
    CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_detail_2, container, false);
        tvType = (TextView) view.findViewById(R.id.tvType);
        tvSize = (TextView) view.findViewById(R.id.tvSize);
        tvWeight = (TextView) view.findViewById(R.id.textWeight);
        ivType = (ImageView) view.findViewById(R.id.ivType);
        ivSize = (ImageView) view.findViewById(R.id.ivSize);
        rbYep = (RadioButton) view.findViewById(R.id.rbYep);
        rbNope = (RadioButton) view.findViewById(R.id.rbNope);
        mCardView = (CardView) view.findViewById(R.id.card_view);
        mCardView.setMaxCardElevation(mCardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);
        ButterKnife.bind(view);

        String parselID = getArguments().getString("ParselID");
        final TypedArray sizePics = getResources().obtainTypedArray(R.array.size_pics);
        final TypedArray sizes = getResources().obtainTypedArray(R.array.sizes);
        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselID, new GetCallback<ParselTransaction>() {
            @Override
            public void done(ParselTransaction transaction, ParseException e) {
                if (e == null) {
                    tvType.setText(transaction.getMailType());
                    ivType.setImageResource(getTypeId(transaction.getMailType()));
                    tvWeight.setText(String.valueOf(transaction.getWeight()));
                    tvSize.setText(sizes.getString(transaction.getVolume()));
                    ivSize.setImageDrawable(sizePics.getDrawable(transaction.getVolume()));
                    setFragile(transaction.getIsFragile());
                }
            }
        });
        return view;

    }

    public static CardDetail2 newInstance(String parselID){

        Bundle transactionID = new Bundle();
        transactionID.putString("ParselID", parselID);
        CardDetail2 cardDetail2 = new CardDetail2();
        cardDetail2.setArguments(transactionID);

        return cardDetail2;
    }

    public void setFragile(Boolean b) {
        if(b) {
            RadioButton Yes = rbYep;
            Yes.setChecked(true);
        }
        else{
            RadioButton No = rbNope;
            No.setChecked(true);

        }
    }

    public int getTypeId(String type) {

        if (type.equals("Clothes")) {
            return R.mipmap.ic_clothes_fill;
        } else if (type.equals("Electronics")) {
            return R.mipmap.ic_electronics_filled;
        } else if (type.equals("Books")) {
            return R.mipmap.ic_books_filled;
        } else if (type.equals("Games")) {
            return R.mipmap.ic_games_filled;
        } else if (type.equals("Movies")) {
            return R.mipmap.ic_movies_filled;
        } else if (type.equals("Food")) {
            return R.mipmap.ic_food_filled;
        } else if (type.equals("Health")) {
            return R.mipmap.ic_health_filled;
        } else if (type.equals("Beauty")) {
            return R.mipmap.ic_beauty_filled;
        } else {
            return R.mipmap.ic_other_filled;
        }
    }

    public CardView getCardView() {
        return mCardView;
    }
}
