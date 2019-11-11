package com.app.cheffyuser.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;

public class CustomOrderListAdapter extends RecyclerView.Adapter<CustomOrderListAdapter.MyViewHolder> {


    Context context;
    private String foodItemList[];
    private int imgList[];

    //constructor
    public CustomOrderListAdapter(Context context, String foodItemList[], double foodPriceList[], int imgList[]) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.imgList=imgList;
    }



    @NonNull
    public CustomOrderListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        // infalte the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_list_row_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
       CustomOrderListAdapter.MyViewHolder vh = new CustomOrderListAdapter.MyViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myHolder, final int position) {



        myHolder.txtChefName.setText(foodItemList[position]);

        //myHolder.txtI.setText(foodItemList[position]);



//        myHolder.imgFood.setImageResource(imgList[position]);
        // implement setOnClickListener event on item view.
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, foodItemList[position], Toast.LENGTH_SHORT).show();
            }
        });
    }


    //for item count
    @Override
    public int getItemCount() {
        return foodItemList.length;
    }


    //create class
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtChefName,txtOrderText,txtOrder;
        ImageView imgFood;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtChefName =  itemView.findViewById(R.id.txt_chef_name);

            }
    }
}


