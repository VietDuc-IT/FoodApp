package com.levietduc.foodapp.model;

public class modelBuyNow {
    String name,price,number,img;
    public  modelBuyNow(){}

    public modelBuyNow(String name, String number, String price, String img) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.img = img;
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
