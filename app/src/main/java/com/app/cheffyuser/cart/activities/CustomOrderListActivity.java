package com.app.cheffyuser.cart.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.adapter.CustomOrderListAdapter;

public class CustomOrderListActivity extends AppCompatActivity {

    String foodItemList[]={"Cheffy","Chefyy",};
    double foodPriceList[]={96.00,120.00};
    int imgList[]={R.drawable.ic_item_1,R.drawable.ic_item_2};
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order_list);



        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.recycler_view);


        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // set a LinearLayoutManager with default Horizontal orientation
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomOrderListAdapter customAdapter = new CustomOrderListAdapter(this,foodItemList,foodPriceList, imgList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView


    }
}
