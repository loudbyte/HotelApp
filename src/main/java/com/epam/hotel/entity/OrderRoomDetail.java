package com.epam.hotel.entity;

import java.util.Objects;

public class OrderRoomDetail extends BaseEntity {

    private long roomId;
    private long facilityPackageId;
    private long orderMainId;
    private String startDate;
    private String endDate;

    public OrderRoomDetail() {
    }

    public OrderRoomDetail(long id, long roomId, long facilityPackageId, long orderMainId, String startDate, String endDate) {
        this.id = id;
        this.roomId = roomId;
        this.facilityPackageId = facilityPackageId;
        this.orderMainId = orderMainId;
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

    public long getFacilityPackageId() {
        return facilityPackageId;
    }

    public void setFacilityPackageId(long facilityPackageId) {
        this.facilityPackageId = facilityPackageId;
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
                ", facilityPackageId=" + facilityPackageId +
                ", orderMainId=" + orderMainId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRoomDetail that = (OrderRoomDetail) o;
        return roomId == that.roomId &&
                facilityPackageId == that.facilityPackageId &&
                orderMainId == that.orderMainId &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, facilityPackageId, orderMainId, startDate, endDate);
    }
}