package com.app.cheffyuser.home.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;
import com.app.cheffyuser.create_account.activities.CreateAccountActivity;

public class MainActivity extends AppCompatActivity {


    Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=findViewById(R.id.btn_login);
        btnSignup=findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(v -> {

            Intent intent =new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });



        btnSignup.setOnClickListener(v -> {

            Intent intent =new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);

        });





    }
}
