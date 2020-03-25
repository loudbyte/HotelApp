package com.epam.hotel.dao.daoapi;

import com.epam.hotel.entity.Room;

import java.util.List;

public interface RoomDAOInterface {
    List<Room> getAllRooms();

    void setRoom(Room room);
}
