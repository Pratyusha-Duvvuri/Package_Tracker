package com.codepath.packagetwitter.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;

import java.util.ArrayList;

/**
 * Created by michaunp on 7/11/17.
 */

public class CurrentTransactionsFragment extends Fragment {

    TransactionAdapter transactionAdapter;
    ArrayList<Transaction> transactions;
    RecyclerView rvTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate the layout
        View v = inflater.inflate(R.layout.current_fragment_transaction_list,container, false);
        rvTransaction = (RecyclerView) v.findViewById(R.id.rvTransaction);
        //init arraylist (data source)
        transactions = new ArrayList<>();
        //construct adapter from data source
        transactionAdapter = new TransactionAdapter(transactions);
        //RecylerView setup (layout manager, link with adapter)
        rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        //set the adapter
        rvTransaction.setAdapter(transactionAdapter);
        //set up lines to seperate tweets
        DividerItemDecoration mDividerItemDecoration;
        mDividerItemDecoration = new DividerItemDecoration(rvTransaction.getContext(), Configuration.ORIENTATION_PORTRAIT);
        rvTransaction.addItemDecoration(mDividerItemDecoration);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
