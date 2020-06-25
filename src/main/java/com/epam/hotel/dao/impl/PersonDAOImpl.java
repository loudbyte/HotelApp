package com.epam.hotel.dao.impl;

import com.epam.hotel.connectionpool.ConnectionPool;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.entity.Person;
import com.epam.hotel.password.EncodePassword;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.epam.hotel.util.constant.DAOConstant.*;

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

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    private final EncodePassword encodePassword = new EncodePassword();

    @Override
    public long create(Person person) {
        connection = connectionPool.getConnection();

        long id = ERROR_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PERSON);
             PreparedStatement preparedStatementGetSeq = connection.prepareStatement(GET_LAST_VALUE_FROM_PERSON_SEQ)) {

            createUpdatePerson(person, preparedStatement);

            ResultSet resultSetGetSeq = preparedStatementGetSeq.executeQuery();

            if (resultSetGetSeq.next())
                id = resultSetGetSeq.getLong(1);

        } catch (SQLException | ParseException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    private void createUpdatePerson(Person person, PreparedStatement preparedStatement) throws ParseException, SQLException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        java.util.Date myDate = format.parse(person.getBirthday());
        Date sqlDate = new Date(myDate.getTime());

        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setDate(3, sqlDate);
        preparedStatement.setString(4, person.getPhone());
        preparedStatement.setString(5, person.getEmail());
        preparedStatement.setString(6, person.getIin());
        preparedStatement.setString(7, encodePassword.getHashPassword(person.getPassword()));
        preparedStatement.setBoolean(8, person.isAdmin());
        preparedStatement.setBoolean(9, person.isBan());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateOneById(long id, Person person) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_BY_ID)) {

            preparedStatement.setLong(10, id);
            createUpdatePerson(person, preparedStatement);

        } catch (SQLException | ParseException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Person> getAll() {
        connection = connectionPool.getConnection();

        List<Person> personList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PERSONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = getPerson(resultSet);
                personList.add(person);
            }
            personList.sort(Comparator.comparing(Person::getId));
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return personList;
    }

    @Override
    public Person getOneByPasswordAndEmail(String email, String password) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = getPerson(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public Person getOneByEmail(String email) {
        return getPersonByStringParameter(email, GET_ONE_BY_EMAIL);
    }

    @Override
    public Person getOneByPassword(String password) {
        return getPersonByStringParameter(password, GET_ONE_BY_PASSWORD);
    }

    @Override
    public Person getByFirstName(String firstName) {
        return getPersonByStringParameter(firstName, GET_ONE_BY_FIRST_NAME);
    }

    @Override
    public Person getByLastName(String lastName) {
        return getPersonByStringParameter(lastName, GET_ONE_BY_LAST_NAME);
    }

    @Override
    public Person getOneById(long id) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = getPerson(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }

    @Override
    public void deleteOneById(long id) {
        connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Person getPerson(ResultSet resultSet) throws SQLException {
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
        return person;
    }

    private Person getPersonByStringParameter(String stringParameter, String sql) {
        connection = connectionPool.getConnection();

        Person person = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, stringParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = getPerson(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error(exception, exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return person;
    }
}