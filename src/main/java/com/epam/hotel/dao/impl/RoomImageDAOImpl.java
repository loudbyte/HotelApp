package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.RoomImageDAO;
import com.epam.hotel.entity.RoomImage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class RoomImageDAOImpl implements RoomImageDAO {

    private static final Logger LOGGER = Logger.getLogger(RoomImageDAOImpl.class);
    private static final String GET_ALL_ROOM_IMAGES_BY_ROOM_ID = "SELECT * FROM room_image";
    private static final String GET_ONE_BY_ID = "SELECT * FROM room_image WHERE id = ?";
    private static final String GET_ALL_BY_ROOM_ID = "SELECT * FROM room_image WHERE room_id = ?";
    private static final String CREATE_ROOM_IMAGE = "INSERT INTO room_image (image, room_id)" +
            "VALUES (?, ?)";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM room_image WHERE id = ?";
    private static final String UPDATE_ONE_BY_ID = "UPDATE room_image " +
            "SET image = ?, room_id = ?" +
            "WHERE id = ?";

    private  static final String GET_LAST_VALUE_FROM_ROOM_IMAGE_SEQ = "select last_value FROM room_image_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(RoomImage roomImage) {
        connection = connectionPool.getConnection();

        long id = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM_IMAGE);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ROOM_IMAGE_SEQ);) {

            preparedStatement.setBytes(1, roomImage.getImage());
            preparedStatement.setLong(2, roomImage.getRoomId());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next()) {
                id = resultSetGetSeq.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<RoomImage> geAllByRoomId(long roomId) {
        connection = connectionPool.getConnection();

        List<RoomImage> roomImageList = new ArrayList<>();
        RoomImage roomImage;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROOM_IMAGES_BY_ROOM_ID);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                roomImage = new RoomImage();
                roomImage.setId(resultSet.getLong(ID));
                roomImage.setImage(resultSet.getBytes(IMAGE));
                roomImage.setRoomId(roomId);

                roomImageList.add(roomImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        roomImageList.sort(Comparator.comparing(RoomImage::getId));
        return roomImageList;
    }

    @Override
    public List<RoomImage> getAll() {
        connection = connectionPool.getConnection();

        List<RoomImage> roomImageList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROOM_IMAGES_BY_ROOM_ID);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                byte[] image = resultSet.getBytes(IMAGE);
                long roomId = resultSet.getLong(ROOM_ID);

                roomImageList.add(new RoomImage(id, image, roomId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        roomImageList.sort(Comparator.comparing(RoomImage::getId));
        return roomImageList;
    }

    @Override
    public RoomImage getOneById(long id) {
        connection = connectionPool.getConnection();

        RoomImage roomImage = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            roomImage = getRoomImage(roomImage, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl getById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomImage;
    }

    @Override
    public void updateOneById(long id, RoomImage roomImage) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID);) {

            preparedStatement.setBytes(1, roomImage.getImage());
            preparedStatement.setLong(2, roomImage.getRoomId());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl updateOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomImageDAOimpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private RoomImage getRoomImage(RoomImage roomImage, ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {
            roomImage = new RoomImage();
            roomImage.setId(resultSet.getLong(ID));
            roomImage.setImage(resultSet.getBytes(IMAGE));
            roomImage.setRoomId(resultSet.getLong(ROOM_ID));
        }
        return roomImage;
    }
}