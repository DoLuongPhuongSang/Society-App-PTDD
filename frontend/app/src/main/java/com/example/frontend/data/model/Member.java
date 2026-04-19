package com.example.frontend.data.model;

import com.google.gson.annotations.SerializedName;
public class Member {
    @SerializedName("userId")
    private User userId;

    @SerializedName("role")
    private String role;

    public User getUserId() { return userId; }
    public String getRole() { return role; }
}