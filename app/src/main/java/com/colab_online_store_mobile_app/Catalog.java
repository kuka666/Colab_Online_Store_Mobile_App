package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Catalog extends Fragment {
    LinearLayout fruit;
    LinearLayout milk;
    LinearLayout pharmacy;
    LinearLayout zoo;
    LinearLayout sweets;
    LinearLayout snacks;
    LinearLayout drinks;
    LinearLayout meat;
    LinearLayout bread;
    SharedPreferences sharedPreferences;
    EditText edit;
    ImageButton ib;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.catalog, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        fruit = rootView.findViewById(R.id.fruit);
        milk = rootView.findViewById(R.id.milk);
        pharmacy = rootView.findViewById(R.id.pharmacy);
        zoo = rootView.findViewById(R.id.zoo);
        sweets = rootView.findViewById(R.id.sweets);
        snacks = rootView.findViewById(R.id.snacks);
        drinks = rootView.findViewById(R.id.drinks);
        meat = rootView.findViewById(R.id.meat);
        bread = rootView.findViewById(R.id.bread);
        edit = (EditText) rootView.findViewById(R.id.edit_search);
        ib = (ImageButton) rootView.findViewById(R.id.search);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_str  = edit.getText().toString().toLowerCase(Locale.ROOT);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("search", search_str);
                editor.commit();
                Fragment fragment = null;
                fragment = new Search();
                replaceFragment(fragment);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "fruit");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "milk");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "pharmacy");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        zoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "zoo");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "sweets");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "snacks");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "drinks");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "meat");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category", "bread");
                editor.commit();
                Fragment fragment = null;
                fragment = new Category();
                replaceFragment(fragment);
            }
        });
        edit.setText(sharedPreferences.getString("search",""));

        return rootView;

    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
