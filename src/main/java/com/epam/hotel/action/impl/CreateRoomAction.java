package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreateRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
        int capacity = Integer.parseInt(request.getParameter(CAPACITY));
        long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
        String availability = request.getParameter(AVAILABILITY);
        boolean isAvailable = false;

        if (roomNumber == ZERO || capacity == ZERO || roomClassId == ZERO || price.equals(null)) {
            request.setAttribute(MESSAGE, "Пустые поля.");
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

        RoomDAOImpl roomDAO = new RoomDAOImpl();
        roomDAO.create(room);

        request.getRequestDispatcher(SHOW_ROOM_ADMIN_LIST_URL).forward(request, response);
    }
}