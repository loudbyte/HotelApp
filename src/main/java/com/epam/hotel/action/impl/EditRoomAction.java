package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class EditRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getSession().getAttribute(ID)))) {
            request.setAttribute(MESSAGE, "Комната не найдена.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(ROOM_NUMBER))
                || !NumericValidation.isNumeric(request.getParameter(CAPACITY))
                || !NumericValidation.isNumeric(request.getParameter(ROOM_CLASS_ID))
                || !NumericValidation.isNumeric(request.getParameter(PRICE))) {
            request.setAttribute(MESSAGE, "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long id = Long.parseLong(String.valueOf(request.getSession().getAttribute(ID)));
        int roomNumber = Integer.parseInt(request.getParameter(ROOM_NUMBER));
        int capacity = Integer.parseInt(request.getParameter(CAPACITY));
        long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));
        String availability = request.getParameter(AVAILABILITY);
        boolean isAvailable = false;

        if(1 > roomClassId || roomClassId > 3) {
            request.setAttribute(MESSAGE, "Недопустимый класс.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (availability != null) {
            isAvailable = true;
        }

        RoomDAOImpl roomDAO = new RoomDAOImpl();

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomClassId(roomClassId);
        room.setPrice(price);
        room.setAvailability(isAvailable);

        roomDAO.updateOneById(id, room);

        request.getRequestDispatcher(SHOW_ROOM_ADMIN_LIST_URL).forward(request, response);
    }
}