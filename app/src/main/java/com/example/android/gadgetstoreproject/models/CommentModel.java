package com.example.android.gadgetstoreproject.models;

import com.google.gson.annotations.SerializedName;

public class CommentModel {
    private int itemId;
    private int id;
    private String name;
    private String email;

    @SerializedName("body")
    private String text;

    public int getItemId() {
        return itemId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
