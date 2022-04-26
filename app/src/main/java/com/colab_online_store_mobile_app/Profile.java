package com.colab_online_store_mobile_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
    EditText username;
    EditText password;
    EditText address;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_profile, container, false);

        username =  (EditText) rootView.findViewById(R.id.inputName);
        password =  (EditText) rootView.findViewById(R.id.inputPassword);
        address =  (EditText) rootView.findViewById(R.id.inputAddress);
        button = (Button) rootView.findViewById(R.id.btnSave);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("name", ""));
        password.setText(sharedPreferences.getString("pass", ""));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("address", address.getText().toString());
        editor.commit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=address.getText().toString();
                address.setText(value);
            }
        });
        return rootView;


    }


}