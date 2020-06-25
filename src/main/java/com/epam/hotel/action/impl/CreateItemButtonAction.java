package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.CREATE_ITEM_JSP;
import static com.epam.hotel.util.constant.ActionConstant.ROOM_ID;

public class CreateItemButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(ROOM_ID, request.getParameter(ROOM_ID));
        response.sendRedirect(CREATE_ITEM_JSP);
    }
}