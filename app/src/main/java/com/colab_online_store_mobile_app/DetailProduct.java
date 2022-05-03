package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.colab_online_store_mobile_app.model.ProductModel;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailProduct extends Fragment implements View.OnClickListener {


    TextView tv_dp_title;
    TextView tv_dp_desc;
    ImageView iv_dp_image;
    SharedPreferences sharedPreferences;
    String slug_for_btn;
    Button addBtn;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.detail_product, container, false);



        tv_dp_title = (TextView) rootView.findViewById(R.id.ltv_dp_title);
        tv_dp_desc = (TextView) rootView.findViewById(R.id.ltv_dp_desc);
        iv_dp_image = (ImageView) rootView.findViewById(R.id.liv_dp_image);

        addBtn = (Button) rootView.findViewById(R.id.btn_add_cart);
        sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);




        Bundle bundle = this.getArguments();
        if(bundle != null){
            String bundle_id = bundle.getString("key_slug");
            GetServerData(bundle_id);
        }
        addBtn.setText(sharedPreferences.getString("price",""));
        addBtn.setOnClickListener(this);


        return rootView;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_cart:
                AddButtonClick();
                break;
        }
    }

    public void AddButtonClick()
    {
        GetCreateCart(slug_for_btn);
    }



    private void GetServerData(String  getted_slug)
    {

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Upload...");
        mProgressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String data = getted_slug;

        PostApi postApi= retrofit.create(PostApi.class);

        Call<ProductModel> call = postApi.getDetailProduct(data);

        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


                if(response.isSuccessful()){

                    if (response.body() != null) {

                        ProductModel productItem = response.body();


                        String str_dp_title = productItem.getTitle();
                        String str_dp_desc = productItem.getDesc();
                        slug_for_btn = productItem.getSlug();
                        Integer int_dp_price  = productItem.getPrice();
                        String str_dp_price = int_dp_price.toString() + " ₸";
                        String str_dp_image = productItem.getImage();


                        tv_dp_title.setText(str_dp_title);
                        tv_dp_desc.setText(str_dp_desc);
                        addBtn.setText(str_dp_price);
                        Picasso.get().load(str_dp_image).into(iv_dp_image);


                    }

                }else {
                    Log.d("fail", "fail");
                }


            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });

    }



    private void GetCreateCart(String  slug_cart)
    {

        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Upload...");
        mProgressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String data = slug_cart;

        PostApi postApi= retrofit.create(PostApi.class);
        String my_token = SharedDataGetSet.getMySavedToken(getActivity());

        Call<ResponseBody> call = postApi.getCreateCart(my_token, data);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


                if(response.isSuccessful()){

                    if (response.body() != null) {

                        String message = "+ to cart";
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Log.d("fail", my_token+" "+data);
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



}