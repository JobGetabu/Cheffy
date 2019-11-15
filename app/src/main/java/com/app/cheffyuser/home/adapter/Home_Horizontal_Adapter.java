package com.app.cheffyuser.home.adapter;


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
import com.app.cheffyuser.home.activities.PostFoodActivity;
import com.app.cheffyuser.home.model.Home_Horizontal_Model;

import java.util.List;

public class Home_Horizontal_Adapter extends RecyclerView.Adapter<Home_Horizontal_Adapter.Holder>{


    private List<Home_Horizontal_Model> foodlist;
    private Context context;

    public Home_Horizontal_Adapter(List<Home_Horizontal_Model> foodlist, Context context) {
        this.foodlist = foodlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_horizontal,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        holder.title.setText(foodlist.get(position).getText());
        holder.icon.setImageResource(foodlist.get(position).getImageView());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.getAdapterPosition() == 0)
                {
                    context.startActivity(new Intent(context, PostFoodActivity.class));
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textview);
            icon = itemView.findViewById(R.id.imageview);
        }
    }

}





