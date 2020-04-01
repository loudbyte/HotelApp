package com.epam.hotel.dao.daoimpl;

import com.epam.hotel.dao.daocommon.RoomDAOInterface;
import com.epam.hotel.entity.Room;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends BaseDao implements RoomDAOInterface {

    private final String GET_ALL_ROOMS = "SELECT * FROM rooms";
    private final String ADD_ROOM = "INSERT INTO rooms (room_number, capacity, grade, cost, image_id, availability)" +
            "VALUES ( ?, ?, ?, ?, ?, ?)";

    public List<Room> getAll() {

        List<Room> rooms = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_ROOMS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                int grade = resultSet.getInt("grade");
                int imageId = resultSet.getInt("image_id");
                BigDecimal cost = resultSet.getBigDecimal("cost");
                boolean availability = resultSet.getBoolean("availability");
                rooms.add(new Room(id, roomNumber, capacity, grade, imageId, cost, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return rooms;
    }

    public void setRoom(Room room) {

        try {
            preparedStatement = connection.prepareStatement(ADD_ROOM);

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setInt(3, room.getGrade());
            preparedStatement.setBigDecimal(4, room.getCost());
            preparedStatement.setInt(5, room.getImageId());
            preparedStatement.setBoolean(6, room.isAvailability());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}

