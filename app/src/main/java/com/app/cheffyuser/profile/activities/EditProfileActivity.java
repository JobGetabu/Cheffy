package com.app.cheffyuser.profile.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;
import com.app.cheffyuser.networking.Constant;
import com.app.cheffyuser.profile.utils.Utils;

public class EditProfileActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView txtSave,txtName;
    EditText etxtEmail,etxtPhone,etxtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        txtName=findViewById(R.id.txt_name);
        imgBack=findViewById(R.id.img_back);
        txtSave=findViewById(R.id.txt_save);
        etxtEmail=findViewById(R.id.etxt_email);
        etxtAddress=findViewById(R.id.etxt_address);
        etxtPhone=findViewById(R.id.etxt_phone);



        //Fetching cell from shared preferences
        SharedPreferences sp;
        sp =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sp.getString(Constant.SP_NAME, "N/A");
        String email = sp.getString(Constant.SP_EMAIL, "N/A");
        String image_path=sp.getString(Constant.SP_IMAGE_PATH,"N/A");
        String country_code=sp.getString(Constant.SP_COUNTRY_CODE,"N/A");
        String phone=sp.getString(Constant.SP_PHONE_NO,"N/A");

        String latitude=sp.getString(Constant.SP_LOCATION_LAT,"");
        String longitude=sp.getString(Constant.SP_LOCATION_LON,"");

        final String getAddress= Utils.getCurrentPlace(EditProfileActivity.this,latitude,longitude);


        txtName.setText(name);
        etxtEmail.setText(email);
        etxtPhone.setText(phone);
        etxtAddress.setText(getAddress);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(EditProfileActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                etxtEmail.setEnabled(false);
                etxtAddress.setEnabled(false);
                etxtPhone.setEnabled(false);
            }
        });


    }
}
