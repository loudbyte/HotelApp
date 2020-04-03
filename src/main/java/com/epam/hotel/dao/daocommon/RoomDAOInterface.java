package com.epam.hotel.dao.daocommon;

import com.epam.hotel.entity.Room;

public interface RoomDAOInterface extends BaseDAOInterface<Room> {

    Room getByRoomNumber(int roomNumber);

    Room getByCapacity(int roomCapacity);

}
