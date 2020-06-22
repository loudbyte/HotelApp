package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.AuthorizationValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_ORDER_NOT_FOUND;

public class ShowOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!AuthorizationValidation.authorizationGetBoolean(request, response))
            return;

        if (!NumericValidation.isNumeric(request.getParameter(ORDER_MAIN_ID))) {
            request.setAttribute(MESSAGE, ERROR_ORDER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long orderMainId = Long.parseLong(request.getParameter(ORDER_MAIN_ID));

        request.setAttribute(ORDER_MAIN_ID, orderMainId);
        request.getRequestDispatcher(SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}