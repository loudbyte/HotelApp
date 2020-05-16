package com.epam.hotel.entity;

public class OrderRoomDetail implements BaseEntityMarker {

    private long id;
    private long roomId;
    private long orderFacilityDetailId;
    private long orderMainId;
    private String startDate;
    private String endDate;

    public OrderRoomDetail() {
    }

    public OrderRoomDetail(long id, long roomId, long orderFacilityDetailId, long orderMainid, String startDate, String endDate) {
        this.id = id;
        this.roomId = roomId;
        this.orderFacilityDetailId = orderFacilityDetailId;
        this.orderMainId = orderMainid;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getOrderFacilityDetailId() {
        return orderFacilityDetailId;
    }

    public void setOrderFacilityDetailId(long orderFacilityDetailId) {
        this.orderFacilityDetailId = orderFacilityDetailId;
    }

    public long getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(long orderMainId) {
        this.orderMainId = orderMainId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "OrderRoomDetail{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", orderFacilityDetailId=" + orderFacilityDetailId +
                ", orderMainid=" + orderMainId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
