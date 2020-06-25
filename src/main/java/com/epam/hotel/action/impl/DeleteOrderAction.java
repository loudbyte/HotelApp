package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class DeleteOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {

            long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

            OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
            orderMainDAO.deleteOneById(orderMainId);

            response.sendRedirect(SHOW_ORDER_ADMIN_LIST_JSP);

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}
