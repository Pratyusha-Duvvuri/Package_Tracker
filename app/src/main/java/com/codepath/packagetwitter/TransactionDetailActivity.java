package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionDetailActivity extends AppCompatActivity {

    public ParseUser parseUser;

    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.Volume) TextView Volume;
    @BindView(R.id.Type) TextView Type;
    @BindView(R.id.Weight) TextView tvWeight;
    @BindView(R.id.ivPackageImage) ImageView ivPackageImage;
    @BindView(R.id.tvFrom) TextView tvFrom;
    @BindView(R.id.tvTo) TextView tvTo;
    @BindView(R.id.btnBack) ImageButton btnBack;
    @BindView(R.id.tvTitle1) TextView tvTitle1;
    @BindView(R.id.tvTitle2) TextView tvTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_test);
        final FloatingActionMenu materialDesignFAM;
        final com.github.clans.fab.FloatingActionButton matchButton;
        final com.github.clans.fab.FloatingActionButton chatButton;
        ButterKnife.bind(this);
        //find toolbar inside activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //sets toolbar to act as the actionbar
        setSupportActionBar(toolbar);
        //sets up toolbar title
        getSupportActionBar().setTitle(null);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        matchButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_matches);
        chatButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_chat);
        final String parselTransactionId = getIntent().getStringExtra("ParselTransactionId");
        ParseQuery<ParselTransaction> transactionQuery = ParseQuery.getQuery(ParselTransaction.class);
        // First try to find from the cache and only then go to network
        transactionQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        transactionQuery.getInBackground(parselTransactionId, new GetCallback<ParselTransaction>() {
                    public void done(ParselTransaction transaction, ParseException e) {
                        if (e == null) {
                            // item was found
                            tvDescription.setText(transaction.getMailDescription());
                            Type.setText(transaction.getMailType());
                            tvFrom.setText("From: " + transaction.getSenderLoc());
                            tvTo.setText("To: " + transaction.getReceiverLoc());
                            tvWeight.setText(String.valueOf(transaction.getWeight()));
                            Volume.setText(String.valueOf(transaction.getVolume()));
                            //not using match activity and button for now
                            matchButton.setVisibility(View.GONE);
                            if (transaction.getTransactionState() == 2) {
                                ParseQuery<ParseUser> senderquery = ParseUser.getQuery();
                                //senderquery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
                                senderquery.whereEqualTo("username", transaction.getSender());
                                senderquery.findInBackground(new FindCallback<ParseUser>() {
                                    @Override
                                    public void done(List<ParseUser> userList, ParseException e) {

                                        ParseUser user = null;
                                        String sender_name;
                                        if (e == null) {
                                            user = userList.get(0);
                                            sender_name = user.get("fullName").toString();
                                            tvTitle1.setText(sender_name.toString() + "'s package ");
                                        }
                                    }
                                });

                                ParseQuery<ParseUser> receiverquery = ParseUser.getQuery();
                                receiverquery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
                                receiverquery.whereEqualTo("username", transaction.getReceiver());
                                receiverquery.findInBackground(new FindCallback<ParseUser>() {
                                    @Override
                                    public void done(List<ParseUser> userList, ParseException e) {

                                        ParseUser user = null;
                                        String receiver_name;
                                        if (e == null) {
                                            user = userList.get(0);
                                            receiver_name = user.get("fullName").toString();
                                            tvTitle2.setText("to " + receiver_name.toString());
                                        }
                                    }
                                });
                            }
                            else {
                                tvTitle1.setText("This Transaction is ");
                                tvTitle2.setText("still pending...");
                            }
                        }
                    }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TransactionDetailActivity.this, OtherChatActivity.class);
                i.putExtra("ParselTransactionId",parselTransactionId);
                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );

                startActivity(i);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
