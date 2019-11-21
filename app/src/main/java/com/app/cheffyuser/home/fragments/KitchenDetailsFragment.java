package com.app.cheffyuser.home.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.cheffyuser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenDetailsFragment extends Fragment {


    public KitchenDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kitchen_details, container, false);

        return view;
    }

}
