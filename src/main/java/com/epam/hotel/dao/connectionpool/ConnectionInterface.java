package com.epam.hotel.dao.connectionpool;

import java.sql.Connection;

public interface ConnectionInterface {

        Connection getConnection();
        void releaseConnection(Connection connection);
}
