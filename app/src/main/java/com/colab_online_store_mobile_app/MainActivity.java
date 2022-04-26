package com.colab_online_store_mobile_app;

import android.content.Context;
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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null&&CheckForLogin()==false) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileLogin()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ListProduct()).commit();
        }

    }

    Fragment selectedFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (CheckForLogin()==true) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new ListProduct();
                                break;
                            case R.id.navigation_catalog:
                                break;
                            case R.id.navigation_search:
                                break;
                            case R.id.nav_cart:
                                selectedFragment = new Summary();
                                break;
                            case R.id.nav_profile:
                                selectedFragment = new Profile();
                                break;
                        }
                    } else {
                        selectedFragment = new ProfileLogin();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public Boolean CheckForLogin() {

        SharedPreferences preferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean s = preferences.getBoolean("loggedin",false);

        return s;

    }

}