package com.app.cheffyuser.custom_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.app.cheffyuser.R;

public class CustomOrderPostActivity extends AppCompatActivity {

    Button btnPostOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order_post);


        btnPostOrder=findViewById(R.id.btn_post_order);



        btnPostOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomOrderPostActivity.this,OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

    }
}
