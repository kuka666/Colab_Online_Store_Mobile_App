package com.colab_online_store_mobile_app.model;


public class ProductModel {

    private Integer id;
    private String timestamp;
    private String data_otpravleniya;
    private String updated;
    private Integer ves_gruza;
    private Integer cena_uslugi;
    private String commentariy;
    private Integer id_author;
    private String name;
    private String phone;
    private String image;


    private String title;
    private String desc;
    private Integer price;
//    private String image;
    private String cart_url;
    private String slug;


    public ProductModel(Integer id,
                        String timestamp,
                        String data_otpravleniya,
                        String updated,
                        Integer ves_gruza,
                        Integer cena_uslugi,
                        String commentariy,
                        Integer id_author,
                        String name,
                        String phone,
                        String image,

                        String title,
                        String desc,
                        Integer price,
                        String cart_url,
                        String slug


    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.data_otpravleniya = data_otpravleniya;
        this.updated = updated;
        this.ves_gruza = ves_gruza;
        this.cena_uslugi = cena_uslugi;
        this.commentariy = commentariy;
        this.id_author = id_author;
        this.name = name;
        this.phone = phone;
        this.image = image;


        this.title = title;
        this.desc = desc;
        this.price = price;
        this.cart_url = cart_url;
        this.slug = slug;
    }

    public Integer getId() {return id;}
    public String getTimestamp() {
        return timestamp;
    }
    public String getData_otpravleniya() {
        return data_otpravleniya;
    }
    public String getUpdated() {
        return updated;
    }
    public Integer getVes_gruza() {
        return ves_gruza;
    }
    public Integer getCena_uslugi() { return cena_uslugi; }
    public String getCommentariy() {
        return commentariy;
    }

    public Integer getId_author() {
        return id_author;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getImage() {
        return image;
    }





    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCart_url() {
        return cart_url;
    }

    public String getSlug() {
        return slug;
    }


}