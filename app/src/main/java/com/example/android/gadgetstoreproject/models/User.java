package com.example.android.gadgetstoreproject.models;

public class User {
    public String name, email, city, password;

    public User(){
    }

    public User(String name,String email, String city){
        this.name = name;
        this.email = email;
        this.city = city;

    }
}
