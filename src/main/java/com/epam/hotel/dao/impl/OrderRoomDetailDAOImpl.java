package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.util.constant.DAOConstant;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.util.constant.DAOConstant.*;

public class OrderRoomDetailDAOImpl implements OrderRoomDetailDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderRoomDetailDAOImpl.class);

    private static final String GET_ALL_ORDER_ROOM_DETAILS = "SELECT * FROM order_room_detail";
    private static final String GET_ALL_BY_ORDER_ID = "SELECT * FROM order_room_detail WHERE order_main_id = ?";
    private static final String GET_ONE_BY_ID = "SELECT * FROM order_room_detail WHERE id = ?";
    private static final String CREATE_ORDER_ROOM_DETAIL = "INSERT INTO " +
            "order_room_detail (room_id, facility_package_id, order_main_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ONE_BY_ID = "UPDATE " +
            "order_room_detail SET room_id = ?, facility_package_id = ?, order_main_id = ?, start_date= ?, end_date = ? " +
            "WHERE id = ?";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM order_room_detail WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_ORDER_ROOM_DETAIL_SEQ = "select last_value FROM order_room_detail_id_seq";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(OrderRoomDetail orderRoomDetail) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_ROOM_DETAIL);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ORDER_ROOM_DETAIL_SEQ)) {

            createUpdateOrderRoomDetail(orderRoomDetail, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1);

        } catch (SQLException | ParseException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<OrderRoomDetail> getAll() {
        connection = connectionPool.getConnection();

        List<OrderRoomDetail> orderRoomDetailList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDER_ROOM_DETAILS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            orderRoomDetailList = new ArrayList<>();

            while (resultSet.next()) {
                orderRoomDetailList.add(getOrderRoomDetail(resultSet));
            }
            orderRoomDetailList.sort(Comparator.comparing(OrderRoomDetail::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return orderRoomDetailList;
    }

    public List<OrderRoomDetail> getAllByOrderId(long orderId) {
        connection = connectionPool.getConnection();

        List<OrderRoomDetail> orderRoomDetailList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_ORDER_ID)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            orderRoomDetailList = new ArrayList<>();
            while (resultSet.next()) {
                orderRoomDetailList.add(getOrderRoomDetail(resultSet));
            }
            orderRoomDetailList.sort(Comparator.comparing(OrderRoomDetail::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return orderRoomDetailList;
    }

    @Override
    public OrderRoomDetail getOneById(long id) {
        connection = connectionPool.getConnection();

        OrderRoomDetail orderRoomDetail = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderRoomDetail = getOrderRoomDetail(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return orderRoomDetail;
    }

    @Override
    public void updateOneById(long id, OrderRoomDetail orderRoomDetail) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            createUpdateOrderRoomDetail(orderRoomDetail, preparedStatement);
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | ParseException exception) {
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

    private OrderRoomDetail getOrderRoomDetail(ResultSet resultSet) throws SQLException {
        OrderRoomDetail orderRoomDetail = new OrderRoomDetail();
        orderRoomDetail.setId(resultSet.getLong(ID));
        orderRoomDetail.setRoomId(resultSet.getLong(ROOM_ID));
        orderRoomDetail.setFacilityPackageId(resultSet.getLong(DAOConstant.FACILITY_PACKAGE_ID));
        orderRoomDetail.setOrderMainId(resultSet.getLong(ORDER_MAIN_ID));
        orderRoomDetail.setStartDate(resultSet.getString(START_DATE));
        orderRoomDetail.setEndDate(resultSet.getString(END_DATE));
        return orderRoomDetail;
    }

    private void createUpdateOrderRoomDetail(OrderRoomDetail orderRoomDetail, PreparedStatement preparedStatement) throws ParseException, SQLException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        java.util.Date myStartDate = format.parse(orderRoomDetail.getStartDate());
        java.util.Date myEndDate = format.parse(orderRoomDetail.getEndDate());
        Date sqlStartDate = new Date(myStartDate.getTime());
        Date sqlEndDate = new Date(myEndDate.getTime());

        preparedStatement.setLong(1, orderRoomDetail.getRoomId());
        preparedStatement.setLong(2, orderRoomDetail.getFacilityPackageId());
        preparedStatement.setLong(3, orderRoomDetail.getOrderMainId());
        preparedStatement.setDate(4, sqlStartDate);
        preparedStatement.setDate(5, sqlEndDate);
    }
}