package com.app.cheffyuser.create_account.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;


public class UserActivity extends AppCompatActivity {

    Button btnSignup;
    String type="driver";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnSignup=findViewById(R.id.btn_sign_up);




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(UserActivity.this,CreateAccountActivity.class);
                startActivity(intent);

            }
        });
    }
}


