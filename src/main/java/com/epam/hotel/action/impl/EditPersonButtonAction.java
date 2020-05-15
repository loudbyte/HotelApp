package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.EDIT_PERSON_URL;

public class EditPersonButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("person_id", request.getParameter("person_id"));
        request.getRequestDispatcher(EDIT_PERSON_URL).forward(request, response);
    }
}
