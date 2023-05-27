package com.levietduc.foodapp.model;

public class modelPopular {
    private String title;
    private String pic;
    private String description;
    private Double price;
    private int nubCart;

    public modelPopular(String title, String pic, String description, Double price, int nubCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.nubCart = nubCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNubCart() {
        return nubCart;
    }

    public void setNubCart(int nubCart) {
        this.nubCart = nubCart;
    }
}
