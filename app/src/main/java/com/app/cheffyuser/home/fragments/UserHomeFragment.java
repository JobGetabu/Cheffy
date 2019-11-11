package com.app.cheffyuser.home.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.home.adapter.FoodNearByAdapter;
import com.app.cheffyuser.home.adapter.Food_Menu_Adapter2;
import com.app.cheffyuser.home.model.FoodNearByModel;
import com.app.cheffyuser.networking.remote.ApiClient;
import com.app.cheffyuser.networking.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHomeFragment extends Fragment {


    private RecyclerView foodNearByList,recyclerView2,recyclerView3;
    private FoodNearByAdapter foodNearByAdapter;
    private Food_Menu_Adapter2 adapter2;
    private LinearLayoutManager layoutManager1,layoutManager2,layoutManager3;
    private List<FoodNearByModel> foodList;

    private ApiInterface apiInterface;


    public UserHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_chef_home, container, false);
        foodNearByList = view.findViewById(R.id.recyclerview2);
        recyclerView2 = view.findViewById(R.id.recyclerview1);
        recyclerView3 = view.findViewById(R.id.recyclerview3);
        foodNearByList.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager3 = new LinearLayoutManager(getActivity());
        foodNearByList.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
//        foodList = new ArrayList<>();
//        foodList.add(new FoodNearByModel("Grilled salmon","4.9(200)","15-20 min","Delivery",R.drawable.upload_thumbnail));
//        foodList.add(new FoodNearByModel("Grilled salmon","4.9(200)","15-20 min","Delivery",R.drawable.food_image2));
//        foodList.add(new FoodNearByModel("Grilled salmon","4.9(200)","15-20 min","Delivery",R.drawable.food_image3));
//        foodList.add(new FoodNearByModel("Grilled salmon","4.9(200)","15-20 min","Delivery",R.drawable.food_image2));
//        foodList.add(new FoodNearByModel("Grilled salmon","4.9(200)","15-20 min","Delivery",R.drawable.upload_thumbnail));

        //foodNearByAdapter = new FoodNearByAdapter(foodList,getActivity());
       // adapter2 = new Food_Menu_Adapter2(foodList,getActivity());
       // foodNearByList.setAdapter(foodNearByAdapter);
       // recyclerView2.setAdapter(foodNearByAdapter);
       // recyclerView3.setAdapter(adapter2);



        foodList = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String latitude="-5.0328144";
        String longitude="-42.8150343";
        String radius="10";
        fetchNearFood(latitude,longitude,radius);
//        foodNearByAdapter = new FoodNearByAdapter(foodList,getActivity());
//        foodNearByList.setAdapter(foodNearByAdapter);


//        foodNearByAdapter = new FoodNearByAdapter(foodList, getActivity());
//        foodNearByList.setAdapter(adapter2);
//


        return view;
    }




    private void fetchNearFood(String lat, String lon, String radius) {


        Call<List<FoodNearByModel>> call = apiInterface.getFoodNearBy(lat,lon,radius);
        call.enqueue(new Callback<List<FoodNearByModel>>() {
            @Override
            public void onResponse(Call<List<FoodNearByModel>> call, Response<List<FoodNearByModel>> response) {

               foodList = response.body();


                Log.d("Response", ""+foodList.size());

                String name=foodList.get(0).getName();
                Log.d("name",name);

                foodNearByAdapter = new FoodNearByAdapter(foodList, getActivity());
                foodNearByList.setAdapter(foodNearByAdapter);

//                mShimmerViewContainer.stopShimmer();
//                mShimmerViewContainer.setVisibility(View.GONE);


                foodNearByAdapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<FoodNearByModel>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error : " + t.toString(), Toast.LENGTH_SHORT).show();

                Log.d("Error",t.toString());
            }
        });
    }


}
