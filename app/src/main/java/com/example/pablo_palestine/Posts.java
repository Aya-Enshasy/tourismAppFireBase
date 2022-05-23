package com.example.pablo_palestine;

import android.net.Uri;

public class Posts {
    String comment,Data,Time,name,image,user_image;


    public Posts(String comment, String data, String time, String name, String image, String user_image) {
        this.comment = comment;
        this.Data = data;
        this.Time = time;
        this.name = name;
        this.image = image;
        this.user_image = user_image;

    }

    public Posts() {
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
