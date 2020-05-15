package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.SHOW_ADMIN_ROOM_LIST_URL;

public class ShowRoomAdminListAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        List<Room> roomList = roomDAO.getAll();
        request.setAttribute("room_list", roomList);
        request.getRequestDispatcher(SHOW_ADMIN_ROOM_LIST_URL).forward(request, response);
    }
}
