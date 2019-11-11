package com.app.cheffyuser.cart.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.activities.CustomOrderCheckoutActivity;
import com.app.cheffyuser.cart.activities.ItemCartActivity;
import com.app.cheffyuser.cart.adapter.CustomOrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomOrderFragment extends Fragment {


    public CustomOrderFragment() {
        // Required empty public constructor
    }

    String foodItemList[]={"Grilled salmon","Pasta Ham  ",};
    double foodPriceList[]={96.00,120.00};
    int imgList[]={R.drawable.ic_item_1,R.drawable.ic_item_2};
    RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_custom_order, container, false);

        LinearLayout itemCart=view.findViewById(R.id.layout_custom_order_cart);

        // get the reference of RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);


        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        // set a LinearLayoutManager with default Horizontal orientation
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomOrderAdapter customAdapter = new CustomOrderAdapter(getActivity(),foodItemList,foodPriceList, imgList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView



        itemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CustomOrderCheckoutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
