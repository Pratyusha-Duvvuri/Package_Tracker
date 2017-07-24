package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.ParselTransaction;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.R.drawable.user;

public class TransactionDetailActivity extends AppCompatActivity {

    public ParseUser parseUser;

    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.Volume) TextView Volume;
    @BindView(R.id.Type) TextView Type;
    @BindView(R.id.Weight) TextView tvWeight;
    @BindView(R.id.ivPackageImage) ImageView ivPackageImage;
    @BindView(R.id.tvFrom) TextView tvFrom;
    @BindView(R.id.tvTo) TextView tvTo;


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
                                matchButton.setVisibility(View.GONE);

//                            if (transaction.getCourier() == null) {
//                                materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.fab_exclaim));
//                                chatButton.setVisibility(View.GONE);
//                            } else {
//                                materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.email));
//                                matchButton.setVisibility(View.GONE);
//                            }
                        }
                    }
        });

        //Transaction transaction = Parcels.unwrap(getIntent().getParcelableExtra("transaction"));
        //ivPackageImage.setImageBitmap(transaction.getMail().getPicture());

        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TransactionDetailActivity.this, ChatActivity.class);
                i.putExtra("ParselTransactionId",parselTransactionId);
                i.putExtra("courier", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));
                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );

                startActivity(i);

            }
        });

    }
}
