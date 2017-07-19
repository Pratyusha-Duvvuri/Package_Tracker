package com.codepath.packagetwitter.Fragments;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class TransactionList_Fragment extends android.support.v4.app.Fragment {

<<<<<<< HEAD
/*
    TransactionAdapter transactionAdapter;
    ArrayList<Transaction> transactions;
    RecyclerView rvTransactions;
    //SwipeRefreshLayout swipeContainer;
    //TwitterClient client;
    public static int page;
    //private EndlessRecyclerViewScrollListener scrollListener;


    //inflation happens inside onCreateView


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        Log.d("Trans list frag", "on create view");
        return v;
    }

    public void addItems(Transaction transaction) {

        transactions = new ArrayList<>();


        transactions.add(transaction);
        transactionAdapter.notifyItemInserted(transactions.size() - 1);


    }
    */
}
=======
//
//    TransactionAdapter transactionAdapter;
//    ArrayList<Transaction> transactions;
//    RecyclerView rvTransactions;
//    //SwipeRefreshLayout swipeContainer;
//    //TwitterClient client;
//    public static int page;
//    //private EndlessRecyclerViewScrollListener scrollListener;
//
//
//    //inflation happens inside onCreateView
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        //inflate the layout
//        View v = inflater.inflate(R.layout.fragments_transactions_list, container, false);
//
//
//        //find the recycler view and swipe containerview
//        rvTransactions = (RecyclerView) v.findViewById(R.id.rvTransactions);
//        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
//        transactions = new ArrayList<>();
//        // init the array list (data source)
//        //construct the adapter form this datasource
//        transactionAdapter = new TransactionAdapter(transactions);
//        //RecyclerView setup ( layout manager, use adapter)
//        //llayout= new LinearLayoutManager(getContext()) ;
//        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
//        rvTransactions.setLayoutManager(llayout);
//
//        //set the adapter
//
//        rvTransactions.setAdapter(transactionAdapter);
//        Log.d("Trans list frag", "on create view");
//        return v;
//    }
//
//    public void addItems(Transaction transaction) {
//
//        transactions = new ArrayList<>();
//
//
//        transactions.add(transaction);
//        transactionAdapter.notifyItemInserted(transactions.size() - 1);
//

    }
>>>>>>> 39708a770e3a96ba11cf0a4a59e09b68d2f41936
