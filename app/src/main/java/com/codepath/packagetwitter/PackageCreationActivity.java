package com.codepath.packagetwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.packagetwitter.Models.Mail;
import com.codepath.packagetwitter.Models.Receiver;
import com.codepath.packagetwitter.Models.Sender;

public class PackageCreationActivity extends AppCompatActivity {

String sender_handle;
    Receiver receiver;
    Sender sender;
    Mail mail;
    /*
    @BindView(R.id.etWeight)EditText weight;
    @BindView(R.id.etLength)EditText lenght;
    @BindView(R.id.etWidth)EditText width;
    @BindView(R.id.etHeight)EditText height;
    @BindView(R.id.etDescription)EditText description;
    @BindView(R.id.etSenderLocation)EditText senderLocation;
    @BindView(R.id.etStartDate)EditText startDate;
    @BindView(R.id.etEndDate)EditText endDate;
    @BindView(R.id.rbFragile)RadioButton isFragile;
    @BindView(R.id.rbFragile)RadioButton notFragile;
    @BindView(R.id.spPackageType)Spinner type;
    @BindView(R.id.tvSenderLocation)TextView tvSenderLocation;
    @BindView(R.id.tvStartDate)TextView tvStartDate;
    @BindView(R.id.tvEndDate)TextView tvEndDate;
    @BindView(R.id.tvWeight)TextView tvWeight;
    @BindView(R.id.tvHeight)TextView tvHeight;
    @BindView(R.id.tvWidth)TextView tvWidth;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_creation);

        /*
        receiver = getIntent().getParcelableExtra("receiver");
        sender = getIntent().getParcelableExtra("sender");
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mail.setDescription(description.getText().toString());
                mail.setFragile(true);//cbIsFragile.isChecked());
                //have to set picture
                mail.setSpecialRequirements(specialRequirements.getText().toString());
                mail.setWeight(Double.parseDouble(weight.getText().toString()));
                mail.setType(type.getSelectedItem().toString());

                //Call the modal to verify information
                //onVerifyAction

            }
        });


        */
    }

    /*
    public void onVerifyAction(MenuItem mi) {
        //setContentView(R.layout.fragment_edit_name);
        //Tweet tweet = new Tweet();

//        FragmentManager fm = getSupportFragmentManager();
//        PackageConfirmation_Fragment editNameDialogFragment = PackageCreationActivity.newInstance("Some Title",tweet);
//       tweet editNameDialogFragment.show(fm, "fragment_edit_name");
    }
    */

    }


