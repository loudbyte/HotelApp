package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.OrderFacilityDetailDAO;
import com.epam.hotel.entity.FacilityPackageRelation;
import com.epam.hotel.entity.OrderFacilityDetail;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.dao.impl.Constant.*;

public class OrderFacilityDetailDAOImpl implements OrderFacilityDetailDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderFacilityDetailDAOImpl.class);

    private static final String GET_ALL_ORDERS = "SELECT * FROM order_facility_detail";
    private static final String GET_ONE_BY_ID = "SELECT * FROM order_facility_detail WHERE id = ?";
    private static final String GET_ONE_FACILITY_PACKAGE_RELATION_LIST_BY_PACKAGE_ID = "SELECT * FROM facility_package_relation WHERE package_id = ?";
    private static final String CREATE_ORDER_FACILITY_DETAIL = "INSERT INTO order_facility_detail (facility_package_name) VALUES (?)";
    private static final String UPDATE_ONE_BY_ID = "UPDATE order_facility_detail SET facility_package_name = ? WHERE id = ?";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM order_facility_detail WHERE id = ?";
    private static final String CREATE_FACILITY_PACKAGE_RELATION = "INSERT INTO facility_package_relation (facility_id, package_id) " +
            "VALUES (?, ?)";
    private static final String UPDATE_FACILITY_PACKAGE_RELATION_BY_ID = "UPDATE facility_package_relation " +
            "SET facility_id= ? WHERE facility_id = ? AND package_id =?)";
    private static final String DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID = "DELETE FROM facility_package_relation WHERE package_id =?";

    private static final String GET_LAST_VALUE_FROM_ORDER_FACILITY_DETAIL_SEQ = "select last_value FROM order_facility_detail_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(OrderFacilityDetail orderFacilityDetail) {
        connection = connectionPool.getConnection();

        long id = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_FACILITY_DETAIL);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_ORDER_FACILITY_DETAIL_SEQ);) {

            preparedStatement.setString(1, orderFacilityDetail.getFacilityPackageName());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next()) {
                id = resultSetGetSeq.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderFacilityDetailDAOimpl create", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<OrderFacilityDetail> getAll() {
        connection = connectionPool.getConnection();

        List<OrderFacilityDetail> orderFacilityDetailList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            orderFacilityDetailList = new ArrayList<>();

            while (resultSet.next()) {
                OrderFacilityDetail orderFacilityDetail = new OrderFacilityDetail();
                orderFacilityDetail.setId(resultSet.getLong(ID));
                orderFacilityDetail.setFacilityPackageName(resultSet.getString(FACILITY_PACKAGE_NAME));

                orderFacilityDetailList.add(orderFacilityDetail);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderFacilityDetailDAOimpl getAll", e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        orderFacilityDetailList.sort(Comparator.comparing(OrderFacilityDetail::getId));
        return orderFacilityDetailList;

    }

    @Override
    public OrderFacilityDetail getOneById(long id) {
        connection = connectionPool.getConnection();

        OrderFacilityDetail orderFacilityDetail = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                orderFacilityDetail = new OrderFacilityDetail();
                orderFacilityDetail.setId(id);
                orderFacilityDetail.setFacilityPackageName(resultSet.getString(FACILITY_PACKAGE_NAME));
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderFacilityDetailDAOimpl getOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return orderFacilityDetail;
    }

    @Override
    public void updateOneById(long id, OrderFacilityDetail orderFacilityDetail) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            preparedStatement.setString(1, orderFacilityDetail.getFacilityPackageName());
            preparedStatement.setLong(2, orderFacilityDetail.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderFacilityDetailDAOImpl updateOneById", e);
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
            LOGGER.error("SQLException in OrderFacilityDetailDAOImpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void createFacilityPackageRelation(long facilityId, long packageId) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE_RELATION)) {
            preparedStatement.setLong(1, facilityId);
            preparedStatement.setLong(2, packageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in OrderFacilityDetailDAOImpl createFacilityPackageRelation", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void updateFacilityPackageRelationByPackageId(long newFacilityId, long facilityId, long packageId) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FACILITY_PACKAGE_RELATION_BY_ID)) {
            preparedStatement.setLong(1, newFacilityId);
            preparedStatement.setLong(2, facilityId);
            preparedStatement.setLong(3, packageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl updateFacilityPackageRelationByPackageId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void deleteFacilityPackageRelationByPackageId(long packageId) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID)) {
            preparedStatement.setLong(1, packageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl updateFacilityPackageRelationByPackageId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public List<FacilityPackageRelation> getFacilityPackageRelationByPackageId(long packageId) {
        connection = connectionPool.getConnection();

        List<FacilityPackageRelation> facilityPackageRelationList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_FACILITY_PACKAGE_RELATION_LIST_BY_PACKAGE_ID)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            facilityPackageRelationList = new ArrayList<>();
            preparedStatement.setLong(1, packageId);
            while (resultSet.next()){
                FacilityPackageRelation facilityPackageRelation = new FacilityPackageRelation();
                facilityPackageRelation.setFacilityId(resultSet.getLong(FACILITY_ID));
                facilityPackageRelation.setFacilityId(resultSet.getLong(PACKAGE_ID));
                facilityPackageRelationList.add(facilityPackageRelation);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOimpl getFacilityPackageRelationByPackageId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        Comparator.comparing(FacilityPackageRelation::getFacilityId);
        return facilityPackageRelationList;
    }
}