package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.Constant.ERROR_URL;

public class CreateRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAOImpl roomDAO = new RoomDAOImpl();

        int roomNumber = Integer.parseInt(request.getParameter("room_number"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        long roomClassId = Long.parseLong(request.getParameter("room_class_id"));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter("price")));
        String availability = request.getParameter("availability");
        String image = request.getParameter("images");
        boolean isAvailable = false;

        if (roomNumber == 0 || capacity == 0 || roomClassId == 0 || price.equals(null) ) {
            request.setAttribute("message", "Пустые поля.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (availability != null) {
            isAvailable = true;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomClassId(roomClassId);
        room.setPrice(price);
        room.setAvailability(isAvailable);

        roomDAO.create(room);

        new ShowRoomAdminListAction().execute(request, response);
    }
}
