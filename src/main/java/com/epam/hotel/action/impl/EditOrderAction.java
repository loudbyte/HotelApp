package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();

        long orderId = Long.parseLong(request.getParameter("order_id"));
        long personId = Long.parseLong(request.getParameter("person_id"));
        long statusId = Long.parseLong(request.getParameter("status_id"));
        String date = request.getParameter("date");

        orderMainDAO.updateOneById(orderId, new OrderMain(orderId, personId, statusId, date));

        new ShowOrderAdminListButtonAction().execute(request, response);
    }
}
