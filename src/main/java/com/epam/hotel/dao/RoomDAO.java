package com.epam.hotel.dao;

import com.epam.hotel.entity.Room;

import java.util.List;

public interface RoomDAO extends BaseDAO<Room> {

    List<Room> getAllByRoomClassId(long roomClassId);

}
