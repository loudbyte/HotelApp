package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ORDER_MAIN_ID;
import static com.epam.hotel.action.impl.ActionConstant.SHOW_ORDER_ADMIN_LIST_URL;

public class DeleteOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

        OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
        orderMainDAO.deleteOneById(orderMainId);

        response.sendRedirect(SHOW_ORDER_ADMIN_LIST_URL);
    }
}
