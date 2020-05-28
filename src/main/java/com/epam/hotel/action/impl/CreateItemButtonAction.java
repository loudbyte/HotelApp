package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.CREATE_ITEM_URL;

public class CreateItemButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RoomDAOImpl roomDAO = new RoomDAOImpl();

        long id = Long.parseLong(request.getParameter("room_id"));

        Room room = roomDAO.getOneById(id);

        //TODO if item have not created - change true(at the next jsp)

        request.setAttribute("room", room);
        request.getRequestDispatcher(CREATE_ITEM_URL).forward(request, response);
    }
}
