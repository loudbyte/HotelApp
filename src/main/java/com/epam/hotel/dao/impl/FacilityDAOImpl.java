package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.entity.Facility;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.epam.hotel.util.constant.DAOConstant.*;

public class FacilityDAOImpl implements FacilityDAO {

    private static final Logger LOGGER = Logger.getLogger(FacilityDAOImpl.class);
    private static final String CREATE_FACILITY = "INSERT INTO facility (price) VALUES (?) ";
    private static final String CREATE_FACILITY_TRANSLATION = "INSERT INTO " +
            "facility_translation (name, description, language_id, facility_id) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_FACILITIES = "SELECT * FROM facility";
    private static final String GET_FACILITY_NAME_MAP_BY_FACILITY_ID = "SELECT name, language.id FROM facility_translation " +
            "LEFT JOIN language ON language.id = facility_translation.language_id WHERE facility_id = ?";
    private static final String GET_FACILITY_DESCRIPTION_MAP_BY_FACILITY_ID = "SELECT description, language.id FROM facility_translation " +
            "LEFT JOIN language ON language.id = facility_translation.language_id WHERE facility_id = ?";
    private static final String GET_FACILITY_LIST_BY_FACILITY_PACKAGE_ID = "SELECT * FROM facility AS f " +
            "JOIN facility_package_relation AS fpr ON f.id = fpr.facility_id WHERE fpr.facility_package_id = ?";
    private static final String GET_ONE_FACILITY_BY_FACILITY_ID = "SELECT * FROM facility WHERE id = ?";
    private static final String UPDATE_ONE_FACILITY_BY_FACILITY_ID = "UPDATE facility " +
            "SET price = ? WHERE id = ?";
    private static final String UPDATE_ONE_FACILITY_TRANSLATION_BY_FACILITY_ID = "UPDATE facility_translation " +
            "SET name = ?, description = ?, language_id = ?, facility_id = ? WHERE facility_id = ? AND language_id = ?";
    private static final String DELETE_ONE_FACILITY_TRANSLATION_BY_ID = "DELETE FROM facility_translation WHERE facility_id = ?";
    private static final String DELETE_ONE_FACILITY_BY_ID = "DELETE FROM facility WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_FACILITY_SEQ = "SELECT last_value FROM facility_id_seq";

    private static final String SAVEPOINT_CREATE_FACILITY = "savepointCreateFacility";
    private static final String SAVEPOINT_UPDATE_FACILITY = "savepointUpdateFacility";
    private static final String SAVEPOINT_DELETE_FACILITY = "savepointDeleteFacility";

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(Facility facility) {

        long facilityId = ERROR_ID;

        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_CREATE_FACILITY);

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY);
                 PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_FACILITY_SEQ)) {

                preparedStatement.setBigDecimal(1, facility.getPrice());
                preparedStatement.executeUpdate();
                ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

                if (resultSetGetSeq.next())
                    facilityId = resultSetGetSeq.getLong(1);

            }
            for (Map.Entry<Integer, String> entry : facility.getFacilityNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_TRANSLATION)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setString(2, facility.getFacilityDescriptionMap().get(entry.getKey()));
                    preparedStatement.setInt(3, entry.getKey());
                    preparedStatement.setLong(4, facilityId);
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (Exception exception) {
            facilityId = ERROR_ID;
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
        return facilityId;
    }

    @Override
    public List<Facility> getAll() {
        connection = connectionPool.getConnection();

        List<Facility> facilityList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FACILITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                facilityList.add(getFacility(resultSet));
            }
            facilityList.sort(Comparator.comparing(Facility::getId));

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facilityList;
    }

    @Override
    public Facility getOneById(long id) {
        connection = connectionPool.getConnection();

        Facility facility = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_FACILITY_BY_FACILITY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                facility = getFacility(resultSet);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facility;
    }

    public List<Facility> getFacilityListByFacilityPackageId(long packageId) {
        connection = connectionPool.getConnection();

        List<Facility> facilityList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FACILITY_LIST_BY_FACILITY_PACKAGE_ID)) {
            preparedStatement.setLong(1, packageId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                facilityList.add(getFacility(resultSet));
            }
            facilityList.sort(Comparator.comparing(Facility::getId));

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facilityList;
    }

    @Override
    public void updateOneById(long facilityId, Facility facility) {

        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_UPDATE_FACILITY);

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_FACILITY_BY_FACILITY_ID)) {
                preparedStatement.setBigDecimal(1, facility.getPrice());
                preparedStatement.setLong(2, facilityId);
                preparedStatement.executeUpdate();
            }
            for (Map.Entry<Integer, String> entry : facility.getFacilityNameMap().entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_FACILITY_TRANSLATION_BY_FACILITY_ID)) {

                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setString(2, facility.getFacilityDescriptionMap().get(entry.getKey()));
                    preparedStatement.setInt(3, entry.getKey());
                    preparedStatement.setLong(4, facilityId);
                    preparedStatement.setLong(5, facilityId);
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
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();
        Savepoint savepoint = null;

        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVEPOINT_DELETE_FACILITY);

            deleteFacility(id, DELETE_ONE_FACILITY_TRANSLATION_BY_ID);
            deleteFacility(id, DELETE_ONE_FACILITY_BY_ID);

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

    private void deleteFacility(long facilityId, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, facilityId);
            preparedStatement.executeUpdate();
        }
    }

    private Facility getFacility(ResultSet resultSet) throws SQLException {
        Facility facility = new Facility();
        facility.setId(resultSet.getInt(ID));
        facility.setPrice(resultSet.getBigDecimal(PRICE));
        facility.setFacilityNameMap(FacilityPackageDAOImpl
                .getNameOrDescriptionTranslationMap(connection, GET_FACILITY_NAME_MAP_BY_FACILITY_ID, facility.getId(), NAME));
        facility.setFacilityDescriptionMap(FacilityPackageDAOImpl
                .getNameOrDescriptionTranslationMap(connection, GET_FACILITY_DESCRIPTION_MAP_BY_FACILITY_ID, facility.getId(), DESCRIPTION));
        return facility;
    }
}