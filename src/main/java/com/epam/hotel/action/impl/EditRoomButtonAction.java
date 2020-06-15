package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.EDIT_ROOM_URL;
import static com.epam.hotel.action.impl.ActionConstant.ID;

public class EditRoomButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(ID, request.getParameter(ID));
        request.getRequestDispatcher(EDIT_ROOM_URL).forward(request, response);
    }
}
