package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.OrderRoomDetail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.SHOW_ORDER_DETAIL_URL;

public class EditOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderDetailId = Long.parseLong(request.getParameter("order_detail_id"));
        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));
        long roomId = Long.parseLong(request.getParameter("room_id"));
        long orderFacilityDetailId = Long.parseLong(request.getParameter("order_facility_detail_id"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        OrderRoomDetail orderRoomDetail = new OrderRoomDetail(orderDetailId, roomId, orderFacilityDetailId, orderMainId, startDate, endDate);
        orderRoomDetailDAO.updateOneById(orderDetailId, orderRoomDetail);

        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        List orderDetailList = orderRoomDetailDAO.getAllByOrderId(orderMainId);
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        List roomList = roomDAO.getAll();

        request.setAttribute("room_list", roomList);
        request.setAttribute("room_dao", roomDAO);
        request.setAttribute("order_detail_list", orderDetailList);
        request.setAttribute("order_facility_detail_dao", orderFacilityDetailDAO);
        request.setAttribute("order_status_id", new OrderMainDAOImpl().getOneById(orderMainId).getStatusId());
        request.getRequestDispatcher(SHOW_ORDER_DETAIL_URL).forward(request, response);
//        new ShowOrderDetailAction().execute(request, response);
    }
}
