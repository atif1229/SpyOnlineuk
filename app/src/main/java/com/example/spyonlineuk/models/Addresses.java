package com.example.spyonlineuk.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Addresses implements Serializable {
    private int addressId;
    private String fullAddress;
    private String nearByLandMark;
    private int userId;

    public Addresses() {
    }


    public Addresses(int addressId, String fullAddress, String nearByLandMark, int userId) {
        this.addressId = addressId;
        this.fullAddress = fullAddress;
        this.nearByLandMark = nearByLandMark;
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getNearByLandMark() {
        return nearByLandMark;
    }

    public void setNearByLandMark(String nearByLandMArk) {
        this.nearByLandMark = nearByLandMark;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
