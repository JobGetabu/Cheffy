package com.app.cheffyuser.profile.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.activities.AddCardActivity;
import com.app.cheffyuser.create_account.activities.CreateAccountActivity;
import com.app.cheffyuser.profile.activities.EditProfileActivity;
import com.app.cheffyuser.profile.activities.ShippingActivity;

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
        View view = inflater.inflate(R.layout.fragment_accounts_setting, container, false);

        LinearLayout layoutProfileEdit = view.findViewById(R.id.layout_profile_edit);
        LinearLayout layoutPayment = view.findViewById(R.id.layout_payment);
        LinearLayout layoutShipping = view.findViewById(R.id.layout_shipping);
        LinearLayout layoutLogout = view.findViewById(R.id.layout_logout);


        layoutLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
            startActivity(intent);

        });


        layoutProfileEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);

        });


        layoutShipping.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ShippingActivity.class);
            startActivity(intent);
        });

        layoutPayment.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddCardActivity.class);
            startActivity(intent);
        });
        return view;
    }

}
