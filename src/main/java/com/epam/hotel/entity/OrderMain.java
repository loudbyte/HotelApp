package com.epam.hotel.entity;

public class OrderMain implements BaseEntityMarker {

    private long id;
    private long personId;
    private long statusId;
    private String statusEn;
    private String statusRu;
    private String date;

    public OrderMain() {
    }

    public OrderMain(long id, long personId, long statusId, String date) {
        this.id = id;
        this.personId = personId;
        this.statusId = statusId;
        this.date = date;
    }

    public OrderMain(long id, long personId, long statusId, String statusEn, String statusRu, String date) {
        this.id = id;
        this.personId = personId;
        this.statusId = statusId;
        this.statusEn = statusEn;
        this.statusRu = statusRu;
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

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusRu() {
        return statusRu;
    }

    public void setStatusRu(String statusRu) {
        this.statusRu = statusRu;
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
                ", statusId=" + statusId +
                ", statusEn='" + statusEn + '\'' +
                ", statusRu='" + statusRu + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
