package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.AuthorizationValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ActionConstant.SHOW_MY_ORDERS_URL;

public class ShowMyOrdersAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthorizationValidation.authorizationGetBoolean(request, response))
            return;
        response.sendRedirect(SHOW_MY_ORDERS_URL);
    }
}
