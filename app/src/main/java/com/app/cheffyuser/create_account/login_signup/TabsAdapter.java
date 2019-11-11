package com.app.cheffyuser.create_account.login_signup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


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