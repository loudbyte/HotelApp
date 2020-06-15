package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.entity.FacilityPackage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class FacilityPackageDAOImpl implements FacilityPackageDAO {

    private static final Logger LOGGER = Logger.getLogger(FacilityPackageDAOImpl.class);

    private static final String GET_ALL_ORDERS = "SELECT * FROM facility_package";
    private static final String GET_ONE_BY_ID = "SELECT * FROM facility_package WHERE id = ?";
    private static final String CREATE_FACILITY_PACKAGE = "INSERT INTO facility_package (facility_package_name) VALUES (?)";
    private static final String UPDATE_ONE_BY_ID = "UPDATE facility_package SET facility_package_name = ? WHERE id = ?";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM facility_package WHERE id = ?";
    private static final String CREATE_FACILITY_PACKAGE_RELATION = "INSERT INTO facility_package_relation (facility_id, package_id) " +
            "VALUES (?, ?)";
    private static final String DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID = "DELETE FROM facility_package_relation WHERE package_id =?";

    private static final String GET_LAST_VALUE_FROM_PACKAGE_SEQ = "select last_value FROM facility_package_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(FacilityPackage facilityPackage) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_PACKAGE_SEQ);) {

            preparedStatement.setString(1, facilityPackage.getFacilityPackageName());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next()) {
                id = resultSetGetSeq.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityPackageDAOImpl create", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<FacilityPackage> getAll() {
        connection = connectionPool.getConnection();

        List<FacilityPackage> facilityPackageList = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            facilityPackageList = new ArrayList<>();

            while (resultSet.next()) {
                FacilityPackage facilityPackage = new FacilityPackage();
                facilityPackage.setId(resultSet.getLong(ID));
                facilityPackage.setFacilityPackageName(resultSet.getString(FACILITY_PACKAGE_NAME));

                facilityPackageList.add(facilityPackage);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityPackageDAOImpl getAll", e);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        facilityPackageList.sort(Comparator.comparing(FacilityPackage::getId));
        return facilityPackageList;
    }

    @Override
    public FacilityPackage getOneById(long id) {
        connection = connectionPool.getConnection();

        FacilityPackage facilityPackage = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                facilityPackage = new FacilityPackage();
                facilityPackage.setId(id);
                facilityPackage.setFacilityPackageName(resultSet.getString(FACILITY_PACKAGE_NAME));
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityPackageDAOImpl getOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facilityPackage;
    }

    @Override
    public void updateOneById(long id, FacilityPackage facilityPackage) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            preparedStatement.setString(1, facilityPackage.getFacilityPackageName());
            preparedStatement.setLong(2, facilityPackage.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityPackageDAOImpl updateOneById", e);
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
            LOGGER.error("SQLException in FacilityPackageDAOImpl deleteOneById", e);
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
            LOGGER.error("SQLException in FacilityPackageDAOImpl createFacilityPackageRelation", e);
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
            LOGGER.error("SQLException in FacilityPackageDAOImpl updateFacilityPackageRelationByPackageId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}