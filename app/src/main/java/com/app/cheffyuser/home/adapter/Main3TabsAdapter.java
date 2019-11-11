package com.app.cheffyuser.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.cheffyuser.home.fragments.KitchenFragment;
import com.app.cheffyuser.home.fragments.PlateFragment;
import com.app.cheffyuser.home.fragments.ReceiptFragment;


public class Main3TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public Main3TabsAdapter(FragmentManager fm, int NoofTabs){
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
               PlateFragment pf = new PlateFragment();

                return pf;
            case 1:
                KitchenFragment kf = new KitchenFragment();
                return kf;

            case 2:
                ReceiptFragment rf = new ReceiptFragment();

                return rf;

            default:
                return null;
        }
    }
}