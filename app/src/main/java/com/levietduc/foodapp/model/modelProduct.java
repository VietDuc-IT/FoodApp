package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelProduct implements Serializable {
    private String name,img;
    private Double price;
    private int numberInCart;

    public modelProduct(){}

    public modelProduct(String name, String img, Double price, int numberInCart) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.numberInCart = numberInCart;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
