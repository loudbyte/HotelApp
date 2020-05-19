package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.role.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderDetailId = Long.parseLong(request.getParameter("order_detail_id"));

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();
        orderRoomDetailDAO.deleteOneById(orderDetailId);

        if (request.getSession().getAttribute("role") == Role.ADMIN) {
            new ShowOrderAdminListButtonAction().execute(request, response);
            return;
        }
        new ShowMyOrdersAction().execute(request, response);
    }
}
