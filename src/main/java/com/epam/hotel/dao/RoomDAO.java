package com.epam.hotel.dao;

import com.epam.hotel.entity.Room;

public interface RoomDAO extends BaseDAO<Room> {

    Room getByRoomNumber(int roomNumber);

    Room getByCapacity(int roomCapacity);

}
