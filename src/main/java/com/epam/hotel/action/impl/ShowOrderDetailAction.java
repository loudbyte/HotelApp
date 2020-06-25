package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.validation.AuthorizationValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ORDER_NOT_FOUND;

public class ShowOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (AuthorizationValidation.authorizationGetBoolean(request, response)) {
            if (NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
                long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

                request.getSession().setAttribute(ORDER_MAIN_ID, orderMainId);
                response.sendRedirect(SHOW_ORDER_ROOM_DETAIL_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        }
    }
}