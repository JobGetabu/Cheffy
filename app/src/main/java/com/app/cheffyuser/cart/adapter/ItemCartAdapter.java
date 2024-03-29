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

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.MyViewHolder>{
    Context context;
    private String foodItemList[];
    private double foodPriceList[];
    private int imgList[];

    //constructor
    public ItemCartAdapter(Context context, String foodItemList[],double foodPriceList[],int imgList[]) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.foodPriceList= foodPriceList;
        this.imgList=imgList;
    }



    @NonNull
    public ItemCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        // infalte the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_row_items2, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ItemCartAdapter.MyViewHolder vh = new ItemCartAdapter.MyViewHolder(view); // pass the view to View Holder
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull ItemCartAdapter.MyViewHolder myHolder, final int position) {

        myHolder.txtItemName.setText(foodItemList[position]);

        myHolder.txtItemName.setText(foodItemList[position]);
        myHolder.txtPrice.setText("$"+foodPriceList[position]);


        myHolder.imgFood.setImageResource(imgList[position]);
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

        TextView txtItemName,txtPrice,txtMinus,txtPlus,txtNumber;
        ImageView imgFood;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtItemName =  itemView.findViewById(R.id.txt_item_name);
            txtPrice=  itemView.findViewById(R.id.txt_price);
            txtMinus =  itemView.findViewById(R.id.txt_minus);
            txtPlus =  itemView.findViewById(R.id.txt_plus);
            txtNumber =  itemView.findViewById(R.id.txt_number);
            imgFood =  itemView.findViewById(R.id.img_food);
        }
    }
}
