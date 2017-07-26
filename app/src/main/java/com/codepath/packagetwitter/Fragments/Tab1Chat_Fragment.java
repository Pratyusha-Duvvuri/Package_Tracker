package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.packagetwitter.ChatActivity;
import com.codepath.packagetwitter.ChatAdapter;
import com.codepath.packagetwitter.Message;
import com.codepath.packagetwitter.ProfileActivity;
import com.codepath.packagetwitter.R;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SubscriptionHandling;

import java.util.ArrayList;
import java.util.List;

import static com.codepath.packagetwitter.Message.TRANSACTION_ID_KEY;

/**
 * Created by pratyusha98 on 7/23/17.
 */

public class Tab1Chat_Fragment extends Fragment {

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    RecyclerView rvChat;
    ArrayList<Message> mMessages;
    ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;
    String transactionid;
    static final String TAG = ChatActivity.class.getSimpleName();
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    EditText etMessage;
    Button btSend;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionid = getActivity().getIntent().getStringExtra("ParselTransactionId");
    }
    void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //you want e to BE null
                if (e != null) {
                    Log.e("ParseApplicationError", "Anonymous login failed: ", e);
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.chatjsutincase, container, false);
        rvChat =  v.findViewById(R.id.rvChat);
        etMessage = (EditText) v.findViewById(R.id.etMessage1);
        btSend = (Button) v.findViewById(R.id.btSend1);
        //find swipe containerview
        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
        rvChat.setLayoutManager(llayout);
        mAdapter = new ChatAdapter(mMessages);

        //set the adapter
        rvChat.setAdapter(mAdapter);

//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeContainer.setRefreshing(true);
//                Toast.makeText(getContext(), "Refresh is working", Toast.LENGTH_LONG);
//                populateTimeline();
//                swipeContainer.setRefreshing(false);
//            }
//        });

        if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        } else {
            Log.d("Parse Application Error","Have no idea how you got here");
            login();
        }

        // Make sure the Parse server is setup to configured for live queries
        // URL for server is determined by Parse.initialize() call.
        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
// Define the class we would like to query
        ParseQuery<Message> parseQuery = ParseQuery.getQuery(Message.class);
        // This query can even be more granular (i.e. only refresh if the entry was added by some other user)
        parseQuery.whereEqualTo(TRANSACTION_ID_KEY, transactionid);

        // Connect to Parse server
        SubscriptionHandling<Message> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

        // Listen for CREATE events
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new
                SubscriptionHandling.HandleEventCallback<Message>() {
                    @Override
                    public void onEvent(ParseQuery<Message> query, Message object) {
                        mMessages.add(0, object);

                        // RecyclerView updates need to be run on the UI thread
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                rvChat.scrollToPosition(0);
                            }
                        });
                    }
                });
        return v;
    }

    // Get the userId from the cached currentUser object
    void startWithCurrentUser() {
        setupMessagePosting();
    }

    void setupMessagePosting() {
        // Find the text field and button

        mMessages = new ArrayList<>();
        mFirstLoad = true;
        final String userId = ProfileActivity.parseUser.getObjectId();
        mAdapter = new ChatAdapter( mMessages);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // to have reverse chronological order of messages
        linearLayoutManager.setReverseLayout(true);

        rvChat.setLayoutManager(linearLayoutManager);

        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();

                Message message = new Message();
                message.setBody(data);
                message.setUserId(ProfileActivity.parseUser.getObjectId());
                message.setUserName(ProfileActivity.parseUser.getString("username"));
                message.setPicture(ProfileActivity.parseUser.getParseFile("ImageFile"));
                message.setTransactionId(transactionid);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
//                            Toast.makeText(ChatActivity.this, "Successfully created message on Parse",
//                                    Toast.LENGTH_SHORT).show();
                            refreshMessages();}
                        else{
                            Log.d("Message  error",e.toString());


                        }
                    }
                });
                etMessage.setText(null);
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    void refreshMessages() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo(TRANSACTION_ID_KEY, transactionid);

        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }




}