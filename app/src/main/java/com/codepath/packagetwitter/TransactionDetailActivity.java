package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Transaction;
import com.github.clans.fab.FloatingActionMenu;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionDetailActivity extends AppCompatActivity {


    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvLength) TextView tvLength;
    @BindView(R.id.tvWidth) TextView tvWidth;
    @BindView(R.id.tvHeight) TextView tvHeight;
    @BindView(R.id.Type) TextView Type;
    @BindView(R.id.Weight) TextView tvWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton matchButton;
        com.github.clans.fab.FloatingActionButton chatButton;
        ButterKnife.bind(this);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        matchButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_matches);
        chatButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_chat);
        Transaction transaction = Parcels.unwrap(getIntent().getParcelableExtra("transaction"));
        tvDescription.setText(transaction.getMail().getDescription());
        Type.setText(transaction.getMail().getType());
        tvWeight.setText(String.valueOf(transaction.getMail().getWeight()));

        if (transaction.getCourier().equals(null)) {
            materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.fab_exclaim));
            chatButton.setVisibility(View.GONE);
        }
        else {
            materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.email));
            matchButton.setVisibility(View.GONE);
        }
        /*
        matchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent( ProfileActivity.this,PackageCreationActivity.class);
                i.putExtra("sender", Parcels.wrap(user) );
                i.putExtra("USER", Parcels.wrap(user) );

                startActivity(i);

            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CourierActivity.class);

                i.putExtra("courier",Parcels.wrap(user) );
                i.putExtra("USER", Parcels.wrap(user) );

                startActivity(i);
            }
        });
        */
    }
}
