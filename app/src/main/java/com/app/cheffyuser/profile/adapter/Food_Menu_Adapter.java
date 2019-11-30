package com.app.cheffyuser.profile.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.profile.model.Food_Menu_Model;

import java.util.List;

public class Food_Menu_Adapter extends RecyclerView.Adapter<Food_Menu_Adapter.Holder> {


    private List<Food_Menu_Model> foodlist;

    private Context context;

    public Food_Menu_Adapter(List<Food_Menu_Model> foodlist, Context context) {
        this.foodlist = foodlist;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fav_fooditem, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        holder.foodname.setText(foodlist.get(position).getFood_name());

        holder.foodimage.setImageResource(foodlist.get(position).getFood_image());

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodname;

        Holder(@NonNull View itemView) {
            super(itemView);
            // foodstatus = itemView.findViewById(R.id.status);
            foodname = itemView.findViewById(R.id.foodname);
            foodimage = itemView.findViewById(R.id.foodimage);
        }
    }

}





