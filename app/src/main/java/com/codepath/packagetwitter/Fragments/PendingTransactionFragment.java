package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.codepath.packagetwitter.R;
import com.codepath.packagetwitter.TransactionAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by michaunp on 7/17/17.
 */

public class PendingTransactionFragment extends Fragment {
    TransactionAdapter transactionAdapter;
    ArrayList<ParselTransaction> transactions;
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
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvTransactions.addItemDecoration(itemDecoration);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        transactions = new ArrayList<>();
        // init the array list (data source)
        //construct the adapter form this datasource
        transactionAdapter = new TransactionAdapter(transactions);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
        rvTransactions.setLayoutManager(llayout);
        //set got the adapter
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

    public void populateTimeline() {
        ArrayList<ParselTransaction> pendingTransactions;
        parseUser = ParseUser.getCurrentUser();
        Log.d(parseUser.getString("username"),"ParseApplication");
        if (parseUser != null) {
            pendingTransactions = (ArrayList<ParselTransaction>) parseUser.get("pendingTransactions");//gets the list of pending transactions
            if (pendingTransactions != null) {
                transactions.clear();
                Log.d("pendingTransactions", pendingTransactions.get(0).toString());

                ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
                // Define our query conditions
                query.whereEqualTo("sender", parseUser.getUsername());
                List<Integer> list = new ArrayList<Integer>();
                list.add(0); list.add(1);
                query.whereContainedIn("transactionState", list);
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

                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });

                ParseQuery<ParselTransaction> query2 = ParseQuery.getQuery(ParselTransaction.class);
                // Define our query conditions
                query2.whereEqualTo("receiver", parseUser.getUsername());
                List<Integer> list2 = new ArrayList<Integer>();

                query2.whereEqualTo("transactionState", 1);
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

                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });}

            ParseQuery<ParselTransaction> query3 = ParseQuery.getQuery(ParselTransaction.class);
            // Define our query conditions
            query3.whereEqualTo("courier", parseUser.getUsername());

            query3.whereEqualTo("transactionState", 7);
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

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });}

//            if (pendingTransactions != null){
//
//            for (int i = 0; i < pendingTransactions.size(); i++) { //for every pending transaction
//
//                //transofrms the ParseTransaction into transaction and adds to the adapter
//                ParselTransaction trans =pendingTransactions.get(i); //gets current parsel transaction
//                addItems(trans);
//            }
//        }}
        }

    public void addItems(ParselTransaction transaction) {
        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);
        transactionAdapter.notifyDataSetChanged();
    }
}
