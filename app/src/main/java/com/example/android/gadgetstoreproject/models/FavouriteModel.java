package com.example.android.gadgetstoreproject.models;

public class FavouriteModel {
    String favName;
    String favPrice;
    String favDesc;
    String favImg;
    String documentId;

    public FavouriteModel() {
    }

    public FavouriteModel(String favName, String favPrice, String favDesc, String favImg) {
        this.favName = favName;
        this.favPrice = favPrice;
        this.favDesc = favDesc;
        this.favImg = favImg;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }

    public String getFavPrice() {
        return favPrice;
    }

    public void setFavPrice(String favPrice) {
        this.favPrice = favPrice;
    }

    public String getFavDesc() {
        return favDesc;
    }

    public void setFavDesc(String favDesc) {
        this.favDesc = favDesc;
    }

    public String getFavImg() {
        return favImg;
    }

    public void setFavImg(String favImg) {
        this.favImg = favImg;
    }
}
