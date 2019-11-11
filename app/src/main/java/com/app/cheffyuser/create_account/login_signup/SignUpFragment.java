package com.app.cheffyuser.create_account.login_signup;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.R;
import com.app.cheffyuser.create_account.VerifyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextView txtSignUpNext=view.findViewById(R.id.txt_next);

        txtSignUpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), VerifyActivity.class);
                startActivity(intent);
            }
        });

       return view;
    }

}
