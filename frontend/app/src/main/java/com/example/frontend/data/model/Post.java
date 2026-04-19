package com.example.frontend.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Post {

    @SerializedName("_id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("author")
    private User author;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    // Getters
    public String getId() { return id; }
    public String getContent() { return content; }
    public User getAuthor() { return author; }
    public String getGroupId() { return groupId; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
}
