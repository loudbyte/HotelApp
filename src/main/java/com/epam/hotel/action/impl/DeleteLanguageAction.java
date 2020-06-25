package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_INVALID_DATA;

public class DeleteLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (NumericValidation.isNumeric(request.getParameter(LANGUAGE_ID))) {

            long languageId = Long.parseLong(request.getParameter(LANGUAGE_ID));

            LanguageDAO languageDAO = new LanguageDAOImpl();
            languageDAO.deleteLanguageById(languageId);

            response.sendRedirect(LANGUAGE_JSP);

        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}