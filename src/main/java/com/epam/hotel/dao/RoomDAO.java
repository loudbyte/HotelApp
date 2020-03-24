package com.epam.hotel.dao;

import com.epam.hotel.dao.interfaces.RoomDAOInterface;
import com.epam.hotel.entity.Room;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements RoomDAOInterface {

    private String[] properties = DBProperty.setProperties();
    private String url = properties[0];
    private String username = properties[1];
    private String password = properties[2];

    public List<Room> getRooms() {

        List<Room> room = new ArrayList<>();


        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int roomNumber = rs.getInt("roomNumber");
                int capacity = rs.getInt("capacity");
                int grade = rs.getInt("grade");
                BigDecimal cost = rs.getBigDecimal("cost");
                boolean availability = rs.getBoolean("availability");
                room.add(new Room(id, roomNumber, capacity, grade, cost, availability));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }

    public void setRoom(Room room) {

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement =
                     con.prepareStatement(
                             "INSERT INTO rooms (\"roomNumber\", \"capacity\", \"grade\", \"cost\", \"availability\") " +
                                     "VALUES ( ?, ?, ?, ?, ?)");) {

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setInt(3, room.getGrade());
            preparedStatement.setBigDecimal(4, room.getCost());
            preparedStatement.setBoolean(5, room.isAvailability());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        BigDecimal cost = new BigDecimal(666);
//        Room room = new Room(6, 1, 888, 8, cost, true);
//        RoomDAO roomDAO = new RoomDAO();
//        roomDAO.setRoom(room);

        RoomDAO dao = new RoomDAO();

        for (Room r : dao.getRooms()) {
            System.out.println(r);
        }

    }

}

