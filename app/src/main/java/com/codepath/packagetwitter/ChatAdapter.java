package com.codepath.packagetwitter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

import static com.codepath.packagetwitter.Fragments.Tab1Chat_Fragment.thisUser1;
import static com.codepath.packagetwitter.Fragments.Tab2Chat_Fragment.thisUser2;
import static com.codepath.packagetwitter.ProfileActivity.parseUser;

/**
 * Created by pratyusha98 on 7/14/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Message> mMessages;
    private Context mContext;
    private String mUserId;
    private Integer tabNo;

    public ChatAdapter(Context context, String userId, List<Message> messages, Integer tabNum) {
        mMessages = messages;
        this.mUserId = userId;
        mContext = context;
        tabNo=tabNum;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_chat, parent, false);


        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Message message = mMessages.get(position);
        final boolean isMe = message.getUserId() != null && message.getUserId().equals(mUserId);
        ParseFile postImage;
        Uri imageUri;
        if (isMe) {
            postImage = parseUser.getParseFile("ImageFile");
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            String imageUrl = postImage.getUrl();//live url
             imageUri = Uri.parse(imageUrl);

            //put my image from parse user
        } else {
            if(tabNo==0)
                postImage = thisUser1.getParseFile("ImageFile");
            else
                postImage = thisUser2.getParseFile("ImageFile");

            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            //load image from messages
            String imageUrl = postImage.getUrl();//live url
             imageUri = Uri.parse(imageUrl);
        }
        //change this code to reflect user now
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
        Glide.with(holder.imageOther.getContext()).load(imageUri.toString()).into(profileView);
        holder.body.setText(message.getBody());
    }



    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageOther;
        ImageView imageMe;
        TextView body;

        public ViewHolder(View itemView) {
            super(itemView);
            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);
            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
            body = (TextView)itemView.findViewById(R.id.tvBody);
        }
    }
}
