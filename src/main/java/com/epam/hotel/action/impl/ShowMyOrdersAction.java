package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.validation.AuthorizationValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.SHOW_MY_ORDERS_JSP;

public class ShowMyOrdersAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (AuthorizationValidation.authorizationGetBoolean(request, response)) {
            response.sendRedirect(SHOW_MY_ORDERS_JSP);
        }
    }
}
