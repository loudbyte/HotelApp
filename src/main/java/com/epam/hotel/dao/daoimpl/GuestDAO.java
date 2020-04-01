package com.epam.hotel.dao.daoimpl;

import com.epam.hotel.dao.daocommon.GuestDAOInterface;
import com.epam.hotel.entity.Guest;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO extends BaseDao implements GuestDAOInterface {

    private final String GET_ALL_GUESTS = "SELECT * FROM guests";
    private final String ADD_GUEST = "INSERT INTO guests (first_name, last_name, birthday, phone, email) " +
            "VALUES ( ?, ?, ?, ?, ?)";

    public List<Guest> getAll() {

        List<Guest> guests = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(GET_ALL_GUESTS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String birthday = String.valueOf(resultSet.getDate("birthday"));
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");

                guests.add(new Guest(id, firstName, lastName, birthday, phone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return guests;
    }

    public void setGuest(Guest guest) {

        try {
            preparedStatement = connection.prepareStatement(ADD_GUEST);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date myDate = format.parse(guest.getBirthday());
            Date sqlDate = new Date(myDate.getTime());

            preparedStatement.setString(1, guest.getFirstName());
            preparedStatement.setString(2, guest.getLastName());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, guest.getPhone());
            preparedStatement.setString(5, guest.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
