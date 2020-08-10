package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomClassDAO;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomClassDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class DeleteRoomClassAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NumericValidator.isNumeric(request.getParameter(ROOM_CLASS_ID))) {
            RoomDAO roomDAO = new RoomDAOImpl();
            long roomClassId = Long.parseLong(request.getParameter(ROOM_CLASS_ID));

            if (roomDAO.getAll().stream().noneMatch(room -> room.getRoomClassId() == roomClassId)) {
                RoomClassDAO roomClassDAO = new RoomClassDAOImpl();
                roomClassDAO.deleteOneById(roomClassId);
                response.sendRedirect(SHOW_ROOM_CLASS_ADMIN_LIST_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ROOM_WITH_THIS_CLASS);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}