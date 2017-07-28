package com.codepath.packagetwitter.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;
import com.codepath.packagetwitter.R;

/**
 * Created by rafasj6 on 7/27/17.
 */

public class NewPackageFragment extends DialogFragment {
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
    public FrameLayout flForm;
    public EditText receiverLocation;
    public EditText receiverEndDate;
    public LinearLayout llRequest;




    // 1. Defines the listener interface with a method passing back data result.
    public interface SendResultListener {
        void onFinishEditDialog( Boolean proceed);
    }


    public NewPackageFragment() {

        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pending_request, container, false);

    }



    public void doThis(){

        SendResultListener listener = (SendResultListener) getActivity();
        listener.onFinishEditDialog( proceed);
        // Close the dialog and return back to the parent activity
        dismiss();
    }
}
