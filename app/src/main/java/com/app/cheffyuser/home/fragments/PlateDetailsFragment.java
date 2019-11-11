package com.app.cheffyuser.home.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.activities.ReceiptDetailsActivity;
import com.app.cheffyuser.home.adapter.Food_plate_adapter;
import com.app.cheffyuser.home.model.Food_Plate_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlateDetailsFragment extends Fragment {


    public PlateDetailsFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private Food_plate_adapter adapter;
    private LinearLayoutManager layoutManager1;
    private List<Food_Plate_Model> foodList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_plate_details, container, false);


        recyclerView = view.findViewById(R.id.recyclerview_plate);
        recyclerView.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager1);
        foodList = new ArrayList<>();
        adapter = new Food_plate_adapter(foodList);
        foodList.add(new Food_Plate_Model("Grilled salmon","Season salmon fillets with lemon pepper, garlic powder, and salt. In a small bowl, ","$120.00","10-20 min","Delivery","Free",R.drawable.upload_thumbnail));
        foodList.add(new Food_Plate_Model("Grilled salmon","Season salmon fillets with lemon pepper, garlic powder, and salt. In a small bowl, ","$140.00","10-20 min","Delivery","Free",R.drawable.upload_thumbnail));
        recyclerView.setAdapter(adapter);

        Button button = view.findViewById(R.id.viewReceipt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), ReceiptDetailsActivity.class));
            }
        });


        return view;
    }

}
