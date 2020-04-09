package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.ServiceDAO;
import com.epam.hotel.entity.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl extends BaseDAOImpl implements ServiceDAO {

    private final String GET_ALL_SERVICES = "SELECT * FROM service";
    private final String GET_ONE_BY_ID = "SELECT * FROM service WHERE id = ";
    private final String GET_ONE_BY_NAME = "SELECT * FROM service WHERE name = ";
    private final String GET_ONE_BY_SERVICE_PACKAGE_ID = "SELECT * FROM service WHERE service_package_id = ";
    private final String ADD_SERVICE = "INSERT INTO service (id, name, price, description, service_package_id)" +
            "VALUES ( ?, ?, ?, ?, ?)";
    private final String DELETE_ONE_BY_ID = "DELETE FROM service WHERE id = ";
    private final String UPDATE_ONE_BY_ID = "UPDATE service " +
            "SET id = ?, name = ?, price = ?, description = ?, service_package_id = ? " +
            "WHERE id = ";

    @Override
    public Service getByServicePackageId(int servicePackageId) {
        return getOneFromDB(GET_ONE_BY_SERVICE_PACKAGE_ID + servicePackageId);
    }

    @Override
    public Service getByName(String name) {
        return getOneFromDB(GET_ONE_BY_NAME + "'" + name + "'") ;
    }

    @Override
    public void create(Service entity) {
        createUpdate(ADD_SERVICE, entity);

    }

    @Override
    public List<Service> getAll() {
        List<Service> services = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_SERVICES);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                String description = resultSet.getString("description");
                int servicePackageId = resultSet.getInt("service_package_id");

                services.add(new Service(id, name, price, description, servicePackageId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return services;
    }

    @Override
    public Service getById(int id) {
        return getOneFromDB(GET_ONE_BY_ID + id);
    }

    @Override
    public void updateOneById(int id, Service entity) {
        String sql = UPDATE_ONE_BY_ID + id;
        createUpdate(sql, entity);
    }

    @Override
    public void deleteOneById(int id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private void createUpdate(String sql, Service service) {
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, service.getId());
            preparedStatement.setString(2, service.getName());
            preparedStatement.setBigDecimal(3, service.getPrice());
            preparedStatement.setString(4, service.getDescription());
            preparedStatement.setInt(5, service.getServicePackageId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Service getOneFromDB(String sql) {

        Service service = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                String description = resultSet.getString("description");
                int servicePackageId = resultSet.getInt("service_package_id");

                service = new Service(id, name, price, description, servicePackageId);
            } else {
                service = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return service;
    }
}
