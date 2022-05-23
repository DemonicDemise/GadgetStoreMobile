package com.example.android.gadgetstoreproject.models;

public class UserModel {
    public String name, email, city, password, profileImg, navBackgroundImg;

    public UserModel(){
    }

    public UserModel(String name, String email, String city){
        this.name = name;
        this.email = email;
        this.city = city;
    }

    public UserModel(String name, String email, String city, String profileImg) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.profileImg = profileImg;
    }

    public UserModel(String name, String email, String city, String profileImg, String navBackgroundImg) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.profileImg = profileImg;
        this.navBackgroundImg = navBackgroundImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getNavBackgroundImg() {
        return navBackgroundImg;
    }

    public void setNavBackgroundImg(String navBackgroundImg) { this.navBackgroundImg = navBackgroundImg; }
}
