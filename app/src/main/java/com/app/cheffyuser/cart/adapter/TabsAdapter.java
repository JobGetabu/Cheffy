package com.app.cheffyuser.cart.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.cheffyuser.cart.fragments.AddCartFragment;
import com.app.cheffyuser.cart.fragments.CustomOrderFragment;
import com.app.cheffyuser.cart.fragments.DeliveryFragment;
import com.app.cheffyuser.cart.fragments.TrackingOrderFragment;


public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                AddCartFragment add_cart = new AddCartFragment();
                return add_cart;
            case 1:
                CustomOrderFragment custom_order = new CustomOrderFragment();
                return custom_order;

            case 2:
                TrackingOrderFragment track_order = new TrackingOrderFragment();
                return track_order;

            case 3:
                DeliveryFragment delivery = new DeliveryFragment();
                return delivery;

            default:
                return null;
        }
    }
}