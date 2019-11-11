package com.app.cheffyuser.food_category;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cheffyuser.R;
import com.app.cheffyuser.food_category.adapter.Food_Category_Adapter;
import com.app.cheffyuser.food_category.model.FoodCategoryModel;
import com.app.cheffyuser.networking.remote.ApiClient;
import com.app.cheffyuser.networking.remote.ApiInterface;
import com.app.cheffyuser.profile.adapter.Food_Menu_Adapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodCategoryFragment extends Fragment {


    public FoodCategoryFragment() {
        // Required empty public constructor
    }

    private ShimmerFrameLayout mShimmerViewContainer;

    private RecyclerView recyclerView;
    private Food_Category_Adapter adapter;
    private GridLayoutManager layoutManager;
    private List<FoodCategoryModel> categoryList;




    private ApiInterface apiInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_category, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);



        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);

//        categoryList.add(new FoodCategoryModel("Arabian", R.drawable.upload_thumbnail));
//        categoryList.add(new FoodCategoryModel("Japanese", R.drawable.food_image2));
//        categoryList.add(new FoodCategoryModel("Asian", R.drawable.img_food_2));
//        categoryList.add(new FoodCategoryModel("Japanese", R.drawable.food_image3));
//        categoryList.add(new FoodCategoryModel("Asian", R.drawable.upload_thumbnail));
//        categoryList.add(new FoodCategoryModel("Arabian", R.drawable.food_image2));
//        categoryList.add(new FoodCategoryModel("Japanese", R.drawable.upload_thumbnail));
//        categoryList.add(new FoodCategoryModel("Asian", R.drawable.food_image3));


        categoryList = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        categoryList = new ArrayList<>();
        fetchData();
        adapter = new Food_Category_Adapter(categoryList,getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }



    public void fetchData() {


        Call<List<FoodCategoryModel>> call = apiInterface.categoryList();
        call.enqueue(new Callback<List<FoodCategoryModel>>() {
            @Override
            public void onResponse(Call<List<FoodCategoryModel>> call, Response<List<FoodCategoryModel>> response) {

                categoryList = response.body();


                Log.d("Response", categoryList.toString());

                adapter = new Food_Category_Adapter(categoryList, getActivity());
                recyclerView.setAdapter(adapter);

                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);


                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<FoodCategoryModel>> call, Throwable t) {

                Toast.makeText(getActivity(), "Error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

