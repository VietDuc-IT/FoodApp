package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelCategory implements Serializable {
    private String name;
    private String img,type;

    public modelCategory(){}

    public modelCategory(String name, String img, String type) {
        this.name = name;
        this.img = img;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
