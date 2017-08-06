package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class OldTransactionFragment extends Fragment {

    TransactionAdapter transactionAdapter;
    ArrayList<ParselTransaction> transactions;
    RecyclerView rvTransactions;
    SwipeRefreshLayout swipeContainer;
    //TwitterClient client;
    public static int page;
    TextView tvTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout

        Log.d("on create", "in current");
        View v = inflater.inflate(R.layout.fragments_transactions_list, container, false);


        //find the recycler view and swipe containerview
        rvTransactions =  v.findViewById(R.id.rvTransactions);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvTransactions.addItemDecoration(itemDecoration);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        tvTransaction = (TextView) v.findViewById(R.id.tvTransaction);
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
                populateTimeline();
                Toast.makeText(getContext(), "Refresh is working", Toast.LENGTH_LONG);
                swipeContainer.setRefreshing(false);
            }
        });
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


    public void populateTimeline(){
        ArrayList<ParselTransaction> pendingTransactions;
        try {
            parseUser = ParseUser.getCurrentUser().fetch();
            }
            catch (com.parse.ParseException e) {}

        if (parseUser != null) {
            transactions.clear();
            transactionAdapter.notifyDataSetChanged();
            query();

            }
        }

    public void query(){
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // Define our query conditions
        query.whereEqualTo("sender", parseUser.getUsername());
        query.whereEqualTo("transactionState", 4);
        // Execute the find asynchronously
        query.findInBackground(new FindCallback<ParselTransaction>() {
            @Override
            public void done(List<ParselTransaction> issueList, ParseException e) {
                if (e == null) {
                    Log.d("Issue", "Retrieved " + issueList.size() + " issue");
                    for (int i = 0; i < issueList.size(); i++) {
                        ParselTransaction trans = issueList.get(i); //gets current parsel transaction
                        addItems(trans);

                    }
                    queryForReceiver();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void queryForReceiver(){

        ParseQuery<ParselTransaction> query2 = ParseQuery.getQuery(ParselTransaction.class);
        // Define our query conditions
        query2.whereEqualTo("receiver", parseUser.getUsername());

        query2.whereEqualTo("transactionState", 4);
        // Execute the find asynchronously

        query2.findInBackground(new FindCallback<ParselTransaction>() {
            @Override
            public void done(List<ParselTransaction> issueList, ParseException e) {
                if (e == null) {
                    Log.d("Issue", "Retrieved " + issueList.size() + " issue");
                    for (int i = 0; i < issueList.size(); i++) {
                        ParselTransaction trans = issueList.get(i); //gets current parsel transaction
                        addItems(trans);
                    }

                    queryForCourier();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    public void queryForCourier(){
        ParseQuery<ParselTransaction> query3 = ParseQuery.getQuery(ParselTransaction.class);
        // Define our query conditions
        query3.whereEqualTo("courier", parseUser.getUsername());
        query3.whereEqualTo("transactionState", 4);
        // Execute the find asynchronously

        query3.findInBackground(new FindCallback<ParselTransaction>() {
            @Override
            public void done(List<ParselTransaction> issueList, ParseException e) {
                if (e == null) {
                    Log.d("Issue", "Retrieved " + issueList.size() + " issue");
                    for (int i = 0; i < issueList.size(); i++) {
                        ParselTransaction trans = issueList.get(i); //gets current parsel transaction
                        addItems(trans);
                    }
                    Collections.sort(transactions, new Comparator<ParselTransaction>() {
                        @Override
                        public int compare(ParselTransaction t1, ParselTransaction t2) {
                            if (t1.getTransactionState() == 7)return 1;
                            return t2.getTransactionState()-t1.getTransactionState();

                        }
                    });
                    transactionAdapter.notifyDataSetChanged();

                    if (transactions.size()>0) tvTransaction.setVisibility(View.GONE);
                    else {tvTransaction.setVisibility(View.VISIBLE);}


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }




    public void addItems(ParselTransaction transaction) {


        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);


    }

}
