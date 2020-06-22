package com.epam.hotel.entity;

public class OrderMain implements BaseEntityMarker {

    private long id;
    private long personId;
    private long status;
    private String date;

    public OrderMain() {
    }

    public OrderMain(long id, long personId, long status, String date) {
        this.id = id;
        this.personId = personId;
        this.status = status;
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

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
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
                ", status=" + status +
                ", date='" + date + '\'' +
                '}';
    }
}