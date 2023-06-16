package com.levietduc.foodapp.model;

public class modelProfile {
    String phone,address;

    public modelProfile(){}
    public modelProfile(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
