package com.colab_online_store_mobile_app;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.colab_online_store_mobile_app.HelpFuncFile.SharedDataGetSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerListProduct extends RecyclerView.Adapter<RecyclerListProduct.ViewHolder>{

    private ArrayList<Integer> mIdProduct = new ArrayList<>();
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mDesc = new ArrayList<>();
    private ArrayList<Integer> mPrice = new ArrayList<>();
    private ArrayList<String> mSlug = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    ImageButton button;


    private Context mContext;


    public RecyclerListProduct(Context context, ArrayList<Integer> idShowPost, ArrayList<String> Title, ArrayList<String> Desc, ArrayList<Integer> Price,  ArrayList<String> Slug, ArrayList<String> Image ) {
        mIdProduct = idShowPost;
        mTitle = Title;
        mDesc = Desc;
        mPrice = Price;
        mSlug = Slug;
        mImage = Image;

        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        button = view.findViewById(R.id.btn_add_cart);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.TTitle.setText(String.valueOf(mTitle.get(position)));;

        Integer int_price = mPrice.get(position);
        String str_price  = int_price.toString() + "₸";
        holder.TPrice.setText(str_price);


        String str_image = mImage.get(position);

        Picasso.get().load(str_image).into(holder.TImage);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("key_slug",mSlug.get(position));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new com.colab_online_store_mobile_app.DetailProduct();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key_slug",mSlug.get(position));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PostApi.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                String data = mSlug.get(position);
                PostApi postApi= retrofit.create(PostApi.class);
                SharedPreferences  sharedPreferences = mContext.getSharedPreferences("myPrefs", MODE_PRIVATE);
                String my_token = sharedPreferences.getString("token","");
                Call<ResponseBody> call = postApi.getCreateCart("Token "+my_token, data);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){

                            if (response.body() != null) {

                                String message = "+ to cart";


                            }

                        }else {
                            Log.d("fail", my_token);
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return mIdProduct.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TTitle;
        TextView TDesc;
        TextView TPrice;
        ImageView TImage;


        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            TTitle = itemView.findViewById(R.id.title);
            TPrice = itemView.findViewById(R.id.price);
            TImage = itemView.findViewById(R.id.list_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }





}