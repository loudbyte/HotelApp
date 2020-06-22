package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class EditOrderAction implements Action {
    private static final long MAX_ORDER_STATUS = 6L;
    private static final long MIN_ORDER_STATUS = 1L;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(String.valueOf(request.getParameter(ORDER_MAIN_ID)))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                || !NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                || !NumericValidation.isNumeric(request.getParameter(STATUS))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!DateValidation.isDate(request.getParameter(DATE))) {
            request.setAttribute(MESSAGE, ERROR_WRONG_DATE);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
        long personId = Long.parseLong(request.getParameter(PERSON_ID));
        long status = Long.parseLong(request.getParameter(STATUS));
        String date = request.getParameter(DATE);

        if (MIN_ORDER_STATUS < status && status > MAX_ORDER_STATUS) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        OrderMainDAO orderMainDAO = new OrderMainDAOImpl();

        orderMainDAO.updateOneById(orderMainId, new OrderMain(orderMainId, personId, status, date));
        response.sendRedirect(SHOW_ORDER_ADMIN_LIST_URL);
    }
}