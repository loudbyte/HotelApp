package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.dao.FacilityPackageDAO;
import com.epam.hotel.entity.Facility;
import com.epam.hotel.entity.FacilityPackage;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.epam.hotel.util.constant.DAOConstant.*;

public class FacilityPackageDAOImpl implements FacilityPackageDAO {

    private static final Logger LOGGER = Logger.getLogger(FacilityPackageDAOImpl.class);

    private static final String GET_ALL_FACILITY_PACKAGES = "SELECT * FROM facility_package";
    private static final String GET_ONE_BY_ID = "SELECT * FROM facility_package WHERE id = ?";
    private static final String CREATE_FACILITY_PACKAGE = "INSERT INTO facility_package (discount) VALUES (?)";
    private static final String CREATE_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID = "INSERT INTO facility_package_translation " +
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


    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
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
            createUpdateFacilityPackageTranslation(facilityPackage, facilityPackageId, CREATE_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID);
            createFacilityPackageRelation(facilityPackage, facilityPackageId);
            connection.commit();
        } catch (Exception exception) {
            facilityPackageId = ERROR_ID;
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FACILITY_PACKAGES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            facilityPackageList = new ArrayList<>();

            while (resultSet.next()) {
                facilityPackageList.add(getFacilityPackage(resultSet));
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

            if (resultSet.next()) {
                facilityPackage = getFacilityPackage(resultSet);
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
            createUpdateFacilityPackageTranslation(facilityPackage, facilityPackageId, UPDATE_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID);
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACILITY_PACKAGE_RELATION_BY_PACKAGE_ID)) {

                preparedStatement.setLong(1, facilityPackageId);
                preparedStatement.executeUpdate();
            }
            createFacilityPackageRelation(facilityPackage, facilityPackageId);
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

            while (resultSet.next()) {
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

    private FacilityPackage getFacilityPackage(ResultSet resultSet) throws SQLException {
        FacilityDAO facilityDAO = new FacilityDAOImpl();
        FacilityPackage facilityPackage;
        facilityPackage = new FacilityPackage();
        facilityPackage.setId(resultSet.getLong(ID));
        facilityPackage.setDiscount(resultSet.getBigDecimal(DISCOUNT));
        facilityPackage.setFacilityPackageNameMap(getNameOrDescriptionTranslationMap(connection, GET_FACILITY_PACKAGE_TRANSLATION_BY_FACILITY_PACKAGE_ID, facilityPackage.getId(), NAME));
        facilityPackage.setFacilityList(facilityDAO.getFacilityListByFacilityPackageId(facilityPackage.getId()));
        return facilityPackage;
    }

    protected static Map<Integer, String> getNameOrDescriptionTranslationMap(Connection connection, String sql, long entityId, String nameOrDescription) throws SQLException {
        Map<Integer, String> translationNameOrDescriptionMap;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            translationNameOrDescriptionMap = new HashMap<>();
            preparedStatement.setLong(1, entityId);
            ResultSet resultSetNameMap = preparedStatement.executeQuery();

            while (resultSetNameMap.next()) {
                translationNameOrDescriptionMap.put(
                        resultSetNameMap.getInt(ID),
                        resultSetNameMap.getString(nameOrDescription)
                );
            }
        }
        return translationNameOrDescriptionMap;
    }

    private void createUpdateFacilityPackageTranslation(FacilityPackage facilityPackage, long facilityPackageId, String createFacilityPackageTranslationById) throws SQLException {
        for (Map.Entry<Integer, String> entry : facilityPackage.getFacilityPackageNameMap().entrySet()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(createFacilityPackageTranslationById)) {

                preparedStatement.setString(1, entry.getValue());
                preparedStatement.setInt(2, entry.getKey());
                preparedStatement.setLong(3, facilityPackageId);
                preparedStatement.executeUpdate();
            }
        }
    }

    private void createFacilityPackageRelation(FacilityPackage facilityPackage, long facilityPackageId) throws SQLException {
        for (Facility facility : facilityPackage.getFacilityList()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY_PACKAGE_RELATION)) {

                preparedStatement.setLong(1, facility.getId());
                preparedStatement.setLong(2, facilityPackageId);
                preparedStatement.executeUpdate();
            }
        }
    }
}