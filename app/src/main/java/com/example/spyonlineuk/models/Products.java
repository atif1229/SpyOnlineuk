package com.example.spyonlineuk.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Products  implements Serializable {
    private int productId;
    private String productName;
    private int productPrice;
    private String productImage;
    private String productDetail;
    private String productStatus;
    private String productPriority;
    private int productDiscount;
    private int catId;
    private ArrayList<Sizes> productSizes;

    public Products() {
    }

    public Products(int productId, String productName, int productPrice, String productImage, String productDetail, String productStatus, String productPriority, int productDiscount, int catId, ArrayList<Sizes> productSizes) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDetail = productDetail;
        this.productStatus = productStatus;
        this.productPriority = productPriority;
        this.productDiscount = productDiscount;
        this.catId = catId;
        this.productSizes = productSizes;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductPriority() {
        return productPriority;
    }

    public void setProductPriority(String productPriority) {
        this.productPriority = productPriority;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public ArrayList<Sizes> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(ArrayList<Sizes> productSizes) {
        this.productSizes = productSizes;
    }
}
