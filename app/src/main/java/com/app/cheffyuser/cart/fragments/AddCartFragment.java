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
import com.app.cheffyuser.cart.activities.ItemCartActivity;
import com.app.cheffyuser.cart.adapter.AddCartAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCartFragment extends Fragment {

    String foodItemList[]={"Grilled salmon","Pasta Ham  ",};
    double foodPriceList[]={96.00,120.00};
    int imgList[]={R.drawable.ic_item_1,R.drawable.ic_item_2};
    RecyclerView recyclerView;
    LinearLayout layoutItemCart;



    public AddCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_cart, container, false);

        // get the reference of RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutItemCart=view.findViewById(R.id.layout_item_cart);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        // set a LinearLayoutManager with default Horizontal orientation
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        AddCartAdapter customAdapter = new AddCartAdapter(getActivity(),foodItemList,foodPriceList, imgList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView


        layoutItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ItemCartActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
