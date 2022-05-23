package com.example.pablo_palestine.churches;

public class Churches {
    private String name,location,image,details,availableTime,km;


    public Churches(String name, String location, String image, String details, String availableTime, String km) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.details = details;
        this.availableTime = availableTime;
        this.km = km;
    }

    public Churches() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
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

    public void setImage(String imgV) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
