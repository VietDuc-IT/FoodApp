package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelCategory implements Serializable {
    private String name;
    private String img;

    public modelCategory(){}

    public modelCategory(String name, String img) {
        this.name = name;
        this.img = img;
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
}
