package com.example.pablo_palestine.hotel_room;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    private String roomName;
    String roomNum, roomPrice,image1,image2,image3;
    int id;


    public Room() {
    }

    public Room(String roomName, String roomNum, String roomPrice, String image1, String image2, String image3) {
        this.roomName = roomName;
        this.roomNum = roomNum;
        this.roomPrice = roomPrice;
    }

    protected Room(Parcel in) {
        roomName = in.readString();
        roomNum = in.readString();
        roomPrice = in.readString();
        image1 = in.readString();
        image2 = in.readString();
        image3 = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomName);
        dest.writeString(roomNum);
        dest.writeString(roomPrice);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(image3);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

}
