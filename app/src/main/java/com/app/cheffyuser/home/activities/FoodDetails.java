package com.app.cheffyuser.home.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.adapter.Main3TabsAdapter;
import com.app.cheffyuser.home.adapter.ViewPagerAdapter;
import com.app.cheffyuser.home.fragments.KitchenFragment;
import com.app.cheffyuser.home.fragments.PlateFragment;
import com.app.cheffyuser.home.fragments.ReceiptFragment;
import com.google.android.material.tabs.TabLayout;

public class FoodDetails extends AppCompatActivity {




    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imgFullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        imgFullImage=findViewById(R.id.full_imageview);



        TabLayout tabLayout =  findViewById(R.id.food_details_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("The Plate"));
        tabLayout.addTab(tabLayout.newTab().setText("Kitchen"));
        tabLayout.addTab(tabLayout.newTab().setText("Receipts"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        final ViewPager viewPager =(ViewPager)findViewById(R.id.food_view_pager);
        Main3TabsAdapter tabsAdapter = new Main3TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition()==0)
                {
                    imgFullImage.setImageResource(R.drawable.details_background);
                }

                if (tab.getPosition()==1)
                {
                    imgFullImage.setImageResource(R.drawable.image_kitchen);
                }

                if (tab.getPosition()==2)
                {
                    imgFullImage.setImageResource(R.drawable.reciept);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        

    }


}
