package com.app.cheffyuser.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.cheffyuser.home.fragments.PlateDetailsFragment;
import com.app.cheffyuser.home.fragments.PlateReviewFragment;


public class Sub2TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public Sub2TabsAdapter(FragmentManager fm, int NoofTabs){
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
               PlateDetailsFragment pf = new PlateDetailsFragment();
                return pf;
            case 1:
                PlateReviewFragment rf = new PlateReviewFragment();
                return rf;


            default:
                return null;
        }
    }
}