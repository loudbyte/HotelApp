package com.epam.hotel.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {

    protected static final String DATA_BASE_DRIVER = "db.driver";
    protected static final String DATA_BASE_URL = "db.url";
    protected static final String DATA_BASE_USERNAME = "db.username";
    protected static final String DATA_BASE_PASSWORD = "db.password";
    protected static final String DATA_BASE_POLL_SIZE = "db.poolsize";
    private static final String PERSISTENCE = "persistence";

    private final static DBResourceManager instance = new DBResourceManager();

    private static final ResourceBundle bundle = ResourceBundle.getBundle(PERSISTENCE);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
