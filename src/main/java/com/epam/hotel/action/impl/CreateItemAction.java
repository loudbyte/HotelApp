package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ActionConstant.SHOW_CART_URL;

public class CreateItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = null;

        if (request.getSession().getAttribute("person") instanceof Person) {
            person = (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long roomId = Long.parseLong(request.getParameter("room_id"));
        long detailId = Long.parseLong(request.getParameter("detail_id"));

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        OrderRoomDetail orderRoomDetail = new OrderRoomDetail();

        orderRoomDetail.setRoomId(roomId);
        orderRoomDetail.setOrderFacilityDetailId(detailId);
        orderRoomDetail.setStartDate(request.getParameter("start_date"));
        orderRoomDetail.setEndDate(request.getParameter("end_date"));

        orderRoomDetail.setId(cart.getOrderRoomDetailCounter());

        cart.addElementByKeyToOrderRoomDetailMap(orderRoomDetail);

        request.getRequestDispatcher(SHOW_CART_URL).forward(request, response);
    }
}