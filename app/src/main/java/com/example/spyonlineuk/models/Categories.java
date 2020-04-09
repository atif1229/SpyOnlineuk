package com.example.spyonlineuk.models;

import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;

public class Categories implements Serializable {
    private int catId;
    private String catName;
    private String catImage;
    private String catStatus;

    public Categories() {
    }

    public Categories(int catId, String catName, String catImage, String catStatus) {
        this.catId = catId;
        this.catName = catName;
        this.catImage = catImage;
        this.catStatus = catStatus;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(String catStatus) {
        this.catStatus = catStatus;
    }
}
