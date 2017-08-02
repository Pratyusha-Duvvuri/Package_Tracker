package com.codepath.packagetwitter;

/**
 * Created by michaunp on 7/31/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.codepath.packagetwitter.Fragments.CardDetail1;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private float mBaseElevation;
    CardDetail1 cardDetail1;
    CardDetail2 cardDetail2;
    Context context;



    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation,Context contextt) {
        super(fm);
        mBaseElevation = baseElevation;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(contextt);
        String parselID = sharedPref.getString("ParselID", null);
        cardDetail1 = CardDetail1.newInstance(parselID);
        cardDetail2 = CardDetail2.newInstance(parselID);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        if (position == 0)
            return cardDetail1.getCardView();

        else if (position == 1)
            return cardDetail2.getCardView();
        else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return cardDetail1;

        else if (position == 1)
            return cardDetail2;
        else
            return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        if (position == 0)
            fragment = cardDetail1;
        if (position == 1)
            fragment = cardDetail2;
        return fragment;
    }

}