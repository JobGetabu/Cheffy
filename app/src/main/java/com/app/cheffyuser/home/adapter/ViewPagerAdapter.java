package com.app.cheffyuser.home.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> allfragmentlist = new ArrayList<>();
    private List<String> allfragmenttitle = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return allfragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return allfragmentlist.size();
    }


    public void Addmyfragment(Fragment F, String title)
    {
        allfragmentlist.add(F);
        allfragmenttitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return allfragmenttitle.get(position);
    }
}
