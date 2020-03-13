package entity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    List<Room> rooms = new ArrayList<Room>();
    List<Guest> guests = new ArrayList<Guest>();

    public static void main(String[] args) {
        Hotel m = new Hotel();
        m.testDatabase();
    }

    private void testDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/hotel";
            String login = "postgres";
            String password = "q1w2";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int roomNumber = rs.getInt("roomNumber");
                    int capacity = rs.getInt("capacity");
                    int grade = rs.getInt("grade");
                    BigDecimal cost = rs.getBigDecimal("cost");
                    boolean availability = rs.getBoolean("availability");
                    rooms.add(new Room(id, roomNumber, capacity, grade, cost, availability));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rooms.get(0).getId() + " " + rooms.get(0).getRoomNumber() + " " + rooms.get(0).getCost() + " ");
        System.out.println(rooms.get(1).getId() + " " + rooms.get(1).getRoomNumber() + " " + rooms.get(1).getCost() + " ");
    }

    //guests database
    //rooms database

}
