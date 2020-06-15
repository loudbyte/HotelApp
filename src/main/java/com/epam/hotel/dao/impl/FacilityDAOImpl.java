package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.FacilityDAO;
import com.epam.hotel.entity.Facility;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class FacilityDAOImpl implements FacilityDAO {

    private static final Logger LOGGER = Logger.getLogger(FacilityDAOImpl.class);
    private static final String GET_ALL_FACILITIES = "SELECT * FROM facility";
    private static final String GET_ONE_BY_ID = "SELECT * FROM facility WHERE id = ?";
    private static final String GET_ONE_BY_NAME = "SELECT * FROM facility WHERE name = ?";
    private static final String CREATE_FACILITY = "INSERT INTO facility (name, price, description)" +
            "VALUES (?, ?, ?)";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM facility WHERE id = ?";
    private static final String UPDATE_ONE_BY_ID = "UPDATE facility " +
            "SET id = ?, name = ?, price = ?, description = ?" +
            "WHERE id = ?";
    private static final String GET_FACILITY_LIST_BY_PACKAGE_ID = "SELECT * FROM facility AS f " +
            "JOIN facility_package_relation AS fpr ON f.id = fpr.facility_id WHERE fpr.package_id = ?";

    private static final String GET_LAST_VALUE_FROM_FACILITY_SEQ = "SELECT last_value FROM facility_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public long create(Facility facility) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACILITY);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_FACILITY_SEQ);) {

            preparedStatement.setString(1, facility.getName());
            preparedStatement.setBigDecimal(2, facility.getPrice());
            preparedStatement.setString(3, facility.getDescription());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1);

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl create", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public Facility getByName(String name) {
        connection = connectionPool.getConnection();

        Facility facility = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            facility = getFacility(facility, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl getByName", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facility;
    }

    @Override
    public List<Facility> getAll() {
        connection = connectionPool.getConnection();

        List<Facility> facilityList = new ArrayList<>();

        Facility facility = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FACILITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                facility = new Facility();
                facility.setId(resultSet.getInt(ID));
                facility.setName(resultSet.getString(NAME));
                facility.setPrice(resultSet.getBigDecimal(PRICE));
                facility.setDescription(resultSet.getString(DESCRIPTION));
                facilityList.add(facility);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl getAll", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        facilityList.sort(Comparator.comparing(Facility::getId));
        return facilityList;
    }

    @Override
    public Facility getOneById(long id) {
        connection = connectionPool.getConnection();

        Facility facility = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            facility = getFacility(facility, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl getOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return facility;
    }

    @Override
    public void updateOneById(long id, Facility facility) {
        connection = connectionPool.getConnection();

        String sql = UPDATE_ONE_BY_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setLong(1, facility.getId());
            preparedStatement.setString(2, facility.getName());
            preparedStatement.setBigDecimal(3, facility.getPrice());
            preparedStatement.setString(4, facility.getDescription());
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl updateOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public List<Facility> getFacilityListByPackageId(long packageId) {
        connection = connectionPool.getConnection();

        List<Facility> facilityList = null;
        Facility facility = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FACILITY_LIST_BY_PACKAGE_ID)) {
            preparedStatement.setLong(1, packageId);
            ResultSet resultSet = preparedStatement.executeQuery();

            facilityList = new ArrayList<>();
            while (resultSet.next()) {
                facility = new Facility();
                facility.setId(resultSet.getLong(ID));
                facility.setName(resultSet.getString(NAME));
                facility.setPrice(resultSet.getBigDecimal(PRICE));
                facility.setDescription(resultSet.getString(DESCRIPTION));
                facilityList.add(facility);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in FacilityDAOImpl getFacilityListByPackageId", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        facilityList.sort(Comparator.comparing(Facility::getId));
        return facilityList;
    }

    private Facility getFacility(Facility facility, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            facility = new Facility();
            facility.setId(resultSet.getInt(ID));
            facility.setName(resultSet.getString(NAME));
            facility.setPrice(resultSet.getBigDecimal(PRICE));
            facility.setDescription(resultSet.getString(DESCRIPTION));
        }
        return facility;
    }
}