package com.codepath.packagetwitter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Transaction;

import java.util.List;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    //pass tweet array to constructor
    Context context;
    public List<Transaction> mTransactions;

    public TransactionAdapter(List<Transaction> transactions)
    {
        mTransactions = transactions;
    }

    //TwitterClient client = TwitterApp.getRestClient();



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_transaction,parent, false);

        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }





    //bind the valies based on pos of elemt in list


    public void clear() {
        mTransactions.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Transaction> list) {
        mTransactions.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = mTransactions.get(position);


        holder.tvUsername.setText(String.valueOf(transaction.getMail().getType()));
        holder.tvBoarding.setText(String.valueOf(transaction.getCourier().getTripStart()));
        holder.tvArrival.setText(String.valueOf(transaction.getCourier().getTripEnd()));
        holder.tvWeight.setText(String.valueOf(transaction.getMail().getVolume()));
        holder.ivPackageImage.setImageBitmap(transaction.getMail().getPicture());



    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

//create viewholder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivPackageImage;
        public TextView tvUsername;
        public TextView tvArrival;
        public TextView tvBoarding;
        public TextView tvWeight;

        public  ViewHolder (View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvArrival = (TextView) itemView.findViewById(R.id.tvArrival);
            tvBoarding = (TextView) itemView.findViewById(R.id.tvBoarding);
            tvWeight = (TextView) itemView.findViewById(R.id.tvWeight);

            ivPackageImage = (ImageView) itemView.findViewById(R.id.ivPackageImage);


        }



        @Override
        public void onClick(View view) {
            // gets item position
            Intent intent;
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Transaction transaction = mTransactions.get(position);

                // create intent for the new activity
                if (view == itemView) {
                    intent = new Intent(context, TransactionDetailActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                  //  intent.putExtra("user", transaction.user.sreenName);
                   // intent.putExtra("uid", String.valueOf(transaction.uid));


                // show the activity
                context.startActivity(intent);
            }
        }
    }
}

}