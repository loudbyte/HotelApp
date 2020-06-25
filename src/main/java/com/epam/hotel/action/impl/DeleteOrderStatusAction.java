package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.OrderStatusDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderStatusDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class DeleteOrderStatusAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NumericValidation.isNumeric(request.getParameter(ORDER_STATUS_ID))) {
            OrderMainDAO orderMainDAO = new OrderMainDAOImpl();
            long orderStatusId = Long.parseLong(request.getParameter(ORDER_STATUS_ID));

            if (orderMainDAO.getAll().stream().noneMatch(orderMain -> orderMain.getOrderStatusId() == orderStatusId)) {
                OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();
                orderStatusDAO.deleteOneById(orderStatusId);

                response.sendRedirect(SHOW_ORDER_STATUS_ADMIN_LIST_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ALREADY_HAVE_ORDER_WITH_THIS_STATUS);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}
