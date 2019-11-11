package com.app.cheffyuser.create_account.login_signup;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginSignupMainFragment extends Fragment {


    public LoginSignupMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_signup_main, container, false);
        return view;
    }

}
