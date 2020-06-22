package com.epam.hotel.dao;

import com.epam.hotel.entity.OrderRoomDetail;

import java.util.List;

public interface OrderRoomDetailDAO extends BaseDAO<OrderRoomDetail> {
    List<OrderRoomDetail> getAllByOrderId(long orderId);
}
