package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.entity.Room;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl extends BaseDAOImpl implements RoomDAO {

    private final String GET_ALL_ROOMS = "SELECT * FROM room";
    private final String GET_ONE_BY_NUMBER = "SELECT * FROM room WHERE room_number = ";
    private final String GET_ONE_BY_CAPACITY = "SELECT * FROM room WHERE capacity = ";
    private final String GET_ONE_BY_ID = "SELECT * FROM room WHERE id = ";
    private final String ADD_ROOM = "INSERT INTO room (room_number, capacity, grade, price, availability)" +
            "VALUES ( ?, ?, ?, ?, ?, ?)";
    private final String DELETE_ONE_BY_ID = "DELETE FROM room WHERE id = ";
    private final String UPDATE_ONE_BY_ID = "UPDATE room " +
            "SET room_number = ?, capacity = ?, grade = ?, price = ?, availability = ? " +
            "WHERE id = ";

    @Override
    public void create(Room room) {

        createUpdate(ADD_ROOM, room);

    }

    public List<Room> getAll() {

        List<Room> rooms = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_ROOMS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int roomId = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                int grade = resultSet.getInt("grade");
                BigDecimal price = resultSet.getBigDecimal("price");
                boolean availability = resultSet.getBoolean("availability");
                rooms.add(new Room(roomId, roomNumber, capacity, grade, price, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return rooms;
    }

    @Override
    public Room getById(int id) {

        return getOneFromDB(GET_ONE_BY_ID + id);
    }

    @Override
    public void updateOneById(int id, Room room) {
        String sql = UPDATE_ONE_BY_ID + id;
        createUpdate(sql, room);
    }

    @Override
    public void deleteOneById(int id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Room getByRoomNumber(int number) {

        return getOneFromDB(GET_ONE_BY_NUMBER + number);
    }

    @Override
    public Room getByCapacity(int roomCapacity) {

        return getOneFromDB(GET_ONE_BY_CAPACITY + roomCapacity);
    }

    private void createUpdate(String sql, Room room) {
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setInt(3, room.getGrade());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.setBoolean(5, room.isAvailability());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Room getOneFromDB(String sql) {

        Room room = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomId = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                int grade = resultSet.getInt("grade");
                BigDecimal price = resultSet.getBigDecimal("price");
                boolean availability = resultSet.getBoolean("availability");

                room = new Room(roomId, roomNumber, capacity, grade, price, availability);
            } else {
                room = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return room;
    }
}

