package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.colab_online_store_mobile_app.model.Login;
import com.colab_online_store_mobile_app.model.ProductItemModel;
import com.colab_online_store_mobile_app.model.ProductModel;
import com.colab_online_store_mobile_app.model.SummaryModel;
import com.colab_online_store_mobile_app.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Profile extends Fragment {

    SharedPreferences sharedPreferences;
    EditText username,password,address,fname,lname,phonenumber;
    Button buttonLogout,save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("check_login",true)) {
            username = (EditText) rootView.findViewById(R.id.inputName);
            password = (EditText) rootView.findViewById(R.id.inputPassword);
            address = (EditText) rootView.findViewById(R.id.inputAddress);
            fname = (EditText) rootView.findViewById(R.id.inputFirstName);
            lname = (EditText) rootView.findViewById(R.id.inputLastName);
            phonenumber = (EditText) rootView.findViewById(R.id.inputPhoneNumber);
            save = (Button) rootView.findViewById(R.id.btnSave);
            buttonLogout = (Button) rootView.findViewById(R.id.btnLogout);
            username.setText(sharedPreferences.getString("user", ""));
            password.setText(sharedPreferences.getString("pass", ""));
            buttonLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor prefLoginEdit = sharedPreferences.edit();
                    prefLoginEdit.putBoolean("loggedin", false);
                    prefLoginEdit.commit();
                    Fragment fragment = null;
                    fragment = new ProfileLogin();
                    replaceFragment(fragment);
                }
            });
            String kuka = sharedPreferences.getString("user", "");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String add  = address.getText().toString();
                    String firstname = fname.getText().toString();
                    String lastname = lname.getText().toString();
                    String number = phonenumber.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(kuka, add);
                    editor.putString(kuka+"firstname", firstname);
                    editor.putString(kuka+"lastname", lastname);
                    editor.putString(kuka+"phonenumber",number);
                    editor.commit();
                }
            });
            address.setText(sharedPreferences.getString(kuka, ""));
            fname.setText(sharedPreferences.getString(kuka+"firstname", ""));
            lname.setText(sharedPreferences.getString(kuka+"lastname", ""));
            phonenumber.setText(sharedPreferences.getString(kuka+"phonenumber", ""));
        }
        else{
            new ProfileLogin();
        }
        return rootView;

    }
    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}