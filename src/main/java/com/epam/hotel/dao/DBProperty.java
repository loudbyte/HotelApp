package com.epam.hotel.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperty {
    private static String url = null;
    private static String username = null;
    private static String password = null;
    private static String driver = null;

    public static String[] setProperties() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/persistence.properties")) {
            property.load(fis);
            url = property.getProperty("db.url");
            username = property.getProperty("db.username");
            password = property.getProperty("db.password");
            driver = property.getProperty("db.driver");
            Class.forName(driver);
        } catch (IOException e) {
            System.err.println("ERROR: Файл свойств отсуствует.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new String [] {url, username, password, driver};
    }
}
