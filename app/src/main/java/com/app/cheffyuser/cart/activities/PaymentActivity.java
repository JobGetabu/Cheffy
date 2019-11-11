package com.app.cheffyuser.cart.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;

public class PaymentActivity extends AppCompatActivity {

    TextView txtTips1,txtTips2,txtTips5,txtTipsOther,txtNoTips;

    LinearLayout tipsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        txtTips1=findViewById(R.id.txt_tips_1);
        txtTips2=findViewById(R.id.txt_tips_2);
        txtTips5=findViewById(R.id.txt_tips_5);
        txtTipsOther=findViewById(R.id.txt_tips_other);
        txtNoTips=findViewById(R.id.txt_no_tip);
        tipsLayout=findViewById(R.id.tips_layout);


        txtTips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTips1.setBackgroundResource(R.color.red);
                txtTips2.setBackgroundResource(R.color.background_activity);
                txtTips5.setBackgroundResource(R.color.background_activity);
                txtTipsOther.setBackgroundResource(R.color.background_activity);
                txtNoTips.setBackgroundResource(R.color.background_activity);


                txtTips1.setTextColor(Color.WHITE);
                txtTips2.setTextColor(Color.BLACK);
                txtTips5.setTextColor(Color.BLACK);
                txtTipsOther.setTextColor(Color.BLACK);
                txtNoTips.setTextColor(Color.BLACK);
                tipsLayout.setBackgroundResource(R.drawable.round_border6);


            }
        });


        txtTips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTips1.setBackgroundResource(R.color.background_activity);
                txtTips2.setBackgroundResource(R.color.red);
                txtTips5.setBackgroundResource(R.color.background_activity);
                txtTipsOther.setBackgroundResource(R.color.background_activity);
                txtNoTips.setBackgroundResource(R.color.background_activity);


                txtTips2.setTextColor(Color.WHITE);
                txtTips1.setTextColor(Color.BLACK);
                txtTips5.setTextColor(Color.BLACK);
                txtTipsOther.setTextColor(Color.BLACK);
                txtNoTips.setTextColor(Color.BLACK);
                tipsLayout.setBackgroundResource(R.drawable.round_border6);
            }
        });


        txtTips5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTips1.setBackgroundResource(R.color.background_activity);
                txtTips5.setBackgroundResource(R.color.red);
                txtTips2.setBackgroundResource(R.color.background_activity);
                txtTipsOther.setBackgroundResource(R.color.background_activity);
                txtNoTips.setBackgroundResource(R.color.background_activity);


                txtTips5.setTextColor(Color.WHITE);
                txtTips1.setTextColor(Color.BLACK);
                txtTips2.setTextColor(Color.BLACK);
                txtTipsOther.setTextColor(Color.BLACK);
                txtNoTips.setTextColor(Color.BLACK);
                tipsLayout.setBackgroundResource(R.drawable.round_border6);

            }
        });


        txtTipsOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTips1.setBackgroundResource(R.color.background_activity);
                txtTipsOther.setBackgroundResource(R.color.red);
                txtTips2.setBackgroundResource(R.color.background_activity);
                txtTips5.setBackgroundResource(R.color.background_activity);
                txtNoTips.setBackgroundResource(R.color.background_activity);

                txtTipsOther.setTextColor(Color.WHITE);
                txtTips1.setTextColor(Color.BLACK);
                txtTips5.setTextColor(Color.BLACK);
                txtTips2.setTextColor(Color.BLACK);
                txtNoTips.setTextColor(Color.BLACK);
                tipsLayout.setBackgroundResource(R.drawable.round_border6);
            }
        });



        txtNoTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTips1.setBackgroundResource(R.color.background_activity);
                txtNoTips.setBackgroundResource(R.color.red);
                txtTips2.setBackgroundResource(R.color.background_activity);
                txtTipsOther.setBackgroundResource(R.color.background_activity);
                txtTips5.setBackgroundResource(R.color.background_activity);



                txtNoTips.setTextColor(Color.WHITE);
                txtTips1.setTextColor(Color.BLACK);
                txtTips5.setTextColor(Color.BLACK);
                txtTipsOther.setTextColor(Color.BLACK);
                txtTips2.setTextColor(Color.BLACK);
                tipsLayout.setBackgroundResource(R.drawable.round_border6);
            }
        });




    }
}
