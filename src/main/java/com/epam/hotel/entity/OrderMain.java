package com.epam.hotel.entity;

import java.util.Objects;

public class OrderMain extends BaseEntity {

    private long personId;
    private long orderStatusId;
    private String date;

    public OrderMain() {
    }

    public OrderMain(long id, long personId, long orderStatusId, String date) {
        this.id = id;
        this.personId = personId;
        this.orderStatusId = orderStatusId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderMain{" +
                "id=" + id +
                ", personId=" + personId +
                ", status=" + orderStatusId +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMain orderMain = (OrderMain) o;
        return personId == orderMain.personId &&
                orderStatusId == orderMain.orderStatusId &&
                Objects.equals(date, orderMain.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, orderStatusId, date);
    }
}