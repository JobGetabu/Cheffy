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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_food_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

//        holder.foodstatus.setText("Available");
        //holder.foodstatus.setText(foodlist.get(position).getFood_status());
        holder.foodname.setText(foodlist.get(position).getFood_name());

        holder.foodrating.setText(foodlist.get(position).getFood_rating());

        holder.foodrating.setText(foodlist.get(position).getFood_rating());
        holder.foodtime.setText(foodlist.get(position).getFood_time());
        holder.fooddeliver.setText(foodlist.get(position).getFood_delivery_text());
        holder.foodimage.setImageResource(foodlist.get(position).getFood_image());



//        //get size of list
//        int getSize = +foodlist.get(position).getPlateImages().size();
//        Log.d("size", "" + foodlist.get(position).getPlateImages().size());
//
//        if (getSize > 0) {
//
//            plateImages = foodlist.get(position).getPlateImages();
//            String url = plateImages.get(0).getUrl();
//
//            //Loading image from url into imageView
//            Glide.with(context)
//                    .load(url)
//                    .placeholder(R.drawable.image_placeholder)
//                    .error(R.drawable.image_placeholder)
//                    .into(holder.foodimage);
//        }
//
//
//        holder.txtEditPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EditPostActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodstatus, foodname, foodrating, foodtime, fooddeliver, txtEditPost;

        Holder(@NonNull View itemView) {
            super(itemView);
           // foodstatus = itemView.findViewById(R.id.status);
            foodname = itemView.findViewById(R.id.foodname);
            foodrating = itemView.findViewById(R.id.ratings);
            foodtime = itemView.findViewById(R.id.times);
            fooddeliver = itemView.findViewById(R.id.deliverytext);
            foodimage = itemView.findViewById(R.id.foodimage);
           // txtEditPost = itemView.findViewById(R.id.txt_edit_post);
        }
    }

}





