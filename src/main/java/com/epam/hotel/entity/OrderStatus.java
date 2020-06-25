package com.epam.hotel.entity;

import java.util.Map;

public class OrderStatus implements BaseEntityMarker {
    private long id;
    private Map<Integer, String> orderStatusNameMap;

    public OrderStatus() {
    }

    public OrderStatus(Map<Integer, String> orderStatusNameMap) {
        this.orderStatusNameMap = orderStatusNameMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Integer, String> getOrderStatusNameMap() {
        return orderStatusNameMap;
    }

    public void setOrderStatusNameMap(Map<Integer, String> orderStatusNameMap) {
        this.orderStatusNameMap = orderStatusNameMap;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", orderStatusNameMap=" + orderStatusNameMap +
                '}';
    }
}
