package com.example.android.gadgetstoreproject.models;

public class UserModel {
    public String name, email, city, password;

    public UserModel(){
    }

    public UserModel(String name, String email, String city){
        this.name = name;
        this.email = email;
        this.city = city;

    }
}
