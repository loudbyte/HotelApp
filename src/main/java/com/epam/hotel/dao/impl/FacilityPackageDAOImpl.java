package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.FacilityPackage;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class FacilityPackageDAOImpl implements FacilityPackageDAO {

    private static final Logger LOGGER = Logger.getLogger(FacilityPackageDAOImpl.class);

    private static final String GET_ALL_FACILITY_PACKAGES = "SELECT * FROM facility_package";
    private static final String GET_ONE_BY_ID = "SELECT * FROM facility_package WHERE id = ?";
    private static final String CREATE_FACILITY_PACKAGE = "INSERT INTO facility_package (discount) VALUES (?)";
    private static final String CREATE_FACILITY_PACKAGE_TRANSLATION_BY_ID = "INSERT INTO facility_package_translation " +
            "(name, language_id, facility_package_id) VALUES (?, ?, ?)";
    private static final String GET_FACILITY_PACKAGE_NAME_MAP_BY_FACILITY_PACKAGE_ID = "SELECT name, locale FROM facility_package_translation " +
            "LEFT JOIN language ON language.id = facility_package_translation.language_id WHERE facility_package_id = ?";
    private static final String GET_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID = "SELECT language.id, name " +
            "FROM facility_package_translation LEFT JOIN language ON language.id = facility_package_translation.language_id WHERE facility_package_id = ?";
    private static final String UPDATE_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID = "UPDATE facility_package_translation SET name = ? WHERE language_id = ? AND facility_package_id = ?";
    private static final String UPDATE_FACILITY_PACKAGE_BY_FACILITY_PACKAGE_ID = "UPDATE facility_package SET discount = ? WHERE id = ?";
    private static final String DELETE_FACILITY_PACKAGE_BY_ID = "DELETE FROM facility_package WHERE id = ?";
    private static final String DELETE_FACILITY_PACKAGE_TRANSLATION_BY_PACKAGE_ID = "DELETE FROM facility_package_translation WHERE facility_package_id = ?";
    private static final String CREATE_FACILITY_PACKAGE_RELATION = "INSERT INTO facility_package_relation (facility_id, facility_package_id) " +
            "VALUES (?, ?)";
    private static final String DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID = "DELETE FROM facility_package_relation WHERE facility_package_id =?";

    private static final String GET_LAST_VALUE_FROM_PACKAGE_SEQ = "select last_value FROM facility_package_id_seq";

    private static final String SAVEPOINT_CREATE_FACILITY_PACKAGE = "savepointCreateFacilityPackage";
    private static final String SAVEPOINT_UPDATE_FACILITY_PACKAGE = "savepointUpdateFacilityPackage";
    private static final String SAVEPOINT_DELETE_FACILITY_PACKAGE = "savepointDeleteFacilityPackage";


    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    public long create(FacilityPackage facilityPackage) {
        long facilityPackageId = ERROR_ID;

        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_CREATE_FACILITY_PACKAGE);

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE);
                 PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_PACKAGE_SEQ)) {

                preparedStatement.setBigDecimal(1, facilityPackage.getDiscount());
                preparedStatement.executeUpdate();
                ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

                if (resultSetGetSeq.next())
                    facilityPackageId = resultSetGetSeq.getLong(1);

            }

            for (Map.Entry<Integer, String> entry : facilityPackage.getFacilityPackageNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE_TRANSLATION_BY_ID)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setInt(2, entry.getKey());
                    preparedStatement.setLong(3, facilityPackageId);
                    preparedStatement.executeUpdate();
                }
            }
            for (Facility facility : facilityPackage.getFacilityList()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE_RELATION)) {

                    preparedStatement.setLong(1, facility.getId());
                    preparedStatement.setLong(2, facilityPackageId);
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
        return facilityPackageId;
    }

    @Override
    public List<FacilityPackage> getAll() {
        connection = connectionPool.getConnection();

        List<FacilityPackage> facilityPackageList = null;
        FacilityPackage facilityPackage = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FACILITY_PACKAGES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            facilityPackageList = new ArrayList<>();

            FacilityDAO facilityDAO = new FacilityDAOImpl();
            Map<Integer, String> facilityPackageNameMap = null;

            while (resultSet.next()) {
                facilityPackage = new FacilityPackage();
                facilityPackage.setId(resultSet.getLong(ID));
                facilityPackage.setDiscount(resultSet.getBigDecimal(DISCOUNT));
                try (PreparedStatement preparedStatementNameMap = connection.prepareStatement(GET_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID)) {

                    facilityPackageNameMap = new HashMap<>();
                    preparedStatementNameMap.setLong(1, facilityPackage.getId());
                    ResultSet resultSetNameMap = preparedStatementNameMap.executeQuery();

                    while (resultSetNameMap.next()){
                        facilityPackageNameMap.put(
                                resultSetNameMap.getInt(ID),
                                resultSetNameMap.getString(NAME)
                        );
                    }
                }
                facilityPackage.setFacilityPackageNameMap(facilityPackageNameMap);
                facilityPackage.setFacilityList(facilityDAO.getFacilityListByFacilityPackageId(facilityPackage.getId()));

                facilityPackageList.add(facilityPackage);
            }
            facilityPackageList.sort(Comparator.comparing(FacilityPackage::getId));

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return facilityPackageList;
    }

    @Override
    public FacilityPackage getOneById(long id) {
        connection = connectionPool.getConnection();

        FacilityPackage facilityPackage = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            FacilityDAO facilityDAO = new FacilityDAOImpl();
            Map<Integer, String> facilityPackageNameMap = null;

            if (resultSet.next()) {
                facilityPackage = new FacilityPackage();
                facilityPackage.setId(resultSet.getLong(ID));
                facilityPackage.setDiscount(resultSet.getBigDecimal(DISCOUNT));
                try (PreparedStatement preparedStatementNameMap = connection.prepareStatement(GET_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID)) {

                    facilityPackageNameMap = new HashMap<>();
                    preparedStatementNameMap.setLong(1, facilityPackage.getId());
                    ResultSet resultSetNameMap = preparedStatementNameMap.executeQuery();

                    while (resultSetNameMap.next()){
                        facilityPackageNameMap.put(
                                resultSetNameMap.getInt(ID),
                                resultSetNameMap.getString(NAME)
                        );
                    }
                }
                facilityPackage.setFacilityPackageNameMap(facilityPackageNameMap);
                facilityPackage.setFacilityList(facilityDAO.getFacilityListByFacilityPackageId(facilityPackage.getId()));
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return facilityPackage;
    }

    @Override
    public void updateOneById(long facilityPackageId, FacilityPackage facilityPackage) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_UPDATE_FACILITY_PACKAGE);
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FACILITY_PACKAGE_BY_FACILITY_PACKAGE_ID)) {

                preparedStatement.setBigDecimal(1, facilityPackage.getDiscount());
                preparedStatement.setLong(2, facilityPackageId);
                preparedStatement.executeUpdate();

            }
            for (Map.Entry<Integer, String> entry : facilityPackage.getFacilityPackageNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setInt(2, entry.getKey());
                    preparedStatement.setLong(3, facilityPackageId);
                    preparedStatement.executeUpdate();
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID)) {

                preparedStatement.setLong(1, facilityPackageId);
                preparedStatement.executeUpdate();
            }
            for (Facility facility : facilityPackage.getFacilityList()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE_RELATION)) {

                    preparedStatement.setLong(1, facility.getId());
                    preparedStatement.setLong(2, facilityPackageId);
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
    public void deleteOneById(long facilityPackageId) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_DELETE_FACILITY_PACKAGE);

            deleteFacilityPackage(facilityPackageId, DELETE_FACILITY_PACKAGE_TRANSLATION_BY_PACKAGE_ID);
            deleteFacilityPackage(facilityPackageId, DELETE_FACILITY_PACKAGE_BY_ID);
            deleteFacilityPackage(facilityPackageId, DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID);

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

    private void deleteFacilityPackage(long facilityPackageId, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, facilityPackageId);
            preparedStatement.executeUpdate();
        }
    }

    public Map<String, String> getFacilityPackageNameMapByFacilityPackageId(long facilityPackageId) {
        connection = connectionPool.getConnection();

        Map<String, String> facilityNameMap = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FACILITY_PACKAGE_NAME_MAP_BY_FACILITY_PACKAGE_ID)) {
            preparedStatement.setLong(1, facilityPackageId);
            ResultSet resultSet = preparedStatement.executeQuery();

            facilityNameMap = new HashMap<>();

            while (resultSet.next()){
                facilityNameMap.put(
                        resultSet.getString(LOCALE),
                        resultSet.getString(NAME)
                );
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facilityNameMap;
    }
}