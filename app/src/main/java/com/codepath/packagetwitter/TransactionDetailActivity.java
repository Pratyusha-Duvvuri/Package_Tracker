package com.codepath.packagetwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Transaction;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.ParseUser;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.R.drawable.user;

public class TransactionDetailActivity extends AppCompatActivity {

    public ParseUser parseUser;

    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvLength) TextView tvLength;
    @BindView(R.id.tvWidth) TextView tvWidth;
    @BindView(R.id.tvHeight) TextView tvHeight;
    @BindView(R.id.Type) TextView Type;
    @BindView(R.id.Weight) TextView tvWeight;
    @BindView(R.id.ivPackageImage) ImageView ivPackageImage;
    @BindView(R.id.tvFrom) TextView tvFrom;
    @BindView(R.id.tvTo) TextView tvTo;


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
        //int[] volume = transaction.getMail().getVolume().clone();
        int volume = transaction.getMail().getVolume();
        ivPackageImage.setImageBitmap(transaction.getMail().getPicture());
        tvDescription.setText(transaction.getMail().getDescription());
        Type.setText(transaction.getMail().getType());
        tvWeight.setText(String.valueOf(transaction.getMail().getWeight()));
        tvLength.setText(String.valueOf(volume));
        tvWidth.setText(String.valueOf(volume));
        tvHeight.setText(String.valueOf(volume));
        tvTo.setText(transaction.getSender().getUserName());
        tvFrom.setText("From: " + transaction.getSender().getLocation());

        if (transaction.getCourier().equals(null)) {
            materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.fab_exclaim));
            chatButton.setVisibility(View.GONE);
        } else {
            materialDesignFAM.getMenuIconView().setImageDrawable(getDrawable(R.drawable.email));
            matchButton.setVisibility(View.GONE);
        }




        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TransactionDetailActivity.this, ChatActivity.class);

                i.putExtra("courier", Parcels.wrap(user));
                i.putExtra("USER", Parcels.wrap(user));
                i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );

                startActivity(i);

            }
        });

    }
}
