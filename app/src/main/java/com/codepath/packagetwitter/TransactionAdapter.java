package com.codepath.packagetwitter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.packagetwitter.Models.ParselTransaction;

import org.parceler.Parcels;

import java.util.List;

import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by rafasj6 on 7/11/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context context;
    public List<ParselTransaction> mTransactions;
    public TransactionAdapter(List<ParselTransaction> transactions)
    {
        mTransactions = transactions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View transactionView = inflater.inflate(R.layout.item_transaction,parent, false);
        ViewHolder viewHolder = new ViewHolder(transactionView);

        return viewHolder;
    }

    //bind the values based on pos of elemt in list


    public void clear() {
        mTransactions.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<ParselTransaction> list) {
        mTransactions.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParselTransaction transaction = mTransactions.get(position);

        holder.tvTitle.setText(transaction.getString("title"));



        String status = "";
        switch (transaction.getTransactionState()) {

            case 0:
                status = "Awaiting Receiver";
                holder.ivStatus.setImageResource(R.drawable.orange_dot);
                holder.ivMessage.setVisibility(View.GONE);

                break;
            case 1:
                status = "Awaiting Match";
                holder.ivStatus.setImageResource(R.drawable.yellow_dot);
                holder.ivMessage.setVisibility(View.GONE);

                break;
            case 2:
                status = "Awating Delivery";
                holder.ivStatus.setImageResource(R.drawable.green_dot);
                holder.ivMessage.setVisibility(View.VISIBLE);
                break;
            case 4:
                status = "Package Delivered!";
                holder.ivStatus.setImageResource(R.drawable.green_dot);
                holder.ivMessage.setVisibility(View.GONE);

                break;
            case 7:
                status = "Awaiting Match";
                holder.ivStatus.setImageResource(R.drawable.yellow_dot);
                holder.tvTitle.setText("From: " + transaction.getSenderLoc() +"\nTo: " + transaction.getReceiverLoc());
                holder.tvTitle.setTextSize(13);
                holder.ivMessage.setVisibility(View.GONE);

                break;
            default:
                holder.ivMessage.setVisibility(View.GONE);

                break;

        }

        holder.tvStatus.setText(status);



        try{
             Uri imageUri = Uri.parse(transaction.getParseFile("ImageFile").getUrl());
            Glide.with(context).load(imageUri.toString()).into(holder.ivPackageImage);
        }

        catch (NullPointerException e) {
            holder.ivPackageImage.setImageResource(R.drawable.departures);
            //        holder.ivPackageImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //holder.ivPackageImage.setImageBitmap(transaction.getMail().getPicture());

    }
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

//create viewholder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivMessage;
        public ImageView ivPackageImage;
        public TextView tvStatus;
        public ImageView ivStatus;
        public TextView tvTitle;

        public  ViewHolder (View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            ivPackageImage = (ImageView) itemView.findViewById(R.id.ivPackageImage);
            ivStatus = (ImageView) itemView.findViewById(R.id.ivStatus);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivMessage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // gets item position
            Intent intent;
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                ParselTransaction transaction = mTransactions.get(position);
                // create intent for the new activity
                if (view == itemView && transaction.getTransactionState()!=7) {
                    intent = new Intent(context, TransactionDetailActivity.class);
//                    intent.putExtra("PARSEUSER", Parcels.wrap(parseUser) );

                    intent.putExtra("ParselTransactionId", transaction.getObjectId());


                    // show the activity
                    context.startActivity(intent);
                }
                else if (view == ivMessage){
                    Intent i = new Intent(context, OtherChatActivity.class);
                    i.putExtra("ParselTransactionId",transaction.getObjectId());
                    i.putExtra("PARSEUSER", Parcels.wrap(parseUser) );
                    //materialDesignFAM.close(true);
                    context.startActivity(i);
                }
            }
        }
    }

}
