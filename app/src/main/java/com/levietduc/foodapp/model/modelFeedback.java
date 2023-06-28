package com.levietduc.foodapp.model;

import java.io.Serializable;

public class modelFeedback implements Serializable {
    String message,date,time;

    public  modelFeedback(){}

    public modelFeedback(String message, String date, String time) {
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
