package com.example.android.gadgetstoreproject.models;

public class NavCategoryModel {
    String name;
    String discount;
    String img_url;

    public NavCategoryModel(){

    }

    public NavCategoryModel(String name, String description, String discount, String img_url) {
        this.name = name;
        this.discount = discount;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImgUrl(String img_url) {
        this.img_url = img_url;
    }
}
