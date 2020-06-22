package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.validation.LocaleValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_INVALID_DATA;

public class EditLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!NumericValidation.isNumeric(request.getParameter(LANGUAGE_ID))
                || !LocaleValidation.isValid(request.getParameter(LOCALE))) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long languageId = Long.parseLong(request.getParameter(LANGUAGE_ID));
        String locale = request.getParameter(LOCALE);

        LanguageDAO languageDAO = new LanguageDAOImpl();
        languageDAO.updateLanguageById(languageId, locale);
        response.sendRedirect(LANGUAGE_URL);
    }
}