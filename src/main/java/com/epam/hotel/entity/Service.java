package com.epam.hotel.entity;

import java.math.BigDecimal;

public class Service implements BaseEntity{
    private int id;

    private String name;
    private BigDecimal price;
    private String description;
    private int servicePackageId;

    public Service(int id, String name, BigDecimal price, String description, int servicePackageId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.servicePackageId = servicePackageId;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", servicePackageId=" + servicePackageId +
                '}';
    }
}
