package com.app.cheffyuser.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.cheffyuser.R;
import com.app.cheffyuser.cart.adapter.TabsAdapter;
import com.google.android.material.tabs.TabLayout;


public class TabsFragment extends Fragment {

    public TabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        TabLayout tabLayout = (TabLayout)view. findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Add Cart"));
        tabLayout.addTab(tabLayout.newTab().setText("Custom Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Tracking Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivery Complete"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager =(ViewPager)view.findViewById(R.id.view_pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
}
