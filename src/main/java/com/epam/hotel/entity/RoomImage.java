package com.epam.hotel.entity;

public class RoomImage implements BaseEntity {
    private int id;
    private byte[] image;
    private int roomId;

    public RoomImage(int id, byte[] image, int room_id) {
        this.id = id;
        this.image = image;
        this.roomId = room_id;
    }

    public RoomImage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomImage{" +
                "id=" + id +
                ", image=" + image +
                ", room_id=" + roomId +
                '}';
    }
}
