package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.RoomDAO;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ROOM_NOT_FOUND;

public class DeleteRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ID))) {

            long roomId = Long.parseLong(request.getParameter(ID));
            RoomDAO roomDAO = new RoomDAOImpl();
            Room room = roomDAO.getOneById(roomId);
            if (room != null) {
                roomDAO.deleteOneById(roomId);
                response.sendRedirect(SHOW_ROOM_ADMIN_LIST_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ROOM_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}