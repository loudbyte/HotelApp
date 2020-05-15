package com.epam.hotel.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Room implements BaseEntityMarker {

    private long id;
    private int roomNumber;
    private int capacity;
    private long roomClassId;
    private String roomClassEn;
    private String roomClassRu;
    private BigDecimal price;
    private boolean availability;
    private List<RoomImage> imageList;
    private List<byte[]> images;

    public Room() {
    }

    public Room(long id, int roomNumber, int capacity, long roomClassId, String roomClassEn, String roomClassRu, BigDecimal price, boolean availability, List<RoomImage> imageList, ArrayList<byte[]> images) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomClassId = roomClassId;
        this.roomClassEn = roomClassEn;
        this.roomClassRu = roomClassRu;
        this.price = price;
        this.availability = availability;
        this.imageList = imageList;
        this.images = images;
    }

    public Room(long id, int roomNumber, int capacity, long roomClassId, String roomClassEn, String roomClassRu, BigDecimal price, boolean availability, ArrayList<byte[]> images) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomClassId = roomClassId;
        this.roomClassEn = roomClassEn;
        this.roomClassRu = roomClassRu;
        this.price = price;
        this.availability = availability;
        this.images = images;

    }

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

    public String getRoomClassEn() {
        return roomClassEn;
    }

    public void setRoomClassEn(String roomClassEn) {
        this.roomClassEn = roomClassEn;
    }

    public String getRoomClassRu() {
        return roomClassRu;
    }

    public void setRoomClassRu(String roomClassRu) {
        this.roomClassRu = roomClassRu;
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

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
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
                ", roomClassEn='" + roomClassEn + '\'' +
                ", roomClassRu='" + roomClassRu + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", imageList=" + imageList +
                ", images=" + images +
                '}';
    }
}
