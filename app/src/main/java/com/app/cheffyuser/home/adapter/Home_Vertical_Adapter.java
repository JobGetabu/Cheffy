package com.app.cheffyuser.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.model.Home_Vertical_Model;

import java.util.List;

public class Home_Vertical_Adapter extends RecyclerView.Adapter<Home_Vertical_Adapter.Holder>{


    private List<Home_Vertical_Model> foodlist;

    public Home_Vertical_Adapter(List<Home_Vertical_Model> foodlist) {
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_vertical,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.name.setText(foodlist.get(position).getName());
        holder.price.setText(foodlist.get(position).getPrice());
        holder.quantity.setText(foodlist.get(position).getQuantity());
        holder.icon.setImageResource(foodlist.get(position).getImageView());

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name,price,quantity;
        ImageView icon;
        Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.v_foodname);
            price = itemView.findViewById(R.id.v_foodprice);
            quantity = itemView.findViewById(R.id.v_qtn);
            icon = itemView.findViewById(R.id.v_imageview);
        }
    }

}





