package com.app.cheffyuser.home.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter_for_internal_tab extends FragmentPagerAdapter {

    private List<Fragment> allfragmentlist1 = new ArrayList<>();
    private List<String> allfragmenttitle1 = new ArrayList<>();


    public ViewPagerAdapter_for_internal_tab(FragmentManager fm1) {
        super(fm1);
    }

    @Override
    public Fragment getItem(int position) {
        return allfragmentlist1.get(position);
    }

    @Override
    public int getCount() {
        return allfragmentlist1.size();
    }


    public void Addmyfragment1(Fragment F, String title)
    {
        allfragmentlist1.add(F);
        allfragmenttitle1.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return allfragmenttitle1.get(position);
    }
}
