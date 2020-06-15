package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.util.RoomAvailability;
import com.epam.hotel.validation.AuthorizationValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CancelOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!AuthorizationValidation.authorizationGetBoolean(request, response))
            return;

        if (!NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
            request.setAttribute("message", "Заказ не найден.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        OrderMain orderMain = orderMainDAO.getOneById(orderMainId);
        orderMain.setStatusId(CANCELLED);
        orderMainDAO.updateOneById(orderMainId, orderMain);

        RoomAvailability.setAvailabilityForRoomsInOrder(orderMainId, Boolean.TRUE);

        request.getRequestDispatcher(SHOW_MY_ORDERS_URL).forward(request, response);
    }
}