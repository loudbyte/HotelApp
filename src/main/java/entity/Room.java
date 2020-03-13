package entity;

import java.math.BigDecimal;

public class Room {

    private int id;
    private int roomNumber;
    private int capacity;
    private int grade;
    private BigDecimal cost;
    private boolean availability;

    public Room(int id, int roomNumber, int capacity, int grade, BigDecimal cost, boolean availability) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.grade = grade;
        this.cost = cost;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public BigDecimal getCost() {
        return cost;
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
}
