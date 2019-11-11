package com.app.cheffyuser.home.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.activities.FoodAddToCartActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlateReviewFragment extends Fragment {


    public PlateReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_plate_review, container, false);

        CardView cartButton=view.findViewById(R.id.cardbutton);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FoodAddToCartActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
