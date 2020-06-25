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

import static com.epam.hotel.util.constant.DAOConstant.*;

public class RoomImageDAOImpl implements RoomImageDAO {

    private static final Logger LOGGER = Logger.getLogger(RoomImageDAOImpl.class);
    private static final String GET_ALL = "SELECT * FROM room_image";
    private static final String GET_ONE_BY_ID = "SELECT * FROM room_image WHERE id = ?";
    private static final String CREATE_ROOM_IMAGE = "INSERT INTO room_image (image, room_id)" +
            "VALUES (?, ?)";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM room_image WHERE id = ?";
    private static final String UPDATE_ONE_BY_ID = "UPDATE room_image " +
            "SET image = ?, room_id = ?" +
            "WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ROOM_IMAGE_SEQ = "select last_value FROM room_image_id_seq";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(RoomImage roomImage) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM_IMAGE);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ROOM_IMAGE_SEQ)) {

            preparedStatement.setBytes(1, roomImage.getImage());
            preparedStatement.setLong(2, roomImage.getRoomId());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1);

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<RoomImage> getAll() {
        connection = connectionPool.getConnection();

        List<RoomImage> roomImageList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                roomImageList.add(getRoomImage(resultSet));
            }
            roomImageList.sort(Comparator.comparing(RoomImage::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomImageList;
    }

    @Override
    public RoomImage getOneById(long id) {
        connection = connectionPool.getConnection();

        RoomImage roomImage = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roomImage = getRoomImage(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomImage;
    }

    @Override
    public void updateOneById(long id, RoomImage roomImage) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            preparedStatement.setBytes(1, roomImage.getImage());
            preparedStatement.setLong(2, roomImage.getRoomId());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private RoomImage getRoomImage(ResultSet resultSet) throws SQLException {

        RoomImage roomImage = new RoomImage();
        roomImage.setId(resultSet.getLong(ID));
        roomImage.setImage(resultSet.getBytes(IMAGE));
        roomImage.setRoomId(resultSet.getLong(ROOM_ID));
        return roomImage;
    }
}