package com.example.group31finalproject;

public class User {
    private String username;
    private String password;
    private String type;

    // Constructor
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    //user  format for  csv
    public String formatUser() {
        return username + ','+ password + ',' + type;
    }
}
