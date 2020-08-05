package com.epam.hotel.entity;

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
}