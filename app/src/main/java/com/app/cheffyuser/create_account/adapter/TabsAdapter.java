package com.app.cheffyuser.create_account.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.cheffyuser.create_account.fragments.LoginFragment;
import com.app.cheffyuser.create_account.fragments.SignUpFragment;


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
                LoginFragment dpf = new LoginFragment();
                return dpf;
            case 1:
                SignUpFragment cdf = new SignUpFragment();
                return cdf;

            default:
                return null;
        }
    }
}