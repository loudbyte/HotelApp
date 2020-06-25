package com.epam.hotel.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;

public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private final String driverName;
    private final String url;
    private final String userName;
    private final String password;
    private int poolSize;

    private BlockingQueue<Connection> connectionsQueue;
    private static volatile ConnectionPool instance;

    private ConnectionPool() {

        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DBResourceManager.DATA_BASE_DRIVER);
        this.url = dbResourceManager.getValue(DBResourceManager.DATA_BASE_URL);
        this.userName = dbResourceManager.getValue(DBResourceManager.DATA_BASE_USERNAME);
        this.password = dbResourceManager.getValue(DBResourceManager.DATA_BASE_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBResourceManager.DATA_BASE_POLL_SIZE));
        } catch (NumberFormatException exception) {
            poolSize = 5;
        }

        try {
            Class.forName(driverName);
            connectionsQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connectionsQueue.add(DriverManager.getConnection(url, userName, password));
            }
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error(exception, exception);
        }
    }

    public static ConnectionPool getInstance() {

        ConnectionPool localInstance = instance;

        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() {

        Connection newConnection;
        try {
            newConnection = connectionsQueue.take();
        } catch (InterruptedException exception) {
            newConnection = connectionsQueue.poll();
        }
        return newConnection;
    }

    public void releaseConnection(Connection connection) {

        try {
            boolean isAutocommit = connection.getAutoCommit();
            if (!isAutocommit) {
                connection.setAutoCommit(Boolean.TRUE);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        }
        try {
            connectionsQueue.put(connection);
        } catch (InterruptedException exception) {
            connectionsQueue.offer(connection);
        }
    }
}