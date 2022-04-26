package com.colab_online_store_mobile_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileLogin()).commit();
        }




    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            if (SharedDataGetSet.CheckForLogin(getApplicationContext())){
                                selectedFragment = new ListProduct();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
                                selectedFragment = new ProfileLogin();
                            }
                            break;
                        case R.id.nav_cart:
                            if (SharedDataGetSet.CheckForLogin(getApplicationContext())){
                                selectedFragment = new Summary();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
                                selectedFragment = new ProfileLogin();
                            }
                            break;
                        case R.id.navigation_catalog:
                            selectedFragment = new ListProduct();
                            break;
                        case R.id.navigation_search:
                            selectedFragment = new ListProduct();
                            break;
                        case R.id.nav_profile:
                            if (SharedDataGetSet.CheckForLogin(getApplicationContext())){
                                selectedFragment = new Profile();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
                                selectedFragment = new ProfileLogin();
                            }
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };




}