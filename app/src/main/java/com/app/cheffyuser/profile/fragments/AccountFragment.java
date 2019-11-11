package com.app.cheffyuser.profile.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.cheffyuser.R;
import com.app.cheffyuser.networking.Constant;
import com.app.cheffyuser.profile.TabsAdapter;
import com.app.cheffyuser.profile.activities.EditProfileActivity;
import com.app.cheffyuser.profile.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        ImageView imgProfileEdit=view.findViewById(R.id.img_profile_edit);
        ImageView imgUser=view.findViewById(R.id.user_image);


        TabLayout tabLayout = (TabLayout)view. findViewById(R.id.profile_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Your Favorite"));
        tabLayout.addTab(tabLayout.newTab().setText("Account Setting"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        TextView txtUserName=view.findViewById(R.id.tv_user_name);
        TextView txtAddress=view.findViewById(R.id.tv_user_address);

        //Fetching cell from shared preferences
        SharedPreferences sp;
        sp =getContext().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sp.getString(Constant.SP_NAME, "N/A");
        String image_path=sp.getString(Constant.SP_IMAGE_PATH,"N/A");

        String latitude=sp.getString(Constant.SP_LOCATION_LAT,"");
        String longitude=sp.getString(Constant.SP_LOCATION_LON,"");


        txtUserName.setText(name);

        final String getAddress= Utils.getCurrentPlace(getActivity(),latitude,longitude);
        txtAddress.setText(getAddress);

        //Loading image from url into imageView
        Glide.with(getActivity())
                .load(image_path)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imgUser);





        final ViewPager viewPager =(ViewPager)view.findViewById(R.id.profile_view_pager);
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



        imgProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("address",getAddress);
                startActivity(intent);

            }
        });
        return view;
    }


//
//    private String getCurrentPlace(String lat,String lon)
//    {
//
//        double latitude=Double.parseDouble(lat);
//        double longitude=Double.parseDouble(lon);
//        Geocoder geocoder;
//        List<Address> addresses = null;
//        geocoder = new Geocoder(getContext(), Locale.getDefault());
//
//        try {
//            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //for getting current location using gps
//        //https://developer.android.com/reference/android/location/Address
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//
////        String city = addresses.get(0).getLocality();
////        String state = addresses.get(0).getAdminArea();
////        String country = addresses.get(0).getCountryName();
//        // String postalCode = addresses.get(0).getPostalCode();
//
//        // String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
//
//        //txtLocation.setText(address);
//        //Toast.makeText(this, "Post Code:"+postalCode+" "+knownName, Toast.LENGTH_SHORT).show();
//
//        return address;
//    }

}
