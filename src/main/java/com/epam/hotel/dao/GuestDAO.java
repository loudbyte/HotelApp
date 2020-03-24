package com.epam.hotel.dao;

import com.epam.hotel.dao.interfaces.GuestDAOInterface;
import com.epam.hotel.entity.Guest;
import com.epam.hotel.entity.Room;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO implements GuestDAOInterface {

    private String[] properties = DBProperty.setProperties();
    private String url = properties[0];
    private String username = properties[1];
    private String password = properties[2];

    public List<Guest> getGuests() {

        List<Guest> guest = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM guests")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String birthday = String.valueOf(rs.getDate("birthday"));
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                guest.add(new Guest(id, firstName, lastName, birthday, phone, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return guest;
    }

    public void setGuest(Guest guest) {


        try (Connection con = DriverManager.getConnection(url, username, password);) {

            try {
                PreparedStatement preparedStatement =
                        con.prepareStatement(
                                "INSERT INTO guests (\"firstName\", \"lastName\", \"birthday\", \"phone\", \"email\") " +
                                        "VALUES ( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date myDate = format.parse(guest.getBirthday());
                Date sqlDate = new Date(myDate.getTime());

                preparedStatement.setString(1, guest.getFirstName());
                preparedStatement.setString(2, guest.getLastName());
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, guest.getPhone());
                preparedStatement.setString(5, guest.getEmail());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Guest guest = new Guest(1, "CHECK2", "Iridov", "1994-02-15", "+77056648787", "artem@mail.ru");
//        GuestDAO guestDAO = new GuestDAO();
//        guestDAO.setGuest(guest);
//        System.out.println(guest.getId());

        GuestDAO dao = new GuestDAO();

        for (Guest g : dao.getGuests()) {
            System.out.println(g);
        }



    }
}
