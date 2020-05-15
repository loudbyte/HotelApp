package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderId = Long.parseLong(request.getParameter("order_id"));

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        orderMainDAO.deleteOneById(orderId);

        new ShowOrderAdminListButtonAction().execute(request, response);
    }
}
