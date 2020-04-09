package com.example.spyonlineuk.models;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class User {
    private String userId;
    private String email;
    private String fullName;
    private String password;
    private String authToken;
    private int Status;
    private ArrayList<Addresses> addresses;

    public User() {
    }

    public User(String userId, String email, String fullName, String password, String authToken, int status, ArrayList<Addresses> addresses) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.authToken = authToken;
        Status = status;
        this.addresses = addresses;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Addresses> addresses) {
        this.addresses = addresses;
    }
}
