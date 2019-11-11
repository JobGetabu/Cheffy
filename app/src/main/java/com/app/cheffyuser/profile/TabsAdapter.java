package com.app.cheffyuser.profile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.cheffyuser.profile.fragments.AccountsSettingFragment;
import com.app.cheffyuser.profile.fragments.UserFavoriteFragment;


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
               UserFavoriteFragment cf = new UserFavoriteFragment();
                return cf;
            case 1:
                AccountsSettingFragment asf = new AccountsSettingFragment();
                return asf;

            default:
                return null;
        }
    }
}