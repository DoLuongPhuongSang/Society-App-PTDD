package com.example.frontend.data.remote;

public class GroupRequest {

    private String name;
    private String description;
    private boolean isPrivate;

    public GroupRequest(String name, String description, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    // Add getters for Gson serialization
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isPrivate() { return isPrivate; }
}