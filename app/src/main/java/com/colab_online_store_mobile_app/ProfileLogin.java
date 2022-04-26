package com.colab_online_store_mobile_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.colab_online_store_mobile_app.model.Login;
import com.colab_online_store_mobile_app.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileLogin extends Fragment implements View.OnClickListener {
    EditText Edreg_username;
    EditText Edreg_password;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.activity_kuka_login, container, false);


        Button logBtn = (Button) rootView.findViewById(R.id.btnLogin);
        TextView to_reg_Btn = (TextView) rootView.findViewById(R.id.createNewAccount);


        Edreg_username = (EditText) rootView.findViewById(R.id.inputEmail);
        Edreg_password = (EditText) rootView.findViewById(R.id.inputPassword);
        logBtn.setOnClickListener(this);
        to_reg_Btn.setOnClickListener(this);

        return rootView;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                LogButtonClick();
                break;
            case R.id.createNewAccount:
                RegButtonClick();
                break;
        }
    }



    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public void RegButtonClick()
    {

        Fragment fragment = null;
        fragment = new com.colab_online_store_mobile_app.ProfileRegister();
        replaceFragment(fragment);

    }


    public void LogButtonClick()
    {

        if (!IsEmptyEditTextLogin()){

            if ( com.colab_online_store_mobile_app.InternetUtil.isInternetOnline(getActivity()) ){
                login();
            }

        }



    }


    private void login(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(com.colab_online_store_mobile_app.PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        com.colab_online_store_mobile_app.PostApi postApi = retrofit.create(com.colab_online_store_mobile_app.PostApi.class);


        String add1      =  Edreg_username.getText().toString();
        String add2      =  Edreg_password.getText().toString();

        Login login = new Login(add1, add2);

        Call<User> call = postApi.login(login);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        String token = response.body().getToken();
                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("loggedin", true);
                        prefLoginEdit.putString("token", token);
                        prefLoginEdit.putString("user", add1);
                        prefLoginEdit.putString("pass", add2);
                        prefLoginEdit.commit();

                        Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();

                        Fragment fragment = null;
                        fragment = new ListProduct();
                        replaceFragment(fragment);

                    }

                }else {
                    Toast.makeText(getContext(), "login no correct:(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
                Log.i("kuka",t.toString());
            }
        });
    }



    private Boolean IsEmptyEditTextLogin(){



        if(Edreg_password.getText().toString().isEmpty() || Edreg_username.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }







}