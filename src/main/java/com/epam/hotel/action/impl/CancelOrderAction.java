package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.AuthorizationValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CancelOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (AuthorizationValidation.authorizationGetBoolean(request, response)) {
            if (NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {

                long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
                OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
                OrderMain orderMain = orderMainDAO.getOneById(orderMainId);
                orderMain.setOrderStatusId(CANCELLED);
                orderMainDAO.updateOneById(orderMainId, orderMain);

                response.sendRedirect(SHOW_MY_ORDERS_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        }
    }
}