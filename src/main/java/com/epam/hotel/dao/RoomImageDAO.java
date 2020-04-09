package com.epam.hotel.dao;

import com.epam.hotel.entity.RoomImage;

public interface RoomImageDAO extends BaseDAO<RoomImage> {

    RoomImage getByRoomId(int roomId);

}
