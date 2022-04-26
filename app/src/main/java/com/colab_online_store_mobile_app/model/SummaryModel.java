package com.colab_online_store_mobile_app.model;



import java.util.List;

public class SummaryModel {
    private int total;
    public List<ProductItemModel> cart;


    public SummaryModel(int total,
                        List<ProductItemModel> cart)
    {
        this.total = total;
        this.cart = cart;

    }

    public Integer getTotal(){
        return total;
    }

    public List<ProductItemModel> getCart(){
        return cart;
    }





}


//first_name = models.CharField(max_length=400, default="Имя")
//        last_name = models.CharField(max_length=400, default="Фамилия")
//        tel = models.CharField(max_length=400, default="0550 03 03 01")
//        description = models.TextField(default="Текст", blank=True)
//        category = models.IntegerField(default=1, blank=True)
//        foto = models.ImageField(upload_to = '', default = 'none/no-img.jpg')