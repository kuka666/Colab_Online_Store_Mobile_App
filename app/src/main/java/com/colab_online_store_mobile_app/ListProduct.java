package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.colab_online_store_mobile_app.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListProduct extends Fragment  {
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private ArrayList<Integer> idProduct = new ArrayList<>();
    private ArrayList<String> titleProduct = new ArrayList<>();
    private ArrayList<String> descProduct = new ArrayList<>();
    private ArrayList<Integer> priceProduct = new ArrayList<>();
    private ArrayList<String> slugProduct = new ArrayList<>();
    private ArrayList<String> imageProduct = new ArrayList<>();
    TextView address;
    private RecyclerView recyclerView;
    EditText edit;
    ImageButton ib;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_list_product, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_list_product);

        viewPager2 = rootView.findViewById(R.id.viewPagerImageSlider);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.slider1));
        sliderItems.add(new SliderItem(R.drawable.slider2));
        sliderItems.add(new SliderItem(R.drawable.slider3));
        sliderItems.add(new SliderItem(R.drawable.slider4));
        sliderItems.add(new SliderItem(R.drawable.slider5));
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);

        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        address = (TextView) rootView.findViewById(R.id.address_list_product);
        String kuka = sharedPreferences.getString("user", "");
        if(sharedPreferences.getString(kuka, "").isEmpty()){
            address.setText("Укажите свое адрес в профиле");
        }
        else{
            address.setText(sharedPreferences.getString(kuka, ""));
        }
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new Profile();
                replaceFragment(fragment);
            }
        });
        edit = (EditText) rootView.findViewById(R.id.edit_search);
        ib = (ImageButton) rootView.findViewById(R.id.search);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_str  = edit.getText().toString();
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
                .baseUrl(com.colab_online_store_mobile_app.PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        com.colab_online_store_mobile_app.PostApi postApi= retrofit.create(com.colab_online_store_mobile_app.PostApi.class);



        Call<List<ProductModel>> call = postApi.getProductList();

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if(response.isSuccessful()){

                    if (response.body() != null) {


                        List<ProductModel> productList = response.body();

                        for(ProductModel productItem:productList){

                            Integer id_product = productItem.getId();
                            idProduct.add(id_product);

                            String str_title = productItem.getTitle();
                            titleProduct.add(str_title);

                            String str_desc = productItem.getDesc();
                            descProduct.add(str_desc);


                            Integer price_product = productItem.getPrice();
                            priceProduct.add(price_product);

                            String str_slug = productItem.getSlug();
                            slugProduct.add(str_slug);

                            String str_image = productItem.getImage();
                            imageProduct.add(str_image);
                            //Search




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
        com.colab_online_store_mobile_app.RecyclerListProduct adapter = new com.colab_online_store_mobile_app.RecyclerListProduct(getActivity(), idProduct, titleProduct, descProduct, priceProduct, slugProduct, imageProduct);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }


    private void updateGruzList() {
        idProduct.clear();
        titleProduct.clear();
        descProduct.clear();
        priceProduct.clear();
        slugProduct.clear();
        imageProduct.clear();

        com.colab_online_store_mobile_app.RecyclerListProduct adapter = new com.colab_online_store_mobile_app.RecyclerListProduct(getActivity(), idProduct, titleProduct, descProduct, priceProduct, slugProduct, imageProduct);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        if ( com.colab_online_store_mobile_app.InternetUtil.isInternetOnline(getActivity()) ){
            showListProduct();
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        updateGruzList();

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}