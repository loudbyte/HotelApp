package com.epam.hotel.entity;

import java.math.BigDecimal;
import java.util.List;

public class Room implements BaseEntityMarker {

    private long id;
    private int roomNumber;
    private int capacity;
    private int roomClass;
    private BigDecimal price;
    private boolean availability;
    private List<RoomImage> imageList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(int roomClassId) {
        this.roomClass = roomClassId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<RoomImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<RoomImage> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", roomClassId=" + roomClass +
                ", price=" + price +
                ", availability=" + availability +
                ", imageList=" + imageList +
                '}';
    }
}