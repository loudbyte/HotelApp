package com.epam.hotel.dao;

import com.epam.hotel.dao.connection.ConnectionPool;
import com.epam.hotel.dao.connection.ConnectionPoolException;
import com.epam.hotel.dao.daoapi.RoomDAOInterface;
import com.epam.hotel.entity.Room;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements RoomDAOInterface {

    private ConnectionPool connectionPool = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet;

    private final String GET_ALL_ROOMS = "SELECT * FROM rooms";
    private final String ADD_ROOM = "INSERT INTO rooms (\"roomNumber\", \"capacity\", \"grade\", \"cost\", \"availability\") " +
            "VALUES ( ?, ?, ?, ?, ?)";

    public RoomDAO() throws ConnectionPoolException {
        connectionPool = new ConnectionPool();
        connectionPool.initPoolData();
        connection = connectionPool.takeConnection();
    }

    public List<Room> getAllRooms() {

        List<Room> rooms = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_ROOMS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("roomNumber");
                int capacity = resultSet.getInt("capacity");
                int grade = resultSet.getInt("grade");
                BigDecimal cost = resultSet.getBigDecimal("cost");
                boolean availability = resultSet.getBoolean("availability");
                rooms.add(new Room(id, roomNumber, capacity, grade, cost, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.dispose();
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
            preparedStatement.setBoolean(5, room.isAvailability());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.dispose();
        }
    }
}

