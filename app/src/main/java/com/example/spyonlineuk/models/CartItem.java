package com.example.spyonlineuk.models;

import android.util.Size;

import java.util.ArrayList;

public class CartItem {
    private int cartId;
    private Products products;
    private Sizes selectedSizes;
    private int quantity;

    public CartItem() {
    }



    public CartItem(int cartId, Products products, Sizes selectedSizes, int quantity) {
        this.cartId = cartId;
        this.products = products;
        this.selectedSizes = selectedSizes;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Sizes getSelectedSizes() {
        return selectedSizes;
    }

    public void setSelectedSizes(Sizes selectedSizes) {
        this.selectedSizes = selectedSizes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
