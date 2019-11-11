package com.app.cheffyuser;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.cheffyuser.cart.TabsFragment;
import com.app.cheffyuser.custom_order.CustomOrderActivity;
import com.app.cheffyuser.food_category.FoodCategoryFragment;
import com.app.cheffyuser.home.fragments.UserHomeFragment;
import com.app.cheffyuser.profile.fragments.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavActivity extends AppCompatActivity {
    private TextView mTextMessage;

    Fragment fragment = null;

    BottomNavigationView bottomNavigationView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new UserHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_category:

                    fragment = new FoodCategoryFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_custom_order:

                    Intent intent=new Intent(BottomNavActivity.this, CustomOrderActivity.class);
                    startActivity(intent);

                    return true;

                case R.id.navigation_cart:
                    //mTextMessage.setText(R.string.title_notifications);
                    fragment = new TabsFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.nav_account:
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //loading the default fragment
        loadFragment(new UserHomeFragment());
    }


    //loadFragment function
    private void loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
        }
    }

}
