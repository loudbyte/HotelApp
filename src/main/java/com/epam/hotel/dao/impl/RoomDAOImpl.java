package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.entity.Person;
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

import static com.epam.hotel.dao.impl.Constant.*;

public class RoomDAOImpl implements RoomDAO {

    private static final Logger LOGGER = Logger.getLogger(RoomDAOImpl.class);
    private static final String GET_ALL_ROOMS =
            "SELECT room.id, room_number, capacity, availability, price, room_class_id, room_class_en, room_class_ru " +
                    "FROM room LEFT JOIN room_class ON room.room_class_id = room_class.id";
    private static final String GET_IMAGES_BY_ROOM_ID = "SELECT * FROM room_image WHERE room_id = ?";
    private static final String GET_ONE_BY_NUMBER = "SELECT * FROM room WHERE room_number = ?";
    private static final String GET_ONE_BY_CAPACITY = "SELECT * FROM room WHERE capacity = ?";
    private static final String GET_ONE_BY_ID =
            "SELECT room.id, room_number, capacity, availability, price, room_class_id, room_class_en, room_class_ru " +
            "FROM room LEFT JOIN room_class ON room.room_class_id = room_class.id WHERE room.id = ?";;
    private static final String CREATE_ROOM = "INSERT INTO room (room_number, capacity, room_class_id, price, availability)" +
            "VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_ONE_BY_ID = "DELETE FROM room WHERE id = ?";
    private final String UPDATE_ONE_BY_ID = "UPDATE room " +
            "SET room_number = ?, capacity = ?, room_class_id = ?, price = ?, availability = ? " +
            "WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ROOM_SEQ = "select last_value FROM room_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(Room room) {
        connection = connectionPool.getConnection();

        long id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ROOM_SEQ);) {

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setLong(3, room.getRoomClassId());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.setBoolean(5, room.isAvailability());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1) + 1;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    private List<RoomImage> getRoomImageListByRoomId(long roomId) {
        connection = connectionPool.getConnection();

        List imageList = null;

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
        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOImpl getRoomImagesByRoomId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        imageList.sort(Comparator.comparing(RoomImage::getId));
        return imageList;
    }

    private List<byte[]> getRoomImagesByRoomId(long id) {
        connection = connectionPool.getConnection();

        List<byte[]> images = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_IMAGES_BY_ROOM_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            images = new ArrayList<>();
            while (resultSet.next()) {
                images.add(resultSet.getBytes("image"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOImpl getRoomImagesByRoomId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return images;
    }

    @Override
    public void updateOneById(long id, Room room) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID);) {

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setLong(3, room.getRoomClassId());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.setBoolean(5, room.isAvailability());
            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl updateOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public List<Room> getAll() {
        connection = connectionPool.getConnection();

        List<Room> roomList = new ArrayList<>();
        Room room = null;
        List<byte[]> images = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROOMS);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                room = new Room();
                room.setId(resultSet.getLong(ID));
                room.setRoomNumber(resultSet.getInt(ROOM_NUMBER));
                room.setCapacity(resultSet.getInt(CAPACITY));
                room.setRoomClassId(resultSet.getLong(ROOM_CLASS_ID));
                room.setRoomClassEn(resultSet.getString(ROOM_CLASS_EN));
                room.setRoomClassRu(resultSet.getString(ROOM_CLASS_RU));
                room.setPrice(resultSet.getBigDecimal(PRICE));
                room.setAvailability(resultSet.getBoolean(AVAILABILITY));
                long id = room.getId();
                room.setImageList(getRoomImageListByRoomId(id));
                room.setImages(getRoomImagesByRoomId(id));
                roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        roomList.sort(Comparator.comparing(Room::getId));
        return roomList;
    }

    @Override
    public Room getOneById(long id) {
        connection = connectionPool.getConnection();

        Room room = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            room = getRoom(room, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl getById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return room;
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Room getByRoomNumber(int number) {
        connection = connectionPool.getConnection();

        Room room = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_NUMBER)) {
            preparedStatement.setLong(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            room = getRoom(room, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl getByRoomNumber", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return room;
    }

    @Override
    public Room getByCapacity(int roomCapacity) {
        connection = connectionPool.getConnection();

        Room room = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_CAPACITY)) {
            preparedStatement.setLong(1, roomCapacity);
            ResultSet resultSet = preparedStatement.executeQuery();

            room = getRoom(room, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in RoomDAOimpl getById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return room;
    }

    private Room getRoom(Room room, ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {
            room = new Room();
            room.setId(resultSet.getLong(ID));
            room.setRoomNumber(resultSet.getInt(ROOM_NUMBER));
            room.setCapacity(resultSet.getInt(CAPACITY));
            room.setRoomClassId(resultSet.getLong(ROOM_CLASS_ID));
            room.setRoomClassEn(resultSet.getString(ROOM_CLASS_EN));
            room.setRoomClassRu(resultSet.getString(ROOM_CLASS_RU));
            room.setPrice(resultSet.getBigDecimal(PRICE));
            room.setAvailability(resultSet.getBoolean(AVAILABILITY));
            long id = room.getId();
            room.setImageList(getRoomImageListByRoomId(id));
            room.setImages(getRoomImagesByRoomId(id));
        }
        return room;
    }
}