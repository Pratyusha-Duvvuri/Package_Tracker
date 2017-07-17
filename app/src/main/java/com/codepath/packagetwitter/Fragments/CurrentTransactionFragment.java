package com.codepath.packagetwitter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;

import java.util.ArrayList;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class CurrentTransactionFragment extends Fragment {

    TransactionAdapter transactionAdapter;
    public ArrayList<Transaction> transactions;
    RecyclerView rvTransactions;
    //SwipeRefreshLayout swipeContainer;
    //TwitterClient client;
    public static int page;
    public Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        transactions = new ArrayList<>();
        // init the array list (data source)
        //construct the adapter form this datasource
        transactionAdapter = new TransactionAdapter(transactions);

        this.context = context;
        populateTimeline();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout

        Log.d("on create", "in current");
        View v = inflater.inflate(R.layout.fragments_transactions_list, container, false);
        rvTransactions =  v.findViewById(R.id.rvTransactions);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
        rvTransactions.setLayoutManager(llayout);

        //set the adapter

        rvTransactions.setAdapter(transactionAdapter);


        //find the recycler view and swipe containerview


        return v;
    }


    //    //this is for the intermediate progress bar
    MenuItem miActionProgressItem;
   // ProgressBar v;

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }


    private void populateTimeline(){




    }
    public void addItems(Transaction transaction) {


        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);


    }

}
