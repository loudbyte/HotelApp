package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.CREATE_ITEM_URL;
import static com.epam.hotel.action.impl.ActionConstant.ROOM_ID;

public class CreateItemButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ROOM_ID, request.getParameter(ROOM_ID));
        request.getRequestDispatcher(CREATE_ITEM_URL).forward(request, response);
    }
}
