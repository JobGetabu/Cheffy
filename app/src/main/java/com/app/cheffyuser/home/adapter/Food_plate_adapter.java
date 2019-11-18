package com.app.cheffyuser.home.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.model.Food_Plate_Model;

import java.util.List;

public class Food_plate_adapter extends RecyclerView.Adapter<Food_plate_adapter.Holder>{

    private List<Food_Plate_Model> foodlist;

    public Food_plate_adapter(List<Food_Plate_Model> foodlist) {
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_foodother_plate,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

      /*  holder.foodimage.setImageResource(foodlist.get(position).getFood_image());
        holder.foodname.setText(foodlist.get(position).getFood_name());
        holder.foodtext.setText(foodlist.get(position).getFood_text());
        holder.foodprice.setText(foodlist.get(position).getFood_price());
        holder.foodtime.setText(foodlist.get(position).getFood_time());
        holder.fooddeliver.setText(foodlist.get(position).getFood_delivery_text());
        holder.foodcatdeliver.setText(foodlist.get(position).getFood_cat_deliver());*/
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodname,foodtext,foodprice,foodtime,fooddeliver, foodcatdeliver;
        Holder(@NonNull View itemView) {
            super(itemView);
            //foodimage = itemView.findViewById(R.id.foodimage);
            //foodname = itemView.findViewById(R.id.foodname1);
            //foodtext = itemView.findViewById(R.id.foodtext1);
            //foodprice = itemView.findViewById(R.id.foodprice1);
            //foodtime = itemView.findViewById(R.id.foodtime1);
            //fooddeliver = itemView.findViewById(R.id.foodstatus1);
            //foodcatdeliver = itemView.findViewById(R.id.food_cat_del1);
        }
    }

}





