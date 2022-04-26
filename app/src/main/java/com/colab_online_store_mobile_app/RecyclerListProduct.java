package com.colab_online_store_mobile_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerListProduct extends RecyclerView.Adapter<RecyclerListProduct.ViewHolder>{

    private ArrayList<Integer> mIdProduct = new ArrayList<>();
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mDesc = new ArrayList<>();
    private ArrayList<Integer> mPrice = new ArrayList<>();
    private ArrayList<String> mSlug = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();



    private Context mContext;


    public RecyclerListProduct(Context context, ArrayList<Integer> idShowPost, ArrayList<String> Title, ArrayList<String> Desc, ArrayList<Integer> Price,  ArrayList<String> Slug, ArrayList<String> Image) {
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
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.TTitle.setText(String.valueOf(mTitle.get(position)));
        holder.TDesc.setText(String.valueOf(mDesc.get(position)));

        Integer int_price = mPrice.get(position);
        String str_price  = int_price.toString() + "$";
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
            TDesc = itemView.findViewById(R.id.desc);
            TPrice = itemView.findViewById(R.id.price);
            TImage = itemView.findViewById(R.id.list_image);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}