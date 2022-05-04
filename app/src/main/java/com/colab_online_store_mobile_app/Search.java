package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.colab_online_store_mobile_app.model.ProductItemModel;
import com.colab_online_store_mobile_app.model.ProductModel;
import com.colab_online_store_mobile_app.model.SummaryModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends Fragment {


    private ArrayList<Integer> idProduct = new ArrayList<>();
    private ArrayList<String> titleProduct = new ArrayList<>();
    private ArrayList<String> descProduct = new ArrayList<>();
    private ArrayList<Integer> priceProduct = new ArrayList<>();
    private ArrayList<String> imageProduct = new ArrayList<>();
    private ArrayList<String> slugProduct = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    EditText edit;
    ImageButton ib;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.search, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_list_product);
        sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
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
        return rootView;


    }

    private void showListProduct() {

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Upload...");
        mProgressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);

        String token_ex_ap = SharedDataGetSet.getMySavedToken(getActivity());

        Call<List<ProductModel>> call = postApi.getProductList();

        call.enqueue(new Callback<List<ProductModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if(response.isSuccessful()){

                    if (response.body() != null) {


                        List<ProductModel> productList = response.body();
                        int i = 0;
                        for(ProductModel productItem:productList){
                            if(productList.get(i).getTitle().toLowerCase(Locale.ROOT).contains(sharedPreferences.getString("search",""))) {
                                Integer id_product = productList.get(i).getId();
                                idProduct.add(id_product);

                                String str_title = productList.get(i).getTitle();
                                titleProduct.add(str_title);

                                String str_desc = productList.get(i).getDesc();
                                descProduct.add(str_desc);


                                Integer price_product = productList.get(i).getPrice();
                                priceProduct.add(price_product);

                                String str_slug = productList.get(i).getSlug();
                                slugProduct.add(str_slug);

                                String str_image = productList.get(i).getImage();
                                imageProduct.add(str_image);
                            }
                            i= i + 1;

                        }

                        initRecyclerView();
                    }

                }else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }

        });

    }


    private void initRecyclerView(){
        RecyclerSearch adapter = new RecyclerSearch(getActivity(), idProduct, titleProduct, descProduct, priceProduct, descProduct,imageProduct);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void updateSummaryList() {
        idProduct.clear();
        titleProduct.clear();
        descProduct.clear();
        priceProduct.clear();
        imageProduct.clear();


        RecyclerSearch adapter = new RecyclerSearch(getActivity(), idProduct, titleProduct, descProduct, priceProduct, descProduct, imageProduct);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            showListProduct();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        updateSummaryList();

    }
    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
