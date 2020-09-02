package com.epam.hotel.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Room extends BaseEntity {

    private int roomNumber;
    private int capacity;
    private long roomClassId;
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

    public long getRoomClassId() {
        return roomClassId;
    }

    public void setRoomClassId(long roomClassId) {
        this.roomClassId = roomClassId;
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
                ", roomClassId=" + roomClassId +
                ", price=" + price +
                ", availability=" + availability +
                ", imageList=" + imageList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber &&
                capacity == room.capacity &&
                roomClassId == room.roomClassId &&
                availability == room.availability &&
                Objects.equals(price, room.price) &&
                Objects.equals(imageList, room.imageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, capacity, roomClassId, price, availability, imageList);
    }
}