package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {

    ArrayList<Transaction> transactions;
    TransactionAdapter adapter;
    RecyclerView rvTransactions;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        user = (User) getIntent().getParcelableExtra("USER");
        transactions = user.getTransactions();


        adapter = new TransactionAdapter(transactions);

        rvTransactions = (RecyclerView) findViewById(R.id.rvTransactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(adapter);



    }
}
