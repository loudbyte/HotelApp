package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.OrderStatusDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderStatusDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class EditOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))
                && NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                && NumericValidation.isNumeric(request.getParameter(PERSON_ID))
                && NumericValidation.isNumeric(request.getParameter(STATUS))) {

            if (DateValidation.isDate(request.getParameter(DATE))) {

                long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));
                long personId = Long.parseLong(request.getParameter(PERSON_ID));
                long roomStatusId = Long.parseLong(request.getParameter(STATUS));
                String date = request.getParameter(DATE);

                OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();

                if (orderStatusDAO.getOneById(roomStatusId) != null) {

                    OrderMainDAO orderMainDAO = new OrderMainDAOImpl();

                    orderMainDAO.updateOneById(orderMainId, new OrderMain(orderMainId, personId, roomStatusId, date));
                    response.sendRedirect(SHOW_ORDER_ADMIN_LIST_JSP);

                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_WRONG_DATE);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}