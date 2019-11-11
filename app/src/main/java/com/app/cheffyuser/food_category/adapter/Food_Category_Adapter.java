package com.app.cheffyuser.food_category.adapter;


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
import com.app.cheffyuser.food_category.model.FoodCategoryModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class Food_Category_Adapter extends RecyclerView.Adapter<Food_Category_Adapter.Holder>{

    private List<FoodCategoryModel> categoryList;
    private Context context;


    public Food_Category_Adapter(List<FoodCategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_food_category_by_country,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

       // holder.foodimage.setImageResource(categoryList.get(position).getImage();
        holder.foodname.setText(categoryList.get(position).getName());
        String url=categoryList.get(position).getUrl();

        //Loading image from url into imageView
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.foodimage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent a = new Intent(context, Food_Search.class);
//                context.startActivity(a);

                Toast.makeText(context, ""+categoryList.get(position).getName(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodname;
        Holder(@NonNull View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.itemimage);
            foodname = itemView.findViewById(R.id.itemname);

        }
    }

}





