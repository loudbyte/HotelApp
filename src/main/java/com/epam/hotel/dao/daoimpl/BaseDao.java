package com.epam.hotel.dao.daoimpl;

import com.epam.hotel.dao.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseDao {

    protected ConnectionPool connectionPool;
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    protected BaseDao() {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }
}

