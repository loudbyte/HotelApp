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

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ROOM_NOT_FOUND;

public class SetRoomAvailabilityAction implements Action {

    private final boolean isAvailable;

    public SetRoomAvailabilityAction(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getParameter(ROOM_ID)))) {
            request.setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long roomId = Long.parseLong(String.valueOf(request.getParameter(ROOM_ID)));

        RoomDAO roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(roomId);
        room.setAvailability(isAvailable);
        roomDAO.updateOneById(roomId, room);

        response.sendRedirect(SHOW_ROOM_ADMIN_LIST_URL);
    }
}
