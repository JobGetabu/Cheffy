package com.app.cheffyuser.create_account.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.BottomNavActivity;
import com.app.cheffyuser.R;
import com.app.cheffyuser.create_account.model.Data;
import com.app.cheffyuser.create_account.model.LoginData;
import com.app.cheffyuser.create_account.model.UserModel;
import com.app.cheffyuser.networking.Constant;
import com.app.cheffyuser.networking.remote.ApiClient;
import com.app.cheffyuser.networking.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    private ApiInterface apiInterface;
    EditText etEmail, etPassword;

    private List<UserModel> userModel;
    private List<Data> userData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        Button btnLogin = view.findViewById(R.id.btn_login);

        // btnLogin.setShadowLayer(24,100,100, Color.RED);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login();


            }
        });
        return view;
    }


    private void login() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Login...");
        progressDialog.show();


        //defining the call
        // Call<Data> call = apiInterface.userLogin(email,password);


        LoginData loginData = new LoginData(email, password);
        Call<LoginData> call = apiInterface.userLogin(loginData);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {


                if (response.body()!=null && response.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                    Log.e("Success1", response.body().getToken());
                    String token=response.body().getToken();
                    Integer id=response.body().getData().getId();
                    String name=response.body().getData().getName();
                    String email=response.body().getData().getEmail();

                    String lat=response.body().getData().getLocationLat();
                    String lon=response.body().getData().getLocationLon();
                    String country_code=response.body().getData().getCountryCode();
                    String phone_no=response.body().getData().getPhoneNo();

                    String image_path=response.body().getData().getImagePath();

                    Toast.makeText(getActivity(), ""+id+" "+name+" "+email+" "+country_code, Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(Constant.SP_TOKEN, token);
                    editor.putInt(Constant.SP_ID, id);
                    editor.putString(Constant.SP_NAME, name);
                    editor.putString(Constant.SP_EMAIL, email);
                    editor.putString(Constant.SP_COUNTRY_CODE, country_code);
                    editor.putString(Constant.SP_PHONE_NO, phone_no);
                    editor.putString(Constant.SP_IMAGE_PATH, image_path);
                    editor.putString(Constant.SP_LOCATION_LAT, lat);
                    editor.putString(Constant.SP_LOCATION_LON, lon);



                    //Saving values to editor
                    editor.apply();




                    Intent intent = new Intent(getActivity(), BottomNavActivity.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(getContext(), "User or password is invalid!", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

                Log.d("onFailure", t.toString());

            }
        });


//        call.enqueue(new Callback<LoginData>() {
//            @Override
//            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
//                progressDialog.dismiss();
//
//                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<LoginData> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });


    }

}
