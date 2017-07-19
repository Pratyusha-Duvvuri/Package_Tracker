package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

/**
 * Created by pratyusha98 on 7/18/17.
 */

public class LogoutFragment extends DialogFragment {

    //adding stuff for step 6


    Mail mail;
    Sender sender;
    Receiver receiver;
    Boolean proceed;
    private final int RESULT_OK = 10;
    private EditText mEditText;
    public TextView characterCount;
    public long num;
    public Button accept;

    // 1. Defines the listener interface with a method passing back data result.
    public interface SendDialogListener {
        void onFinishEditDialog();
    }
//        public interface NextDialogListener {
//        void onNextEditDialog(Sender sender, Receiver receiver, Mail mail);
//        }


    public LogoutFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static LogoutFragment newInstance() {
        LogoutFragment frag = new LogoutFragment();
        Bundle bundle = new Bundle();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view


        accept = (Button) view.findViewById(R.id.acceptLogout);
        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                proceed = true;
                doThis();
            }
        });


    }

    public void doThis(){

        SendDialogListener listener = (SendDialogListener) getActivity();
        listener.onFinishEditDialog();
        // Close the dialog and return back to the parent activity
        dismiss();
    }

}
