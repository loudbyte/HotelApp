package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.businesslogic.PayOrder;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.CardValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class PaymentAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cardNumber = request.getParameter(CARD_NUMBER);

        if (!CardValidation.isValid(cardNumber)) {
            request.setAttribute(MESSAGE, ERROR_WRONG_CARD_NUMBER);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (request.getParameter(ORDER_MAIN_ID) == null
                || !NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

        OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
        OrderMain orderMain = orderMainDAO.getOneById(orderMainId);

        PayOrder payOrder = new PayOrder();

        if (!payOrder.pay(orderMain)) {
            request.setAttribute(MESSAGE, ERROR_PAYMENT_FAILED);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        request.getRequestDispatcher(PAYMENT_URL).forward(request, response);
    }
}