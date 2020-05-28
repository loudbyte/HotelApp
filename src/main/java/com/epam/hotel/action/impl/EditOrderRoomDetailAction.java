package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.entity.OrderRoomDetail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL;

public class EditOrderRoomDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderDetailId = Long.parseLong(request.getParameter("order_room_detail_id"));
        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));
        long roomId = Long.parseLong(request.getParameter("room_id"));
        long orderFacilityDetailId = Long.parseLong(request.getParameter("order_facility_detail_id"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        OrderRoomDetail orderRoomDetail = new OrderRoomDetail(orderDetailId, roomId, orderFacilityDetailId, orderMainId, startDate, endDate);
        orderRoomDetailDAO.updateOneById(orderDetailId, orderRoomDetail);

        request.setAttribute("order_main_id", orderMainId);

        request.getRequestDispatcher(SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}