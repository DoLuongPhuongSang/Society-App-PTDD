package com.example.frontend.data.remote;

public class CreatePostRequest {
    private String content;
    private String groupId;

    public CreatePostRequest(String content, String groupId) {
        this.content = content;
        this.groupId = groupId;
    }

    // Add getters for Gson serialization
    public String getContent() { return content; }
    public String getGroupId() { return groupId; }
}