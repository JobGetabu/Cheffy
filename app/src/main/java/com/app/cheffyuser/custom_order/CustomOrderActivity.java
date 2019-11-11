package com.app.cheffyuser.custom_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.cheffyuser.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomOrderActivity extends AppCompatActivity {

    Button btnPostCustomOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);

        FloatingActionButton fab=findViewById(R.id.fab_close);
        btnPostCustomOrder=findViewById(R.id.btn_post_custom_order);

        btnPostCustomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomOrderActivity.this,CustomOrderPostActivity.class);
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
