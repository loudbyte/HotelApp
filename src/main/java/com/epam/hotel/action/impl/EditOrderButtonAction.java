package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class EditOrderButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(ORDER_MAIN_ID, request.getParameter(ORDER_MAIN_ID));
        request.setAttribute(PERSON_ID, request.getParameter(PERSON_ID));
        request.setAttribute(STATUS_ID, request.getParameter(STATUS_ID));
        request.setAttribute(DATE, request.getParameter(DATE));

        request.getRequestDispatcher(EDIT_ORDER_URL).forward(request, response);
    }
}