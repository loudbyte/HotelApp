package com.epam.hotel.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {

    public static final String DATA_BASE_DRIVER = "db.driver";
    public static final String DATA_BASE_URL = "db.url";
    public static final String DATA_BASE_USERNAME = "db.username";
    public static final String DATA_BASE_PASSWORD = "db.password";
    public static final String DATA_BASE_POLL_SIZE = "db.poolsize";

    private final static DBResourceManager instance = new DBResourceManager();

    private static ResourceBundle bundle = ResourceBundle.getBundle("persistence");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
