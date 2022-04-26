package com.colab_online_store_mobile_app;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.colab_online_store_mobile_app.model.ProductItemModel;
import com.colab_online_store_mobile_app.model.ProductModel;
import com.colab_online_store_mobile_app.model.SummaryModel;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Summary extends Fragment implements View.OnClickListener {


    private ArrayList<Integer> idProduct = new ArrayList<>();
    private ArrayList<String> titleProduct = new ArrayList<>();
    private ArrayList<String> descProduct = new ArrayList<>();
    private ArrayList<Integer> priceProduct = new ArrayList<>();
    private ArrayList<String> imageProduct = new ArrayList<>();

    private RecyclerView recyclerView;

    TextView EdTotal;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.summary, container, false);


        recyclerView = rootView.findViewById(R.id.recycler_list_product);

        Button ClearBtn = (Button) rootView.findViewById(R.id.clear_btn);

        EdTotal =  (TextView) rootView.findViewById(R.id.lt_summary_total);

        ClearBtn.setOnClickListener(this);

        return rootView;


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_btn:
                ClearButtonClick();
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ClearButtonClick()
    {
        GetClear();
        updateSummaryList();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void GetClear()
    {

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Upload...");
        mProgressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(com.colab_online_store_mobile_app.PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        com.colab_online_store_mobile_app.PostApi postApi= retrofit.create(com.colab_online_store_mobile_app.PostApi.class);

        String token_ex_ap = SharedDataGetSet.getMySavedToken(getActivity());

        Call<ResponseBody> call = postApi.getClearCart(token_ex_ap);

        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


                if(response.isSuccessful()){

                    if (response.body() != null) {

                        EdTotal.setText("0");

                        String message = "Clear";

                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }

                }else {

                    try {
                        Integer responce_code = response.code();
                        String err_message = responce_code.toString()+ "_" + response.errorBody().string();
                        Toast.makeText(getContext(), err_message , Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    Log.d("fail", "fail");

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showListProduct() {

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Upload...");
        mProgressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(com.colab_online_store_mobile_app.PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        com.colab_online_store_mobile_app.PostApi postApi= retrofit.create(com.colab_online_store_mobile_app.PostApi.class);

        String token_ex_ap = SharedDataGetSet.getMySavedToken(getActivity());

        Call<SummaryModel> call = postApi.getSummaryList(token_ex_ap);

        call.enqueue(new Callback<SummaryModel>() {
            @Override
            public void onResponse(Call<SummaryModel> call, Response<SummaryModel> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


                if(response.isSuccessful()){

                    if (response.body() != null) {


                        SummaryModel summary_res = response.body();

                        List <ProductItemModel> productCart = summary_res.getCart();

                        Integer int_total = summary_res.getTotal();
                        String str_total_for_view = int_total.toString() + "$";
                        EdTotal.setText(str_total_for_view);


                        for(ProductItemModel productItem:productCart){

                            ProductModel product = productItem.getProduct();


                            Integer id_product = product.getId();
                            idProduct.add(id_product);

                            String str_title = product.getTitle();
                            titleProduct.add(str_title);

                            String str_desc = product.getDesc();
                            descProduct.add(str_desc);


                            Integer price_product = product.getPrice();
                            priceProduct.add(price_product);



                        }

                        initRecyclerView();
                    }

                }else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<SummaryModel> call, Throwable t) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }

        });

    }


    private void initRecyclerView(){
        com.colab_online_store_mobile_app.RecyclerSummary adapter = new com.colab_online_store_mobile_app.RecyclerSummary(getActivity(), idProduct, titleProduct, descProduct, priceProduct, descProduct);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateSummaryList() {
        idProduct.clear();
        titleProduct.clear();
        descProduct.clear();
        priceProduct.clear();
        imageProduct.clear();


        com.colab_online_store_mobile_app.RecyclerSummary adapter = new com.colab_online_store_mobile_app.RecyclerSummary(getActivity(), idProduct, titleProduct, descProduct, priceProduct, descProduct);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        if ( com.colab_online_store_mobile_app.InternetUtil.isInternetOnline(getActivity()) ){
            showListProduct();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        updateSummaryList();

    }
}