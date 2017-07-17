package com.codepath.packagetwitter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.codepath.packagetwitter.Models.CourierModel;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.Models.Transaction;
import com.codepath.packagetwitter.Models.User;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by michaunp on 7/14/17.
 */

public class MatchActivity extends AppCompatActivity{

    User user = Parcels.unwrap(getIntent().getParcelableExtra("USER"));
    Sender sender = Parcels.unwrap(getIntent().getParcelableExtra("sender"));
    CourierModel courier = CourierModel.getRandomCourrier(this);
    TransactionAdapter transactionAdapter;
    RecyclerView.ViewHolder viewHolder;
    SwipeFlingAdapterView flingContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        //If user is a sender, inflate sender layout and do the following...
        if (user instanceof Sender) {
            setContentView(R.layout.sender_match_layout);
            //set up swipe fling container
            flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
            //declare possible matches and approved matches arrays
            ArrayList<Transaction> transactions;
            ArrayList<Transaction> approved_transactions;
            //get possible matches for sender
            transactions = Algorithm.getPossibleMatches(this, sender);
            transactionAdapter = new TransactionAdapter(transactions);


        }
        //otherwise inflate the courier layout and do the following...
        else {
            setContentView(R.layout.courier_match_layout);
            //set up swipe fling container
            flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
            //declare possible matches and approved matches arrays
            ArrayList<Transaction> transactions;
            ArrayList<Transaction> approved_transactions;
            //get possible matches for courier
            transactions = Algorithm.getPossibleMatches(this, courier);
        }

        super.onCreate(savedInstanceState, persistentState);
    }
}
