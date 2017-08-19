package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.packagetwitter.R;

/**
 * Created by pratyusha98 on 7/14/17.
 */

public class PendingRequest_Fragment extends DialogFragment {

    Boolean proceed;
    public Button accept;
    boolean pendingRequest = true;


    // 1. Defines the listener interface with a method passing back data result.
    public interface SendResultListener {
        void onFinishEditDialog( Boolean proceed);
    }


    public PendingRequest_Fragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
    }


    public static PendingRequest_Fragment newInstance(String message) {
        PendingRequest_Fragment frag = new PendingRequest_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending_request, container, false);
        try{
        String message  = getArguments().getString("message");

            //if there is a message diff than defaullt
            //  set the modals msg as such
            ((TextView)v.findViewById(R.id.lbl_your_name)).setText(message);
            ((Button) v.findViewById(R.id.btnAccept)).setText("OK"); // and btn becomes ok
            pendingRequest = false;
        }
        catch (NullPointerException e){}
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        accept = (Button) view.findViewById(R.id.btnAccept);




        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                proceed = true;
                doThis();
            }
        });

    }

    public void doThis(){

        SendResultListener listener = (SendResultListener) getActivity();
        if(pendingRequest)listener.onFinishEditDialog( proceed);
        // Close the dialog and return back to the parent activity
        dismiss();
    }

}
