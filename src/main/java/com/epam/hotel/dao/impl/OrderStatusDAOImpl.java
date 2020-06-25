package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.OrderStatusDAO;
import com.epam.hotel.entity.OrderStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.epam.hotel.util.constant.DAOConstant.*;

public class OrderStatusDAOImpl implements OrderStatusDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderStatusDAOImpl.class);

    private static final String CREATE_ORDER_STATUS = "INSERT INTO order_status (id) VALUES (DEFAULT)";
    private static final String CREATE_ORDER_STATUS_TRANSLATION = "INSERT INTO " +
            "order_status_translation (name, language_id, order_status_id) VALUES (?, ?, ?)";
    private static final String GET_ALL_ORDER_STATUSES = "SELECT * FROM order_status";
    private static final String GET_ORDER_STATUS_NAME_MAP_BY_ORDER_STATUS_ID = "SELECT name, language.id FROM order_status_translation " +
            "LEFT JOIN language ON language.id = order_status_translation.language_id WHERE order_status_id = ?";
    private static final String GET_ONE_ORDER_STATUS_BY_ORDER_STATUS_ID = "SELECT * FROM order_status WHERE id = ?";
    private static final String UPDATE_ONE_ORDER_STATUS_TRANSLATION_BY_ORDER_STATUS_ID = "UPDATE order_status_translation " +
            "SET name = ?, language_id = ?, order_status_id = ? WHERE order_status_id = ? AND language_id = ?";
    private static final String DELETE_ONE_ORDER_STATUS_TRANSLATION_BY_ID = "DELETE FROM order_status_translation WHERE order_status_id = ?";
    private static final String DELETE_ONE_ORDER_STATUS_BY_ID = "DELETE FROM order_status WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ORDER_STATUS_SEQ = "SELECT last_value FROM order_status_id_seq";

    private static final String SAVEPOINT_CREATE_ORDER_STATUS = "savepointCreateOrderStatus";
    private static final String SAVEPOINT_UPDATE_ORDER_STATUS = "savepointUpdateOrderStatus";
    private static final String SAVEPOINT_DELETE_ORDER_STATUS = "savepointDeleteOrderStatus";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(OrderStatus orderStatus) {

        long orderStatusId = ERROR_ID;

        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_CREATE_ORDER_STATUS);

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_STATUS);
                 PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ORDER_STATUS_SEQ)) {

                preparedStatement.executeUpdate();
                ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

                if (resultSetGetSeq.next())
                    orderStatusId = resultSetGetSeq.getLong(1);

            }
            for (Map.Entry<Integer, String> entry : orderStatus.getOrderStatusNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_STATUS_TRANSLATION)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setInt(2, entry.getKey());
                    preparedStatement.setLong(3, orderStatusId);
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (Exception exception) {
            orderStatusId = ERROR_ID;
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
        return orderStatusId;
    }

    @Override
    public List<OrderStatus> getAll() {
        connection = connectionPool.getConnection();

        List<OrderStatus> orderStatusList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDER_STATUSES);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            orderStatusList = new ArrayList<>();

            while (resultSet.next()) {
                orderStatusList.add(getOrderStatus(resultSet));
            }
            orderStatusList.sort(Comparator.comparing(OrderStatus::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return orderStatusList;
    }

    @Override
    public OrderStatus getOneById(long id) {
        connection = connectionPool.getConnection();

        OrderStatus orderStatus = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_ORDER_STATUS_BY_ORDER_STATUS_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                orderStatus = getOrderStatus(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return orderStatus;
    }

    @Override
    public void updateOneById(long id, OrderStatus orderStatus) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_UPDATE_ORDER_STATUS);

            for (Map.Entry<Integer, String> entry : orderStatus.getOrderStatusNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_ORDER_STATUS_TRANSLATION_BY_ORDER_STATUS_ID)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setInt(2, entry.getKey());
                    preparedStatement.setLong(3, id);
                    preparedStatement.setLong(4, id);
                    preparedStatement.setInt(5, entry.getKey());
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
    public void deleteOneById(long orderStatusId) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_DELETE_ORDER_STATUS);

            deleteOrderStatus(orderStatusId, DELETE_ONE_ORDER_STATUS_TRANSLATION_BY_ID);
            deleteOrderStatus(orderStatusId, DELETE_ONE_ORDER_STATUS_BY_ID);

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

    private void deleteOrderStatus(long orderStatusId, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, orderStatusId);
            preparedStatement.executeUpdate();
        }
    }

    private OrderStatus getOrderStatus(ResultSet resultSet) throws SQLException {
        OrderStatus orderStatus;
        orderStatus = new OrderStatus();
        orderStatus.setId(resultSet.getLong(ID));
        orderStatus.setOrderStatusNameMap(FacilityPackageDAOImpl
                .getNameOrDescriptionTranslationMap(connection, GET_ORDER_STATUS_NAME_MAP_BY_ORDER_STATUS_ID, orderStatus.getId(), NAME));
        return orderStatus;
    }
}