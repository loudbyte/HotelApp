package com.epam.hotel.dao.impl;

import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.entity.Person;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl extends BaseDAOImpl implements PersonDAO {

    private final String GET_ALL_PERSONS = "SELECT * FROM person";
    private final String GET_ONE_BY_FIRST_NAME = "SELECT * FROM person WHERE first_name = ";
    private final String GET_ONE_BY_ID = "SELECT * FROM person WHERE id = ";
    private final String ADD_GUEST = "INSERT INTO person (first_name, last_name, birthday, phone, email, login, password, is_admin, is_ban) " +
            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_ONE_BY_ID = "DELETE FROM person WHERE id = ";
    private final String UPDATE_ONE_BY_ID = "UPDATE person " +
            "SET first_name = ?, last_name = ?, birthday = ?, phone = ?, email = ?, login = ?, password = ?, is_admin = ?, is_ban = ? " +
            "WHERE id = ";

    @Override
    public void create(Person person) {

        createUpdate(ADD_GUEST, person);

    }

    @Override
    public List<Person> getAll() {

        List<Person> people = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_PERSONS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String birthday = String.valueOf(resultSet.getDate("birthday"));
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                boolean isBan = resultSet.getBoolean("is_ban");

                people.add(new Person(id, firstName, lastName, birthday, phone, email, login, password, isAdmin, isBan));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return people;
    }

    @Override
    public Person getByFirstName(String name) {

        return getOneFromBD(GET_ONE_BY_FIRST_NAME + "'" + name + "'");
    }

    @Override
    public Person getByLastName(String guestLastName) {

        return getOneFromBD(GET_ONE_BY_FIRST_NAME + "'" + guestLastName + "'");
    }

    @Override
    public Person getById(int id) {

        return getOneFromBD(GET_ONE_BY_ID + id);
    }

    @Override
    public void updateOneById(int id, Person person) {
        String sql = UPDATE_ONE_BY_ID + id;
        createUpdate(sql, person);
    }

    @Override
    public void deleteOneById(int id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_ONE_BY_ID + id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private void createUpdate(String sql, Person person) {
        try {
            preparedStatement = connection.prepareStatement(sql);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date myDate = format.parse(person.getBirthday());
            Date sqlDate = new Date(myDate.getTime());

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, person.getPhone());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setString(6, person.getLogin());
            preparedStatement.setString(7, person.getPassword());
            preparedStatement.setBoolean(8, person.isAdmin());
            preparedStatement.setBoolean(9, person.isBan());

            preparedStatement.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Person getOneFromBD(String sql) {

        Person person = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String birthday = String.valueOf(resultSet.getDate("birthday"));
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                boolean isBan = resultSet.getBoolean("is_ban");

                person = new Person(id, firstName, lastName, birthday, phone, email, login, password, isAdmin, isBan);
            } else {
                person = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return person;
    }
}
