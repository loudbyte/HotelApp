package com.epam.hotel.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart {

    private long orderRoomDetailCounter = 0L;
    private Map<Long, OrderRoomDetail> orderRoomDetailMap = new HashMap<>();

    public Map<Long, OrderRoomDetail> getOrderRoomDetailMap() {
        return orderRoomDetailMap;
    }

    public Long getOrderRoomDetailCounter() {
        return orderRoomDetailCounter;
    }

    public void setOrderRoomDetailMap(Map<Long, OrderRoomDetail> orderRoomDetailMap) {
        this.orderRoomDetailMap = orderRoomDetailMap;
    }

    public void deleteElementByKeyFromOrderRoomDetailMap(Long key) {
        this.orderRoomDetailMap.remove(key);
    }

    public void clearOrderRoomDetailMap() {
        this.orderRoomDetailMap.clear();
    }

    public void addElementByKeyToOrderRoomDetailMap(OrderRoomDetail orderRoomDetail) {
        this.orderRoomDetailMap.put(orderRoomDetailCounter, orderRoomDetail);
        orderRoomDetailCounter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return orderRoomDetailCounter == cart.orderRoomDetailCounter &&
                Objects.equals(orderRoomDetailMap, cart.orderRoomDetailMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderRoomDetailCounter, orderRoomDetailMap);
    }
}