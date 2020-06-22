package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.AuthorizationValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_INVALID_DATA;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ORDER_NOT_FOUND;

public class CancelOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!AuthorizationValidation.authorizationGetBoolean(request, response))
            return;

        if (!NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
        OrderMain orderMain = orderMainDAO.getOneById(orderMainId);
        orderMain.setStatus(CANCELLED);
        orderMainDAO.updateOneById(orderMainId, orderMain);

        response.sendRedirect(SHOW_MY_ORDERS_URL);
    }
}