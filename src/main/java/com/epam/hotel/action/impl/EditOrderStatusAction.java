package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.OrderStatusDAO;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.dao.impl.OrderStatusDAOImpl;
import com.epam.hotel.entity.OrderStatus;
import com.epam.hotel.validation.ActionFieldValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ORDER_STATUS_NOT_FOUND;

public class EditOrderStatusAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NumericValidation.isNumeric(request.getParameter(ORDER_STATUS_ID))) {

            LanguageDAO languageDAO = new LanguageDAOImpl();
            Map<Integer, String> languageMap = languageDAO.getLanguageMap();

            if (ActionFieldValidation.isOrderStatusFieldValid(languageMap, request, response)) {

                long orderStatusId = Long.parseLong(request.getParameter(ORDER_STATUS_ID));

                Map<Integer, String> orderStatusNameMap = new HashMap<>();

                for (Integer key : languageMap.keySet()) {
                    orderStatusNameMap.put(key, request.getParameter(ORDER_STATUS_NAME + key.toString()));
                }
                OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();
                orderStatusDAO.updateOneById(orderStatusId, new OrderStatus(orderStatusNameMap));

                response.sendRedirect(SHOW_ORDER_STATUS_ADMIN_LIST_JSP);
            }

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_ORDER_STATUS_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}
