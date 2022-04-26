package com.colab_online_store_mobile_app.model;

public class ProductItemModel {
    private int id;
    public ProductModel product;


    public ProductItemModel(int id,
                        ProductModel product)
    {
        this.id = id;
        this.product = product;

    }

    public Integer getId(){
        return id;
    }

    public ProductModel getProduct(){
        return product;
    }


}