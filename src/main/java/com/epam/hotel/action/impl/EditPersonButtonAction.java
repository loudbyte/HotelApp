package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.EDIT_PERSON_URL;
import static com.epam.hotel.action.impl.ActionConstant.PERSON_ID;

public class EditPersonButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(PERSON_ID, request.getParameter(PERSON_ID));
        request.getRequestDispatcher(EDIT_PERSON_URL).forward(request, response);
    }
}
