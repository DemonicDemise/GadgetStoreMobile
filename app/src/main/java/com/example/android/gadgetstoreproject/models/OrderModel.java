package com.example.android.gadgetstoreproject.models;

public class OrderModel {
    String orderName;
    String orderImageView;
    String orderId;
    String documentId;


    public OrderModel() {
    }

    public OrderModel(String orderName, String orderImageView, String orderId) {
        this.orderName = orderName;
        this.orderImageView = orderImageView;
        this.orderId = orderId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderImageView() {
        return orderImageView;
    }

    public void setOrderImageView(String orderImageView) {
        this.orderImageView = orderImageView;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
