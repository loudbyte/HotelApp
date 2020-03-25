import com.epam.hotel.dao.GuestDAO;
import com.epam.hotel.dao.connection.ConnectionPoolException;
import com.epam.hotel.entity.Guest;

public class TestGuestDAO {
    public static void main(String[] args) throws ConnectionPoolException {
        Guest guest = new Guest(1, "CHECK2", "Iridov", "1994-02-15", "+77056648787", "artem@mail.ru");
        GuestDAO guestDAO = new GuestDAO();
        guestDAO.setGuest(guest);

        GuestDAO dao = new GuestDAO();

        for (Guest g : dao.getAllGuests()) {
            System.out.println(g);
        }
    }
}
