package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class SetRoomAvailabilityAction implements Action {

    private final boolean isAvailable;

    public SetRoomAvailabilityAction(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidator.isNumeric(String.valueOf(request.getParameter(ROOM_ID)))) {

            long roomId = Long.parseLong(String.valueOf(request.getParameter(ROOM_ID)));

            RoomDAO roomDAO = new RoomDAOImpl();
            Room room = roomDAO.getOneById(roomId);
            room.setAvailability(isAvailable);
            roomDAO.updateOneById(roomId, room);

            response.sendRedirect(SHOW_ROOM_ADMIN_LIST_JSP);

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}
