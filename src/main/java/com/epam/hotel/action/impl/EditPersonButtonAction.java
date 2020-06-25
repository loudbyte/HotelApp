package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.EDIT_PERSON_JSP;
import static com.epam.hotel.util.constant.ActionConstant.PERSON_ID;

public class EditPersonButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(PERSON_ID, request.getParameter(PERSON_ID));
        response.sendRedirect(EDIT_PERSON_JSP);
    }
}
