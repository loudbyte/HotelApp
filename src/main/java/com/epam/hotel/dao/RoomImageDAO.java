package com.epam.hotel.dao;

import com.epam.hotel.entity.RoomImage;

import java.util.List;

public interface RoomImageDAO extends BaseDAO<RoomImage> {

    List<RoomImage> geAllByRoomId(long roomId);

}
