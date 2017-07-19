package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;

import java.util.ArrayList;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by michaunp on 7/17/17.
 */

public class PendingTransactionFragment extends Fragment {
    TransactionAdapter transactionAdapter;
    ArrayList<Transaction> transactions;
    RecyclerView rvTransactions;
    SwipeRefreshLayout swipeContainer;
    public static int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout

        Log.d("on create", "in current");
        View v = inflater.inflate(R.layout.fragments_transactions_list, container, false);


        //find the recycler view and swipe containerview
        rvTransactions =  v.findViewById(R.id.rvTransactions);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
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

        populateTimeline();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Toast.makeText(getContext(), "Refresh is working", Toast.LENGTH_LONG);
                swipeContainer.setRefreshing(false);
            }
        });

        return v;
    }

    public void populateTimeline() {
        ArrayList<ParselTransaction> pendingTransactions;
        if (parseUser != null) {
            pendingTransactions = (ArrayList<ParselTransaction>) parseUser.get("pendingTransactions");//gets the list of pending transactions
            if (pendingTransactions != null){

            for (int i = 0; i < pendingTransactions.size(); i++) { //for every pending transaction

                //transofrms the ParseTransaction into transaction and adds to the adapter
                Transaction trans = new Transaction(pendingTransactions.get(i));
                addItems(trans);
            }
        }}
    }

    public void addItems(Transaction transaction) {
        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);
    }
}
