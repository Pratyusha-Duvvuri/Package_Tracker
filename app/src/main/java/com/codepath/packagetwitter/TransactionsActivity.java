package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.packagetwitter.Models.Transaction;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {

    ArrayList<Transaction> transactions;
    TransactionAdapter adapter;
    RecyclerView rvTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        transactions = new ArrayList<>();

        adapter = new TransactionAdapter(transactions);

        rvTransactions = (RecyclerView) findViewById(R.id.rvTransactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(adapter);

        //get config

    }
}
