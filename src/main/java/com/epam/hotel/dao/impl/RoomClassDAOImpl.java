package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.entity.RoomClass;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.epam.hotel.util.constant.DAOConstant.*;

public class RoomClassDAOImpl implements RoomClassDAO {

    private static final Logger LOGGER = Logger.getLogger(RoomClassDAOImpl.class);

    private static final String CREATE_ROOM_CLASS = "INSERT INTO room_class (id) VALUES (DEFAULT) ";
    private static final String CREATE_ROOM_CLASS_TRANSLATION = "INSERT INTO " +
            "room_class_translation (name, description, language_id, room_class_id) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ROOM_CLASSES = "SELECT * FROM room_class";
    private static final String GET_ROOM_CLASS_NAME_MAP_BY_ROOM_CLASS_ID = "SELECT name, language.id FROM room_class_translation " +
            "LEFT JOIN language ON language.id = room_class_translation.language_id WHERE room_class_id = ?";
    private static final String GET_ROOM_CLASS_DESCRIPTION_MAP_BY_ROOM_CLASS_ID = "SELECT description, language.id FROM room_class_translation " +
            "LEFT JOIN language ON language.id = room_class_translation.language_id WHERE room_class_id = ?";
    private static final String GET_ONE_ROOM_CLASS_BY_ROOM_CLASS_ID = "SELECT * FROM room_class WHERE id = ?";
    private static final String UPDATE_ONE_ROOM_CLASS_TRANSLATION_BY_ROOM_CLASS_ID = "UPDATE room_class_translation " +
            "SET name = ?, description = ?, language_id = ?, room_class_id = ? WHERE room_class_id = ? AND language_id = ?";
    private static final String DELETE_ONE_ROOM_CLASS_TRANSLATION_BY_ROOM_CLASS_ID = "DELETE FROM room_class_translation WHERE room_class_id = ?";
    private static final String DELETE_ONE_ROOM_CLASS_BY_ROOM_CLASS_ID = "DELETE FROM room_class WHERE id = ?";

    private static final String SELECT_LAST_VALUE_FROM_ROOM_CLASS_ID_SEQ = "SELECT last_value FROM room_class_id_seq";

    private static final String SAVEPOINT_CREATE_ROOM_CLASS = "savepointCreateRoomClass";
    private static final String SAVEPOINT_UPDATE_ROOM_CLASS = "savepointUpdateRoomClass";
    private static final String SAVEPOINT_DELETE_ROOM_CLASS = "savepointDeleteRoomClass";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(RoomClass roomClass) {

        long roomClassId = ERROR_ID;

        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_CREATE_ROOM_CLASS);

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM_CLASS);
                 PreparedStatement preparedStatementGetSeq = connection.prepareStatement(SELECT_LAST_VALUE_FROM_ROOM_CLASS_ID_SEQ)) {

                preparedStatement.executeUpdate();
                ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

                if (resultSetGetSeq.next())
                    roomClassId = resultSetGetSeq.getLong(1);

            }
            for (Map.Entry<Integer, String> entry : roomClass.getRoomClassNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM_CLASS_TRANSLATION)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setString(2, roomClass.getRoomClassDescriptionMap().get(entry.getKey()));
                    preparedStatement.setInt(3, entry.getKey());
                    preparedStatement.setLong(4, roomClassId);
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (Exception exception) {
            roomClassId = ERROR_ID;
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
            } catch (SQLException rollbackException) {
                LOGGER.error(rollbackException, rollbackException);
            }
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomClassId;
    }

    @Override
    public List<RoomClass> getAll() {
        connection = connectionPool.getConnection();

        List<RoomClass> roomClassList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROOM_CLASSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomClassList.add(getRoomClass(resultSet));
            }
            roomClassList.sort(Comparator.comparing(RoomClass::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomClassList;
    }

    @Override
    public RoomClass getOneById(long id) {
        connection = connectionPool.getConnection();

        RoomClass roomClass = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_ROOM_CLASS_BY_ROOM_CLASS_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roomClass = getRoomClass(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return roomClass;
    }

    @Override
    public void updateOneById(long roomClassId, RoomClass roomClass) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_UPDATE_ROOM_CLASS);

            for (Map.Entry<Integer, String> entry : roomClass.getRoomClassNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_ROOM_CLASS_TRANSLATION_BY_ROOM_CLASS_ID)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setString(2, roomClass.getRoomClassDescriptionMap().get(entry.getKey()));
                    preparedStatement.setInt(3, entry.getKey());
                    preparedStatement.setLong(4, roomClassId);
                    preparedStatement.setLong(5, roomClassId);
                    preparedStatement.setLong(6, entry.getKey());
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
            } catch (SQLException rollbackException) {
                LOGGER.error(rollbackException, rollbackException);
            }
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteOneById(long roomClassId) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_DELETE_ROOM_CLASS);

            deleteRoomClass(roomClassId, DELETE_ONE_ROOM_CLASS_TRANSLATION_BY_ROOM_CLASS_ID);
            deleteRoomClass(roomClassId, DELETE_ONE_ROOM_CLASS_BY_ROOM_CLASS_ID);

            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
            } catch (SQLException rollbackException) {
                LOGGER.error(rollbackException, rollbackException);
            }
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private RoomClass getRoomClass(ResultSet resultSet) throws SQLException {
        RoomClass roomClass = new RoomClass();
        roomClass.setId(resultSet.getInt(ID));
        roomClass.setRoomClassNameMap(FacilityPackageDAOImpl.getNameOrDescriptionTranslationMap(connection, GET_ROOM_CLASS_NAME_MAP_BY_ROOM_CLASS_ID, roomClass.getId(), NAME));
        roomClass.setRoomClassDescriptionMap(FacilityPackageDAOImpl.getNameOrDescriptionTranslationMap(connection, GET_ROOM_CLASS_DESCRIPTION_MAP_BY_ROOM_CLASS_ID, roomClass.getId(), DESCRIPTION));
        return roomClass;
    }

    private void deleteRoomClass(long roomClassId, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, roomClassId);
            preparedStatement.executeUpdate();
        }
    }
}