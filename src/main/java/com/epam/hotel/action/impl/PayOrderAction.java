package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ORDER_MAIN_ID;
import static com.epam.hotel.action.impl.ActionConstant.PAY_ORDER_URL;

public class PayOrderAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ORDER_MAIN_ID, request.getParameter(ORDER_MAIN_ID));
        request.getRequestDispatcher(PAY_ORDER_URL).forward(request, response);
    }
}