package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class EditOrderRoomDetailButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(ORDER_ROOM_DETAIL_ID, request.getParameter(ORDER_ROOM_DETAIL_ID));
        request.setAttribute(ORDER_MAIN_ID, request.getParameter(ORDER_MAIN_ID));
        request.setAttribute(ROOM_ID, request.getParameter(ROOM_ID));
        request.setAttribute(FACILITY_PACKAGE_ID, request.getParameter(FACILITY_PACKAGE_ID));
        request.setAttribute(START_DATE, request.getParameter(START_DATE));
        request.setAttribute(END_DATE, request.getParameter(END_DATE));

        request.getRequestDispatcher(ActionConstant.EDIT_ORDER_ROOM_DETAIL_URL).forward(request, response);

    }
}