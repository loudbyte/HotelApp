package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.payment.PayOrder;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.CardValidator;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class PaymentAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cardNumber = request.getParameter(CARD_NUMBER);

        if (CardValidator.isValid(cardNumber)) {
            if (NumericValidator.isNumeric(request.getParameter(ORDER_MAIN_ID))) {

                long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

                OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
                OrderMain orderMain = orderMainDAO.getOneById(orderMainId);

                PayOrder payOrder = new PayOrder();

                if (payOrder.pay(orderMain)) {
                    response.sendRedirect(PAYMENT_JSP);
                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_PAYMENT_FAILED);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_WRONG_CARD_NUMBER);
            response.sendRedirect(ERROR_JSP);
        }
    }
}