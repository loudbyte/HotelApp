package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.DAOConstant;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE;

public class CreateRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!roomFieldValidation(request, response))
            return;

        int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
        int capacity = Integer.parseInt(request.getParameter(CAPACITY));
        int roomClass = Integer.parseInt(request.getParameter(ROOM_CLASS));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
        String availability = request.getParameter(AVAILABILITY);
        boolean isAvailable = false;

        if (roomNumber == ZERO || capacity == ZERO || roomClass == ZERO || price.equals(null)) {
            request.setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (availability != null) {
            isAvailable = true;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomClass(roomClass);
        room.setPrice(price);
        room.setAvailability(isAvailable);

        RoomDAO roomDAO = new RoomDAOImpl();
        if (roomDAO.create(room) == DAOConstant.ERROR_ID) {
            request.setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ORDER_WITH_THIS_PACKAGE);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        response.sendRedirect(SHOW_ROOM_ADMIN_LIST_URL);
    }

    protected static boolean roomFieldValidation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!NumericValidation.isNumeric(request.getParameter(ROOM_NUMBER))
                || !NumericValidation.isNumeric(request.getParameter(CAPACITY))
                || !NumericValidation.isNumeric(request.getParameter(ROOM_CLASS))
                || !NumericValidation.isNumeric(request.getParameter(PRICE))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return false;
        }
        return true;
    }
}