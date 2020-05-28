package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderRoomDetailButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("order_room_detail_id", request.getParameter("order_room_detail_id"));
        request.setAttribute("order_main_id", request.getParameter("order_main_id"));
        request.setAttribute("room_id", request.getParameter("room_id"));
        request.setAttribute("order_facility_detail_id", request.getParameter("order_facility_detail_id"));
        request.setAttribute("start_date", request.getParameter("start_date"));
        request.setAttribute("end_date", request.getParameter("end_date"));

        request.getRequestDispatcher(ActionConstant.EDIT_ORDER_ROOM_DETAIL_URL).forward(request, response);

    }
}