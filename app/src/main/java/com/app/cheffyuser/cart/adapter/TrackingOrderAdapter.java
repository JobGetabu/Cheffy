package com.app.cheffyuser.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.activities.MapTrackActivity;

public class TrackingOrderAdapter extends RecyclerView.Adapter<TrackingOrderAdapter.MyViewHolder>{

    Context context;
    private String foodItemList[];
    private double foodPriceList[];
    private int imgList[];

    //constructor
    public TrackingOrderAdapter(Context context, String foodItemList[],double foodPriceList[],int imgList[]) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.foodPriceList= foodPriceList;
        this.imgList=imgList;
    }



    @NonNull
    public TrackingOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        // infalte the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_track_order_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TrackingOrderAdapter.MyViewHolder vh = new TrackingOrderAdapter.MyViewHolder(view); // pass the view to View Holder
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull TrackingOrderAdapter.MyViewHolder myHolder, final int position) {

        myHolder.txtItemName.setText(foodItemList[position]);

        myHolder.txtItemName.setText(foodItemList[position]);
        myHolder.txtPrice.setText("$"+foodPriceList[position]);


        myHolder.imgFood.setImageResource(imgList[position]);
        // implement setOnClickListener event on item view.
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                //Toast.makeText(context, foodItemList[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MapTrackActivity.class);
                context.startActivity(intent);
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

        TextView txtItemName,txtPrice;
        ImageView imgFood;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtItemName =  itemView.findViewById(R.id.txt_item_name);
            txtPrice=  itemView.findViewById(R.id.txt_price_text);
            imgFood =  itemView.findViewById(R.id.img_food);
        }
    }
}