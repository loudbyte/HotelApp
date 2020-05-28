package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.EDIT_ORDER_URL;

public class EditOrderButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("order_main_id", request.getParameter("order_main_id"));
        request.setAttribute("person_id", request.getParameter("person_id"));
        request.setAttribute("status_id", request.getParameter("status_id"));
        request.setAttribute("date", request.getParameter("date"));

        request.getRequestDispatcher(EDIT_ORDER_URL).forward(request, response);
    }
}