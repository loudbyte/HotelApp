package com.epam.hotel.entity;

import java.util.Arrays;

public class RoomImage implements BaseEntityMarker {
    private long id;
    private byte[] image;
    private long roomId;

    public RoomImage(long id, byte[] image, long roomId) {
        this.id = id;
        this.image = image;
        this.roomId = roomId;
    }

    public RoomImage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomImage{" +
                "id=" + id +
                ", image=" + Arrays.toString(image) +
                ", room_id=" + roomId +
                '}';
    }
}
