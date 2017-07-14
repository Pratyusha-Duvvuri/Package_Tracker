package com.codepath.packagetwitter.Fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.R;

import org.parceler.Parcels;

import static org.parceler.Parcels.unwrap;

/**
 * Created by pratyusha98 on 7/14/17.
 */

public class PendingRequest_Fragment extends DialogFragment {



    Mail mail;
    Sender sender;
    Receiver receiver;
    Boolean proceed;
    private final int RESULT_OK = 10;
    private EditText mEditText;
    public TextView characterCount;
    public long num;
    public Button accept;
    public Button reject;



    // 1. Defines the listener interface with a method passing back data result.
    public interface SendResultListener {
        void onFinishEditDialog(Sender sender, Mail mail, Boolean proceed);
    }


    public PendingRequest_Fragment() {

        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
    }

    public static PendingRequest_Fragment newInstance(Mail mail, Sender sender) {
        PendingRequest_Fragment frag = new PendingRequest_Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("sender", Parcels.wrap(sender));
        bundle.putParcelable("mail", Parcels.wrap(mail));
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sender =  unwrap(getArguments().getParcelable("sender"));
        mail =  unwrap(getArguments().getParcelable("mail"));

        return inflater.inflate(R.layout.fragment_pending_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        accept = (Button) view.findViewById(R.id.btnAccept);
        reject = (Button) view.findViewById(R.id.btnReject);

        reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                proceed = false;

                doThis();

            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                proceed = true;
                doThis();
            }
        });

    }

    public void doThis(){

        SendResultListener listener = (SendResultListener) getActivity();
        listener.onFinishEditDialog(sender, mail, proceed);
        // Close the dialog and return back to the parent activity
        dismiss();
    }

}
