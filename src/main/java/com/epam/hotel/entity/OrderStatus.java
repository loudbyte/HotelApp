package com.epam.hotel.entity;

import java.util.Map;
import java.util.Objects;

public class OrderStatus extends BaseEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(orderStatusNameMap, that.orderStatusNameMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatusNameMap);
    }
}
