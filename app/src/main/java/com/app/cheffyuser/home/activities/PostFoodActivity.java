package com.app.cheffyuser.home.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cheffyuser.R;

public class PostFoodActivity extends AppCompatActivity {

    LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_food);

        parentLinearLayout = findViewById(R.id.parent_linear_layout);
    }



    public void upload_food_plate(View view) {

        startActivity(new Intent(PostFoodActivity.this, UploadFoodPlateImageActivity.class));
    }

    public void upload_food_kitchen(View view) {

       startActivity(new Intent(PostFoodActivity.this, UploadFoodKitchenImageActivity.class));
    }

    public void upload_receipts(View view) {

        startActivity(new Intent(PostFoodActivity.this, UploadFoodReceiptActivity.class));
    }

    public void onAddField(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.custom_row, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View view) {
        parentLinearLayout.removeView((View) view.getParent());
    }
}


