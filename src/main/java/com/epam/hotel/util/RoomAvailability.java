package com.epam.hotel.util;

import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Room;

import java.util.List;

public class RoomAvailability {
    public static void setAvailabilityForRoomsInOrder(long orderMainId, boolean isAvailable) {
        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        List<OrderRoomDetail> orderRoomDetailList = orderRoomDetailDAO.getAllByOrderId(orderMainId);
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Room room;
        for(OrderRoomDetail orderRoomDetail : orderRoomDetailList) {
            room = roomDAO.getOneById(orderRoomDetail.getRoomId());
            room.setAvailability(isAvailable);
            roomDAO.updateOneById(room.getId(), room);
        }
    }
}