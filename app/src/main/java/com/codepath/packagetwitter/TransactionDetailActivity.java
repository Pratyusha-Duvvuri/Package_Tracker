package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Transaction;

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
        ButterKnife.bind(this);
        Transaction transaction = Parcels.unwrap(getIntent().getParcelableExtra("transaction"));
        tvDescription.setText(transaction.getMail().getDescription());
        Type.setText(transaction.getMail().getType());
        tvWeight.setText(String.valueOf(transaction.getMail().getWeight()));
    }
}
