package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelPopular implements Serializable{
    private String name,img;
    private double price;

    public modelPopular(){}
    public modelPopular(String name, String img, double price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
