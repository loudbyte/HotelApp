package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.entity.RoomImage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomImageDAOImpl extends BaseDAOImpl implements RoomImageDAO {

    private final String GET_ALL_ROOM_IMAGES = "SELECT * FROM room_image";
    private final String GET_ONE_BY_ID = "SELECT * FROM room_image WHERE id = ";
    private final String GET_ONE_BY_ROOM_ID = "SELECT * FROM room_image WHERE room_id = ";
    private final String ADD_ROOM_IMAGE = "INSERT INTO room_image (id, image, room_id)" +
            "VALUES ( ?, ?, ?)";
    private final String DELETE_ONE_BY_ID = "DELETE FROM room_image WHERE id = ";
    private final String UPDATE_ONE_BY_ID = "UPDATE room_image " +
            "SET id = ?, image = ?, room_id = ?" +
            "WHERE id = ";


    @Override
    public RoomImage getByRoomId(int roomId) {
        return getOneFromDB(GET_ONE_BY_ROOM_ID + roomId);

    }

    @Override
    public void create(RoomImage entity) {
        createUpdate(ADD_ROOM_IMAGE, entity);
    }

    @Override
    public List<RoomImage> getAll() {
        List<RoomImage> roomImage = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_ROOM_IMAGES);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                byte[] image = resultSet.getBytes("image");
                int roomId = resultSet.getInt("room_id");

                roomImage.add(new RoomImage(id, image, roomId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return roomImage;
    }

    @Override
    public RoomImage getById(int id) {
        return getOneFromDB(GET_ONE_BY_ID + id);
    }

    @Override
    public void updateOneById(int id, RoomImage entity) {
        String sql = UPDATE_ONE_BY_ID + id;
        createUpdate(sql, entity);
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

    private void createUpdate(String sql, RoomImage service) {
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, service.getId());
            preparedStatement.setBytes(2, service.getImage());
            preparedStatement.setInt(3, service.getRoomId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private RoomImage getOneFromDB(String sql) {

        RoomImage roomImage = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                byte[] image = resultSet.getBytes("image");
                int roomId = resultSet.getInt("room_id");

                roomImage = new RoomImage(id, image, roomId);
            } else {
                roomImage = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return roomImage;
    }
}
