package com.app.cheffyuser.profile.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.profile.adapter.Food_Menu_Adapter;
import com.app.cheffyuser.profile.model.Food_Menu_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private Food_Menu_Adapter adapter;
    private GridLayoutManager layoutManager;
    private List<Food_Menu_Model> foodList;

    private ProgressBar progressBar;



    public UserFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_chef, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerview_food_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        foodList = new ArrayList<>();



//
        foodList.add(new Food_Menu_Model("Available","Grilled salmon","4.9(200)","15-20min","Delivery",R.drawable.upload_thumbnail));
        foodList.add(new Food_Menu_Model("Not Available","Grilled salmon","4.9(200)","15-20min","Delivery",R.drawable.food_image2));
        foodList.add(new Food_Menu_Model("Available","Grilled salmon","3.9(200)","15-30min","Delivery",R.drawable.upload_thumbnail));
        foodList.add(new Food_Menu_Model("Not Available","Grilled salmon","4.9(200)","15-20min","Delivery",R.drawable.food_image2));
        foodList.add(new Food_Menu_Model("Available","Grilled salmon","2.9(200)","15-20min","Delivery",R.drawable.food_image2));
        foodList.add(new Food_Menu_Model("Available","Grilled salmon","4.9(200)","15-20min","Delivery",R.drawable.food_image3));

        adapter = new Food_Menu_Adapter(foodList,getActivity());
        recyclerView.setAdapter(adapter);


        return view;


    }

}
