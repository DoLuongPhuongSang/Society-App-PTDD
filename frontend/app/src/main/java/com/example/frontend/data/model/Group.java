package com.example.frontend.data.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Group {

    @SerializedName("_id")
    private String id;

    @SerializedName("groupName")
    private String groupName;

    @SerializedName("description")
    private String description;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("member")
    private List<Member> member;

    public String getId() { return id; }
    public String getGroupName() { return groupName; }
    public String getDescription() { return description; }
    public String getAvatarUrl() { return avatarUrl; }
    public List<Member> getMember() { return member; }
}