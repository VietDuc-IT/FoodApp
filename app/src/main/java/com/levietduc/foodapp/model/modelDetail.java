package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelDetail implements Serializable {
    private modelProduct product;
    private modelPopular popular;

    public modelDetail(modelProduct product, modelPopular popular) {
        this.product = product;
        this.popular = popular;
    }

    public modelProduct getProduct() {
        return product;
    }

    public void setProduct(modelProduct product) {
        this.product = product;
    }

    public modelPopular getPopular() {
        return popular;
    }

    public void setPopular(modelPopular popular) {
        this.popular = popular;
    }
}
