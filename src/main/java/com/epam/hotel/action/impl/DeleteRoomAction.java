package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ActionConstant.SHOW_ROOM_ADMIN_LIST_URL;

public class DeleteRoomAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("id"));
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        Room room = roomDAO.getOneById(id);
        if (room != null) {
            roomDAO.deleteOneById(id);
            request.getRequestDispatcher(SHOW_ROOM_ADMIN_LIST_URL).forward(request, response);
        } else {
            request.setAttribute("message", "Комнаты не существует.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}
