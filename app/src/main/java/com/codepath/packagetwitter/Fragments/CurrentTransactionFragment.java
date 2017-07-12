package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by pratyusha98 on 7/12/17.
 */

public class CurrentTransactionFragment extends TransactionList_Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // populateTimeline();


    }
    //    //this is for the intermediate progress bar
    MenuItem miActionProgressItem;
   // ProgressBar v;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
       // miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
       // v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        //populateTimeline();

        //return super.onPrepareOptionsMenu(menu);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }


    private void populateTimeline(){

        transactionAdapter.notifyDataSetChanged();
        //     showProgressBar();

//        client.getHomeTimeline( new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                //Log.d("TwitterClient", response.toString() )    ;
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//
//                addItems(response);
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("TwitterClient", responseString )    ;
//                throwable.printStackTrace();
//            }
//
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString() )    ;
//                throwable.printStackTrace();            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString() )    ;
//                throwable.printStackTrace();             }
//        });
//        //  hideProgressBar();

    }

}
