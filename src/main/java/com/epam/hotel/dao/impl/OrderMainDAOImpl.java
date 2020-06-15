package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.entity.OrderMain;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class OrderMainDAOImpl implements OrderMainDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderMainDAOImpl.class);

    private static final String GET_ALL_ORDERS = "SELECT " +
            "order_main.id AS order_id, status.id AS status_id, order_main.person_id, order_main.date, en, ru " +
            "FROM order_main LEFT JOIN status ON order_main.status_id = status.id";
    private static final String GET_ONE_BY_ID = "SELECT " +
            "order_main.id AS order_id, status.id AS status_id, order_main.person_id, order_main.date, en, ru " +
            "FROM order_main, status WHERE order_main.status_id = status.id AND order_main.id = ?";
    private static final String GET_ALL_BY_PERSON_ID = "SELECT " +
            "order_main.id AS order_id, status.id AS status_id, order_main.person_id, order_main.date, en, ru " +
            "FROM order_main, status WHERE order_main.status_id = status.id AND order_main.person_id = ?";
    private static final String CREATE_ORDER_MAIN = "INSERT INTO order_main (person_id, status_id, date) VALUES (?, ?, ?)";
    private static final String UPDATE_ONE_BY_ID = "UPDATE order_main SET person_id = ?, status_id = ?, date = ? WHERE id = ?";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM order_main WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ORDER_MAIN_SEQ = "select last_value FROM order_main_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(OrderMain orderMain) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_MAIN);
             PreparedStatement preparedStatement1GetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ORDER_MAIN_SEQ);) {

            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            java.util.Date utilDate = format.parse(orderMain.getDate());
            Date sqlDate = new Date(utilDate.getTime());

            preparedStatement.setLong(1, orderMain.getPersonId());
            preparedStatement.setLong(2, orderMain.getStatusId());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatement1GetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getInt(1);

        } catch (SQLException | ParseException e) {
            LOGGER.error("SQLException or ParseException in OrderMainDAOImpl create ", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return id;
    }

    @Override
    public List<OrderMain> getAll() {
        connection = connectionPool.getConnection();

        List<OrderMain> orderMainList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            orderMainList = new ArrayList<>();

            while (resultSet.next()) {
                OrderMain orderMain = new OrderMain();
                orderMain.setId(resultSet.getLong(ORDER_ID));
                orderMain.setStatusId(resultSet.getLong(STATUS_ID));
                orderMain.setPersonId(resultSet.getLong(PERSON_ID));
                orderMain.setStatusEn(resultSet.getString(EN));
                orderMain.setStatusRu(resultSet.getString(RU));
                orderMain.setDate(resultSet.getString(DATE));

                orderMainList.add(orderMain);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderMainDAOImpl getAll", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        orderMainList.sort(Comparator.comparing(OrderMain::getId));
        return orderMainList;
    }

    public List<OrderMain> getAllByPersonId(long id) {
        connection = connectionPool.getConnection();

        List<OrderMain> orderMainList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_PERSON_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            orderMainList = new ArrayList<>();

            while (resultSet.next()) {
                OrderMain orderMain = new OrderMain();
                orderMain.setId(resultSet.getLong(ORDER_ID));
                orderMain.setStatusId(resultSet.getLong(STATUS_ID));
                orderMain.setPersonId(resultSet.getLong(PERSON_ID));
                orderMain.setStatusEn(resultSet.getString(EN));
                orderMain.setStatusRu(resultSet.getString(RU));
                orderMain.setDate(resultSet.getString(DATE));

                orderMainList.add(orderMain);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderMainDAOImpl getAllByPersonId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        orderMainList.sort(Comparator.comparing(OrderMain::getId));
        return orderMainList;
    }

    @Override
    public OrderMain getOneById(long id) {
        connection = connectionPool.getConnection();

        OrderMain orderMain = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderMain = new OrderMain();
                orderMain.setId(id);
                orderMain.setStatusId(resultSet.getLong(STATUS_ID));
                orderMain.setPersonId(resultSet.getLong(PERSON_ID));
                orderMain.setStatusEn(resultSet.getString(EN));
                orderMain.setStatusRu(resultSet.getString(RU));
                orderMain.setDate(resultSet.getString(DATE));
                preparedStatement.executeQuery();
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderMainDAOImpl getOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return orderMain;
    }

    @Override
    public void updateOneById(long id, OrderMain orderMain) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            java.util.Date utilDate = format.parse(orderMain.getDate());
            Date sqlDate = new Date(utilDate.getTime());

            preparedStatement.setLong(1, orderMain.getPersonId());
            preparedStatement.setLong(2, orderMain.getStatusId());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setLong(4, orderMain.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException | ParseException e) {
            LOGGER.error("SQLException or ParseException in OrderMainDAOImpl updateOneById", e);
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

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderMainDAOImpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}