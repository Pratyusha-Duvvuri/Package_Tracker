package com.codepath.packagetwitter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
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
    int icon;
    int color;
    int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        defaultTextColor = tvKey.getTextColors().getDefaultColor();
        /*
        rbKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key_outline) {
                    key_outline = false;
                    icon = R.mipmap.ic_key_filled;
                    color = Color.BLACK;
                }
                else {
                    key_outline = true;
                    icon = R.mipmap.ic_key;
                    color = defaultTextColor;
                }
                rbKey.setButtonDrawable(icon);
                tvKey.setTextColor(color);
            }
        });
        */
        /*
        rbPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_outline) {
                    phone_outline = false;
                    icon = R.mipmap.ic_electronics_filled;
                    color = Color.BLACK;
                }
                else {
                    key_outline = true;
                    icon = R.mipmap.ic_electronics;
                    color = defaultTextColor;
                }
                rbPhone.setButtonDrawable(icon);
                tvPhone.setTextColor(color);
            }
        });
        */

        rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1 :
                        if (key_outline) {
                            key_outline = false;
                            icon = R.mipmap.ic_key_filled;
                            color = Color.BLACK;
                        }
                        else {
                            key_outline = true;
                            icon = R.mipmap.ic_key;
                            color = defaultTextColor;
                        }
                        rbKey.setButtonDrawable(icon);
                        tvKey.setTextColor(color);
                        break;

                    case R.id.rb2:
                        if (phone_outline) {
                            phone_outline = false;
                            icon = R.mipmap.ic_electronics_filled;
                            color = Color.BLACK;
                        }
                        else {
                            key_outline = true;
                            icon = R.mipmap.ic_electronics;
                            color = defaultTextColor;
                        }
                        rbPhone.setButtonDrawable(icon);
                        tvPhone.setTextColor(color);
                        break;
                }
            }
        });
        /*
        rgSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case rb1 :
                        if (key_outline) {
                            key_outline = false;
                            icon = R.mipmap.ic_key_filled;
                            color = Color.BLACK;
                        }
                        else {
                            key_outline = true;
                            icon = R.mipmap.ic_key;
                            color = defaultTextColor;
                        }
                        rbKey.setButtonDrawable(icon);
                        tvKey.setTextColor(color);
                        break;

                    case R.id.rb2:
                        if (phone_outline) {
                            phone_outline = false;
                            icon = R.mipmap.ic_electronics_filled;
                            color = Color.BLACK;
                        }
                        else {
                            key_outline = true;
                            icon = R.mipmap.ic_electronics;
                            color = defaultTextColor;
                        }
                        rbPhone.setButtonDrawable(icon);
                        tvPhone.setTextColor(color);
                        break;

                    default:
                        break;
                }
            }
        });
        */

    }
}
