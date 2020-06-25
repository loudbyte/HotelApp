package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.entity.Room;
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

public class RoomDAOImpl implements RoomDAO {

    private static final Logger LOGGER = Logger.getLogger(RoomDAOImpl.class);
    private static final String GET_ALL_ROOMS = "SELECT id, room_number, capacity, availability, price, room_class_id FROM room";
    private static final String GET_IMAGES_BY_ROOM_ID = "SELECT * FROM room_image WHERE room_id = ?";
    private static final String GET_ALL_BY_ROOM_CLASS_ID = "SELECT * FROM room WHERE room_class_id = ?";
    private static final String GET_ONE_BY_ID =
            "SELECT id, room_number, capacity, availability, price, room_class_id " +
                    "FROM room WHERE room.id = ?";
    private static final String CREATE_ROOM = "INSERT INTO room (room_number, capacity, room_class_id, price, availability)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM room WHERE id = ?";
    private static final String UPDATE_ONE_BY_ID = "UPDATE room " +
            "SET room_number = ?, capacity = ?, room_class_id = ?, price = ?, availability = ? " +
            "WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ROOM_SEQ = "select last_value FROM room_id_seq";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(Room room) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ROOM_SEQ)) {

            createUpdateRoom(room, preparedStatement);
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
    public void updateOneById(long id, Room room) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            preparedStatement.setLong(6, id);
            createUpdateRoom(room, preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public List<Room> getAll() {
        connection = connectionPool.getConnection();

        List<Room> roomList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROOMS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                roomList.add(getRoom(resultSet));
            }
            roomList.sort(Comparator.comparing(Room::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomList;
    }

    @Override
    public Room getOneById(long id) {
        connection = connectionPool.getConnection();

        Room room = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                room = getRoom(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return room;
    }

    @Override
    public List<Room> getAllByRoomClassId(long roomClassId) {
        connection = connectionPool.getConnection();

        List<Room> roomList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_ROOM_CLASS_ID)) {
            preparedStatement.setLong(1, roomClassId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roomList.add(getRoom(resultSet));
            }
            roomList.sort(Comparator.comparing(Room::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomList;
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

    private Room getRoom(ResultSet resultSet) throws SQLException {

        Room room = new Room();
        room.setId(resultSet.getLong(ID));
        room.setRoomNumber(resultSet.getInt(ROOM_NUMBER));
        room.setCapacity(resultSet.getInt(CAPACITY));
        room.setRoomClassId(resultSet.getInt(ROOM_CLASS_ID));
        room.setPrice(resultSet.getBigDecimal(PRICE));
        room.setAvailability(resultSet.getBoolean(AVAILABILITY));
        long id = room.getId();
        room.setImageList(getRoomImageListByRoomId(id));
        return room;
    }

    private List<RoomImage> getRoomImageListByRoomId(long roomId) {
        connection = connectionPool.getConnection();

        List<RoomImage> imageList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_IMAGES_BY_ROOM_ID)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            imageList = new ArrayList<>();

            while (resultSet.next()) {
                RoomImage image = new RoomImage();
                image.setRoomId(roomId);
                image.setId(resultSet.getLong(ID));
                image.setImage(resultSet.getBytes(IMAGE));
                imageList.add(image);
            }
            imageList.sort(Comparator.comparing(RoomImage::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return imageList;
    }

    private void createUpdateRoom(Room room, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, room.getRoomNumber());
        preparedStatement.setInt(2, room.getCapacity());
        preparedStatement.setLong(3, room.getRoomClassId());
        preparedStatement.setBigDecimal(4, room.getPrice());
        preparedStatement.setBoolean(5, room.isAvailability());

        preparedStatement.executeUpdate();
    }
}