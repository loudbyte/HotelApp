package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.LanguageDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.dao.impl.DAOConstant.*;

public class LanguageDAOImpl implements LanguageDAO {

    private static final Logger LOGGER = Logger.getLogger(LanguageDAOImpl.class);

    private static final String GET_LANGUAGE_MAP = "SELECT * FROM language";
    private static final String ADD_LANGUAGE = "INSERT INTO language (locale) VALUES (?)";
    private static final String DELETE_LANGUAGE_BY_ID = "DELETE FROM language WHERE id = ?";
    private static final String UPDATE_LANGUAGE_BY_ID = "UPDATE language SET locale = ? WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_LANGUAGE_SEQ = "select last_value FROM language_id_seq";


    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    public long addLanguage(String locale) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LANGUAGE);
            PreparedStatement preparedStatement1GetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_LANGUAGE_SEQ)) {

            preparedStatement.setString(1, locale);
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatement1GetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getInt(1);

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    public Map<Integer, String> getLanguageMap() {
        connection = connectionPool.getConnection();

        Map<Integer, String> languageMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_MAP)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int key = resultSet.getInt(ID);
                String value = resultSet.getString(LOCALE);
                languageMap.put(key, value);
            }

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return languageMap;
    }

    public void updateLanguageById(long id, String locale) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LANGUAGE_BY_ID)) {

            preparedStatement.setString(1, locale);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void deleteLanguageById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LANGUAGE_BY_ID)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}