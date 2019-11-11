package com.app.cheffyuser.cart.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.FoodTrackingActivity;
import com.app.cheffyuser.cart.adapter.TrackingOrderAdapter;


public class TrackingOrderFragment extends Fragment {

    String foodItemList[]={"Grilled salmon","Pasta Ham"};
    double foodPriceList[]={96.00,120.00};
    int imgList[]={R.drawable.image1,R.drawable.img_food_2};
    RecyclerView recyclerView;

    Button btnTrackOrder;


    public TrackingOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tracking_order, container, false);

               // get the reference of RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        // set a LinearLayoutManager with default Horizontal orientation
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        TrackingOrderAdapter customAdapter = new TrackingOrderAdapter(getActivity(),foodItemList,foodPriceList, imgList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView



        return view;
    }

}
