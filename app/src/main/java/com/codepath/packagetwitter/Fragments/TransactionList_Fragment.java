package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;

import java.util.ArrayList;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class TransactionList_Fragment extends android.support.v4.app.Fragment {




    TransactionAdapter transactionAdapter;
    ArrayList<Transaction> transactions;
    RecyclerView rvTransactions;
    //SwipeRefreshLayout swipeContainer;
    //TwitterClient client;
    public static int page;
    //private EndlessRecyclerViewScrollListener scrollListener;


    //inflation happens inside onCreateView


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_transactions_list, container, false);


        //find the recycler view and swipe containerview
        rvTransactions = (RecyclerView) v.findViewById(R.id.rvTransactions);
        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        transactions = new ArrayList<>();
        // init the array list (data source)
        //construct the adapter form this datasource
        transactionAdapter = new TransactionAdapter(transactions);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
        rvTransactions.setLayoutManager(llayout);

        //set the adapter

        rvTransactions.setAdapter(transactionAdapter);

        transactions = User.getTransactions(getContext());
        transactionAdapter.notifyDataSetChanged();
        return v;
    }


}
