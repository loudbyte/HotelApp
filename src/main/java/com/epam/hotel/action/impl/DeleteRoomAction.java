package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class DeleteRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(ID))) {
            request.setAttribute(MESSAGE, "Комната не найдена");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long roomId = Long.parseLong(request.getParameter(ID));
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(roomId);
        if (room != null) {
            roomDAO.deleteOneById(roomId);
            request.getRequestDispatcher(SHOW_ROOM_ADMIN_LIST_URL).forward(request, response);
        } else {
            request.setAttribute(MESSAGE, "Комната не найдена");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}