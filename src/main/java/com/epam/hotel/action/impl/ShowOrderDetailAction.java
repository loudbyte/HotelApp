package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.dao.impl.RoomDAOImpl;
import com.epam.hotel.entity.OrderFacilityDetail;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.ERROR_URL;
import static com.epam.hotel.action.impl.Constant.SHOW_ORDER_DETAIL_URL;

public class ShowOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = null;

        if (request.getSession().getAttribute("person") instanceof Person) {
            person = (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderId = Long.parseLong(request.getParameter("order_id"));


        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        OrderFacilityDetailDAOImpl orderFacilityDetailDAO = new OrderFacilityDetailDAOImpl();
        List orderDetailList = orderRoomDetailDAO.getAllByOrderId(orderId);
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        List roomList = roomDAO.getAll();

        request.setAttribute("room_list", roomList);
        request.setAttribute("room_dao", roomDAO);
        request.setAttribute("order_detail_list", orderDetailList);
        request.setAttribute("order_facility_detail_dao", orderFacilityDetailDAO);
        request.setAttribute("order_status_id", new OrderMainDAOImpl().getOneById(orderId).getStatusId());
        request.getRequestDispatcher(SHOW_ORDER_DETAIL_URL).forward(request, response);
    }
}
