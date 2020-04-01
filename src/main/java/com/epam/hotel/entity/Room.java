package com.epam.hotel.entity;

import java.math.BigDecimal;

public class Room extends BaseEntity{

    private int roomNumber;
    private int capacity;
    private int grade;
    private int imageId;
    private BigDecimal cost;
    private boolean availability;

    public Room(int id, int roomNumber, int capacity, int grade, int imageId, BigDecimal cost, boolean availability) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.grade = grade;
        this.imageId = imageId;
        this.cost = cost;
        this.availability = availability;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public BigDecimal getCost() {
        return cost;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", grade=" + grade +
                ", imageId=" + imageId +
                ", cost=" + cost +
                ", availability=" + availability +
                '}';
    }
}
