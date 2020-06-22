package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_INVALID_CLASS;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ROOM_NOT_FOUND;

public class EditRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getSession().getAttribute(ID)))) {
            request.setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        if (!CreateRoomAction.roomFieldValidation(request, response))
            return;

        long id = Long.parseLong(String.valueOf(request.getSession().getAttribute(ID)));
        int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
        int capacity = Integer.parseInt(request.getParameter(CAPACITY));
        int roomClass = Integer.parseInt(request.getParameter(ROOM_CLASS));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
        String availability = request.getParameter(AVAILABILITY);
        boolean isAvailable = false;

        if (1 > roomClass || roomClass > 3) {
            request.setAttribute(MESSAGE, ERROR_INVALID_CLASS);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (availability != null) {
            isAvailable = true;
        }

        RoomDAO roomDAO = new RoomDAOImpl();

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomClass(roomClass);
        room.setPrice(price);
        room.setAvailability(isAvailable);

        roomDAO.updateOneById(id, room);

        response.sendRedirect(SHOW_ROOM_ADMIN_LIST_URL);
    }
}