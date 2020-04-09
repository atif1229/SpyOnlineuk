package com.example.spyonlineuk.models;

import java.io.Serializable;

public class Sizes implements Serializable {

   private int sizeId;
   private String sizeName;
   private int sizeStatus;
   private int productId;
   private boolean selected;

    public Sizes() {
    }

    public Sizes(int sizeId, String sizeName, int sizeStatus, int productId, boolean selected) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.sizeStatus = sizeStatus;
        this.productId = productId;
        this.selected = selected;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getSizeStatus() {
        return sizeStatus;
    }

    public void setSizeStatus(int sizeStatus) {
        this.sizeStatus = sizeStatus;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
