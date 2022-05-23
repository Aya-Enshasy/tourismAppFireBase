package com.example.pablo_palestine.hotels;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pablo_palestine.hotel_room.Room;

import java.util.List;

public class Hotels implements Parcelable {
    private String name, location, image, details, roomName;
    double rate;
    String roomNum;
    String roomPrice;
    List<Room> roomList;
    int id;
    private String key;

    public Hotels() {
    }

    public Hotels(String name, String location, String image, String details, String roomName, double rate, String roomNum, String roomPrice) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.details = details;
        this.roomName = roomName;
        this.rate = rate;
        this.roomNum = roomNum;
        this.roomPrice = roomPrice;
    }

    protected Hotels(Parcel in) {
        name = in.readString();
        location = in.readString();
        image = in.readString();
        details = in.readString();
        roomName = in.readString();
        rate = in.readDouble();
        roomNum = in.readString();
        roomPrice = in.readString();
        roomList = in.createTypedArrayList(Room.CREATOR);
        id = in.readInt();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(image);
        dest.writeString(details);
        dest.writeString(roomName);
        dest.writeDouble(rate);
        dest.writeString(roomNum);
        dest.writeString(roomPrice);
        dest.writeTypedList(roomList);
        dest.writeInt(id);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hotels> CREATOR = new Creator<Hotels>() {
        @Override
        public Hotels createFromParcel(Parcel in) {
            return new Hotels(in);
        }

        @Override
        public Hotels[] newArray(int size) {
            return new Hotels[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
