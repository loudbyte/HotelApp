package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.SHOW_ORDER_ADMIN_LIST_URL;

public class EditOrderAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();

        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));
        long personId = Long.parseLong(request.getParameter("person_id"));
        long statusId = Long.parseLong(request.getParameter("status_id"));
        String date = request.getParameter("date");

        orderMainDAO.updateOneById(orderMainId, new OrderMain(orderMainId, personId, statusId, date));

        request.getRequestDispatcher(SHOW_ORDER_ADMIN_LIST_URL).forward(request, response);
    }
}
