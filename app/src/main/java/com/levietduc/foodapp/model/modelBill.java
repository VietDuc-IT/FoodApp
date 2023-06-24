package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelBill implements Serializable {
    String img, name, price, number;

    public modelBill(){}

    public modelBill(String img, String name, String price, String number) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
