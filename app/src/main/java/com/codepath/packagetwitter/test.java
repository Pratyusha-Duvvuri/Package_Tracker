package com.codepath.packagetwitter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.packagetwitter.R.id.rb1;

public class test extends AppCompatActivity {

    @BindView(rb1) RadioButton rbKey;
    @BindView(R.id.rb2) RadioButton rbPhone;
    @BindView(R.id.rb3) RadioButton rbBook;
    @BindView(R.id.rb4) RadioButton rbFish;
    @BindView(R.id.rgSize) RadioGroup rgSize;
    @BindView(R.id.tv1) TextView tvKey;
    @BindView(R.id.tv2) TextView tvPhone;
    @BindView(R.id.tv3) TextView tvBook;
    @BindView(R.id.tv4) TextView tvFish;

    boolean key_outline = true;
    boolean phone_outline = true;
    int iconkey;
    int iconphone;
    int colorkey;
    int colorphone;
    int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        defaultTextColor = tvKey.getTextColors().getDefaultColor();



        rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                Log.d("WORK", String.valueOf(radioGroup.getId()));
                Log.d("WORK", String.valueOf(i));
                Log.d("WORK", String.valueOf(R.id.rb1));
                Log.d("WORK", String.valueOf(R.id.rb2));
                switch (i) {
                    case R.id.rb1 :

                        rbPhone.setChecked(false);
                        phone_outline = true;
                        iconphone = R.mipmap.ic_electronics;
                        colorphone = defaultTextColor;
                        rbBook.setChecked(false);
                        rbFish.setChecked(false);

                        if (key_outline) {
                            key_outline = false;
                            iconkey = R.mipmap.ic_key_filled;
                            colorkey = Color.BLACK;
                        }
                        else {
                            key_outline = true;
                            iconkey = R.mipmap.ic_key;
                            colorkey = defaultTextColor;
                        }
                        rbKey.setButtonDrawable(iconkey);
                        rbPhone.setButtonDrawable(iconphone);
                        tvKey.setTextColor(colorkey);
                        tvPhone.setTextColor(colorphone);
                        break;

                    case R.id.rb2:

                        rbKey.setChecked(false);
                        key_outline = true;
                        iconkey = R.mipmap.ic_key;
                        colorkey = defaultTextColor;
                        rbBook.setChecked(false);
                        rbFish.setChecked(false);

                        if (phone_outline) {
                            phone_outline = false;
                            iconphone = R.mipmap.ic_electronics_filled;
                            colorphone = Color.BLACK;
                        }
                        else {
                            phone_outline = true;
                            iconphone = R.mipmap.ic_electronics;
                            colorphone = defaultTextColor;
                        }
                        rbPhone.setButtonDrawable(iconphone);
                        rbKey.setButtonDrawable(iconkey);
                        tvKey.setTextColor(colorkey);
                        tvPhone.setTextColor(colorphone);
                        break;
                    default:
                        break;
                }
            }
        });


    }
}
