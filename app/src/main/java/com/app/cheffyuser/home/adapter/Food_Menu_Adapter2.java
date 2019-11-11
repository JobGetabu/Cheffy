package com.app.cheffyuser.home.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.model.FoodNearByModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class Food_Menu_Adapter2 extends RecyclerView.Adapter<Food_Menu_Adapter2.Holder>{

    private List<FoodNearByModel> foodlist;
    private Context context;

    public Food_Menu_Adapter2(List<FoodNearByModel> foodlist, Context context) {
        this.foodlist = foodlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_food_item_for_vertical,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        holder.foodname.setText(foodlist.get(position).getName());
        holder.foodrating.setText(foodlist.get(position).getRating());
        holder.foodtime.setText(foodlist.get(position).getDeliveryTime());
        //holder.fooddeliver.setText(foodlist.get(position).getFood_delivery_text());
        //holder.foodimage.setImageResource(foodlist.get(position).getFood_image());

        String url=foodlist.get(position).getImageURL();

        //Loading image from url into imageView
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.foodimage);

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodname,foodrating,foodtime,fooddeliver;
        Holder(@NonNull View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.foodname);
            foodrating = itemView.findViewById(R.id.ratings);
            foodtime = itemView.findViewById(R.id.times);
            fooddeliver = itemView.findViewById(R.id.deliverytext);
            foodimage = itemView.findViewById(R.id.foodimage);
        }
    }

}





