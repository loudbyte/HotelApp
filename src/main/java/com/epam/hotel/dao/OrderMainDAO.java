package com.epam.hotel.dao;

import com.epam.hotel.entity.OrderMain;

import java.util.List;

public interface OrderMainDAO extends BaseDAO<OrderMain> {
    List<OrderMain> getAllByPersonId(long id);
}
