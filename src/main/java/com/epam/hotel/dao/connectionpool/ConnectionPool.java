package com.epam.hotel.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool implements ConnectionInterface{

    private String driverName;
    private String url;
    private String userName;
    private String password;
    private int poolSize;

    private BlockingQueue<Connection> connectionsQueue;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.driverName = dbResourceManager.getValue(DataBaseParameter.DATA_BASE_DRIVER);
        this.url = dbResourceManager.getValue(DataBaseParameter.DATA_BASE_URL);
        this.userName = dbResourceManager.getValue(DataBaseParameter.DATA_BASE_USERNAME);
        this.password = dbResourceManager.getValue(DataBaseParameter.DATA_BASE_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DataBaseParameter.DATA_BASE_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }

        try {
            Class.forName(driverName);
            connectionsQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connectionsQueue.add(DriverManager.getConnection(url, userName, password));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static volatile ConnectionPool instance;

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
        } catch (InterruptedException e) {
            newConnection = connectionsQueue.poll();
        }
        return newConnection;
    }

    public void releaseConnection(Connection connection) {

        try {
            connectionsQueue.put(connection);
        } catch (InterruptedException e) {
            connectionsQueue.offer(connection);
        }
    }
}





