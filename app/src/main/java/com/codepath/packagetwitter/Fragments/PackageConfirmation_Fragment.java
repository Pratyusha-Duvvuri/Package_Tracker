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

import org.parceler.Parcels;

import static org.parceler.Parcels.unwrap;

/**
 * Created by pratyusha98 on 7/12/17.
 */

//This is the confirmation page after all the details are entered for the package
public class PackageConfirmation_Fragment extends DialogFragment{
        //adding stuff for step 6


    Mail mail;
    Sender sender;
    Receiver receiver;
    Boolean proceed;
    private final int RESULT_OK = 10;
    private EditText mEditText;
    public TextView characterCount;
    public long num;
    public Button next;
    public Button back;

        // 1. Defines the listener interface with a method passing back data result.
        public interface SendDialogListener {
            void onFinishEditDialog(Sender sender, Receiver receiver, Mail mail, Boolean proceed);
        }
//        public interface NextDialogListener {
//        void onNextEditDialog(Sender sender, Receiver receiver, Mail mail);
//        }


        public PackageConfirmation_Fragment() {
            // Empty constructor is required for DialogFragment
            // Make sure not to add arguments to the constructor
            // Use `newInstance` instead as shown below
        }

        public static PackageConfirmation_Fragment newInstance(Mail mail, Sender sender, Receiver receiver) {
            PackageConfirmation_Fragment frag = new PackageConfirmation_Fragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("sender", Parcels.wrap(sender));
            bundle.putParcelable("receiver", Parcels.wrap(receiver));
            bundle.putParcelable("mail", Parcels.wrap(mail));
            frag.setArguments(bundle);
            return frag;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        receiver =  unwrap(getArguments().getParcelable("receiver"));
        sender =  unwrap(getArguments().getParcelable("sender"));
        mail =  unwrap(getArguments().getParcelable("mail"));

        return inflater.inflate(R.layout.fragment_package_confirmation, container, false);
    }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // Get field from view


            next = (Button) view.findViewById(R.id.btnNext);
            back = (Button) view.findViewById(R.id.btnBack);

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    proceed = false;

                    doThis();

                }
            });


            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    proceed = true;
                    doThis();
                }
            });


        }

    public void doThis(){

        SendDialogListener listener = (SendDialogListener) getActivity();
        listener.onFinishEditDialog(sender, receiver, mail, proceed);
        // Close the dialog and return back to the parent activity
        dismiss();
    }


    }






