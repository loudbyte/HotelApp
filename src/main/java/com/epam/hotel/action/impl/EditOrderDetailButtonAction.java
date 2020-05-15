package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderDetailButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderDetailId = Long.parseLong(request.getParameter("order_detail_id"));
        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));
        long roomId = Long.parseLong(request.getParameter("room_id"));
        long orderFacilityDetailId = Long.parseLong(request.getParameter("order_facility_detail_id"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        request.setAttribute("order_detail_id", orderDetailId);
        request.setAttribute("order_main_id", orderMainId);
        request.setAttribute("room_id", roomId);
        request.setAttribute("order_facility_detail_id", orderFacilityDetailId);
        request.setAttribute("start_date", startDate);
        request.setAttribute("end_date", endDate);
        request.getRequestDispatcher(Constant.EDIT_ORDER_DETAIL_URL).forward(request, response);

    }
}
