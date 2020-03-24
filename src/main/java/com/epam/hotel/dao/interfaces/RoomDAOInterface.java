package com.epam.hotel.dao.interfaces;

import com.epam.hotel.entity.Room;

import java.util.List;

public interface RoomDAOInterface {
    List<Room> getRooms();

    void setRoom(Room room);
}
