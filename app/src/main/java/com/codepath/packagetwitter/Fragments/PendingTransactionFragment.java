package com.codepath.packagetwitter.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
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
 * Created by michaunp on 7/17/17.
 */

public class PendingTransactionFragment extends Fragment {
    TransactionAdapter transactionAdapter;
     ArrayList<ParselTransaction> transactions;
    RecyclerView rvTransactions;
    SwipeRefreshLayout swipeContainer;
    public static int page;
    TextView tvTransaction;


    public ArrayList<ParselTransaction> getTransactions() {
        return transactions;
    }



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
        //set got the adapter
        rvTransactions.setAdapter(transactionAdapter);

       configureDelete();

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

    public void configureDelete(){
        final ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (swipeDir == ItemTouchHelper.LEFT) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?");    //set message

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            try {

                                transactions.get(position).delete();
                                transactions.get(position).saveInBackground();
                                transactions.remove(position);  //then remove item
                                transactionAdapter.notifyItemRemoved(position);    //item removed from recylcerview
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }




                            return;
                        }

                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            transactionAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            transactionAdapter.notifyItemRangeChanged(position, transactionAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show();

                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTransactions);
    }

    public void populateTimeline() {
        ArrayList<ParselTransaction> pendingTransactions;
        try {
            parseUser = ParseUser.getCurrentUser().fetch();
        } catch (com.parse.ParseException e) {

        }

        if (parseUser != null) {
            transactions.clear();


            query();


        }
        }

    public void addItems(ParselTransaction transaction) {
        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);
        transactionAdapter.notifyDataSetChanged();
    }

    public void query(){
        ParseQuery<ParselTransaction> query = ParseQuery.getQuery(ParselTransaction.class);
        // Define our query conditions
        query.whereEqualTo("sender", parseUser.getUsername());
        List<Integer> list = new ArrayList<Integer>();
        list.add(0); list.add(1); list.add(2);
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
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(1); list2.add(2);

        query2.whereContainedIn("transactionState", list2);
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
        List<Integer> list3 = new ArrayList<Integer>();
        list3.add(7); list3.add(2);
        query3.whereContainedIn("transactionState", list3);
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
                            if (t1.getTransactionState() == 7 &&  t2.getTransactionState() == 2)return 1;
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



}
