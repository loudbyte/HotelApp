package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.entity.Person;
import com.epam.hotel.password.HashPassword;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.epam.hotel.dao.impl.Constant.*;

public class PersonDAOImpl implements PersonDAO {

    private static final Logger LOGGER = Logger.getLogger(PersonDAOImpl.class);
    private static final String GET_ALL_PERSONS = "SELECT * FROM person";
    private static final String GET_ONE_BY_FIRST_NAME = "SELECT * FROM person WHERE first_name = ?";
    private static final String GET_ONE_BY_LAST_NAME = "SELECT * FROM person WHERE last_name = ?";
    private static final String GET_ONE_BY_ID = "SELECT * FROM person WHERE id = ?";
    private static final String GET_ONE_BY_EMAIL = "SELECT * FROM person WHERE email = ?";
    private static final String GET_ONE_BY_PASSWORD = "SELECT * FROM person WHERE password = ?";
    private static final String GET_ONE_BY_EMAIL_AND_PASSWORD = "SELECT * FROM person WHERE email = ? AND password = ? ";
    private static final String CREATE_PERSON = "INSERT INTO person (first_name, last_name, birthday, phone, email, iin, password, is_admin, is_ban) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ONE_BY_ID = "DELETE FROM person WHERE id = ?";
    private static final String UPDATE_ONE_BY_ID = "UPDATE person " +
            "SET first_name = ?, last_name = ?, birthday = ?, phone = ?, email = ?, iin = ?, password = ?, is_admin = ?, is_ban = ? " +
            "WHERE id = ?";

    private static final String GET_LAST_VALUE_FROM_PERSON_SEQ = "select last_value FROM person_id_seq";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    private HashPassword hashPassword = new HashPassword();

    @Override
    public long create(Person person) {
        connection = connectionPool.getConnection();

        long id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PERSON);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_PERSON_SEQ);
        ) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            java.util.Date myDate = format.parse(person.getBirthday());
            Date sqlDate = new Date(myDate.getTime());

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, person.getPhone());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setString(6, person.getIin());
            preparedStatement.setString(7, hashPassword.getHashPassword(person.getPassword()));
            preparedStatement.setBoolean(8, person.isAdmin());
            preparedStatement.setBoolean(9, person.isBan());
            preparedStatement.executeUpdate();
            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1);

        } catch (SQLException | ParseException e) {
            LOGGER.error("SQLException or ParseException in PersonDAOimpl create", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    public void updateOneById(long id, Person person) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID);) {

            preparedStatement.setLong(10, id);

            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            java.util.Date utilDate = format.parse(person.getBirthday());
            Date sqlDate = new Date(utilDate.getTime());

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, person.getPhone());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setString(6, person.getIin());
            preparedStatement.setString(7, hashPassword.getHashPassword(person.getPassword()));
            preparedStatement.setBoolean(8, person.isAdmin());
            preparedStatement.setBoolean(9, person.isBan());

            preparedStatement.executeUpdate();

        } catch (SQLException | ParseException e) {
            LOGGER.error("SQLException or ParseException in PersonDAOimpl updateOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Person> getAll() {
        connection = connectionPool.getConnection();

        List<Person> personList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PERSONS);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setFirstName(resultSet.getString(FIRST_NAME));
                person.setLastName(resultSet.getString(LAST_NAME));
                person.setBirthday(String.valueOf(resultSet.getDate(BIRTHDAY)));
                person.setPhone(resultSet.getString(PHONE));
                person.setEmail(resultSet.getString(EMAIL));
                person.setIin(resultSet.getString(IIN));
                person.setPassword(resultSet.getString(PASSWORD));
                person.setAdmin(resultSet.getBoolean(IS_ADMIN));
                person.setBan(resultSet.getBoolean(IS_BAN));
                personList.add(person);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getAll", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return personList;
    }

    @Override
    public Person getOneByEmail(String email) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            return getPerson(person, preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getOneByEmail", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public Person getOneByPassword(String password) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_PASSWORD)) {
            preparedStatement.setString(1, password);
            return getPerson(person, preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getOneByPassword", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    public Person getOneByPasswordAndEmail(String email, String password) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            return getPerson(person, preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getOneByPasswordAndEmail", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public Person getByFirstName(String firstName) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_FIRST_NAME)) {
            preparedStatement.setString(1, firstName);
            return getPerson(person, preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getByFirstName", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public Person getByLastName(String lastName) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_LAST_NAME)) {
            preparedStatement.setString(1, lastName);
            return getPerson(person, preparedStatement.executeQuery());
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getByLastName", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public Person getOneById(long id) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            person = getPerson(person, resultSet);

        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl getById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    private Person getPerson(Person person, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            person = new Person();
            person.setId(resultSet.getInt(ID));
            person.setFirstName(resultSet.getString(FIRST_NAME));
            person.setLastName(resultSet.getString(LAST_NAME));
            person.setBirthday(String.valueOf(resultSet.getDate(BIRTHDAY)));
            person.setPhone(resultSet.getString(PHONE));
            person.setEmail(resultSet.getString(EMAIL));
            person.setIin(resultSet.getString(IIN));
            person.setPassword(resultSet.getString(PASSWORD));
            person.setAdmin(resultSet.getBoolean(IS_ADMIN));
            person.setBan(resultSet.getBoolean(IS_BAN));
        }
        return person;
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException in PersonDAOimpl deleteOneById", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public boolean checkByPasswordAndEmail(String password, String email) {
        Person person = getOneByPasswordAndEmail(email, password);
        boolean result = false;
        if (person != null) {
            String passwordFromPerson = person.getPassword();
            String emailFromPerson = person.getEmail();
            if (passwordFromPerson.equals(password) && emailFromPerson.equals(email)) {
                result = true;
            }
        }
        return result;
    }
}