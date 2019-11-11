package com.app.cheffyuser.home.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.adapter.Food_plate_adapter;
import com.app.cheffyuser.home.model.Food_Plate_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenDetailsFragment extends Fragment {


    public KitchenDetailsFragment() {
        // Required empty public constructor
    }

    private View view1;
    private RecyclerView recyclerView;
    private Food_plate_adapter adapter;
    private LinearLayoutManager layoutManager1;
    private List<Food_Plate_Model> foodList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kitchen_details, container, false);


//        recyclerView = view1.findViewById(R.id.recyclerview_plate_kitchen);
//        recyclerView.setHasFixedSize(true);
//        layoutManager1 = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager1);
//        foodList = new ArrayList<>();
//        adapter = new Food_plate_adapter(foodList);
//        foodList.add(new Food_Plate_Model("asd","dddddd","38383","10-20","deliver","free",R.drawable.upload_thumbnail));
//        foodList.add(new Food_Plate_Model("asd","dddddd","38383","10-20","deliver","free",R.drawable.upload_thumbnail));
//        foodList.add(new Food_Plate_Model("asd","dddddd","38383","10-20","deliver","free",R.drawable.upload_thumbnail));
//        recyclerView.setAdapter(adapter);

        return view;
    }

}
