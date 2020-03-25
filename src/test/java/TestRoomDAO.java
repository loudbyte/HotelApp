import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.connection.ConnectionPoolException;
import com.epam.hotel.entity.Room;

import java.math.BigDecimal;

public class TestRoomDAO {

    public static void main(String[] args) throws ConnectionPoolException {
        BigDecimal cost = new BigDecimal(666);
        Room room = new Room(6, 1, 888, 8, cost, true);
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.setRoom(room);

        RoomDAO dao = new RoomDAO();

        for (Room r : dao.getAllRooms()) {
            System.out.println(r);
        }
    }
}
