package com.app.cheffyuser.profile.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.MainActivity;
import com.app.cheffyuser.R;
import com.app.cheffyuser.networking.Constant;
import com.app.cheffyuser.profile.activities.EditProfileActivity;
import com.app.cheffyuser.profile.activities.ShippingActivity;
import com.app.cheffyuser.profile.activities.UserPaymentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsSettingFragment extends Fragment {


    public AccountsSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accounts_setting, container, false);

        LinearLayout layoutProfileEdit=view.findViewById(R.id.layout_profile_edit);
        LinearLayout layoutPayment=view.findViewById(R.id.layout_payment);
        LinearLayout layoutShipping=view.findViewById(R.id.layout_shipping);
        LinearLayout layoutLogout=view.findViewById(R.id.layout_logout);



        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });


        layoutProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);

            }
        });


        layoutShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ShippingActivity.class);
                startActivity(intent);
            }
        });

        layoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserPaymentActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
