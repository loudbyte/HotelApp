package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.dao.impl.DAOConstant;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.validation.LocaleValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_FAILED_TO_CREATE_LANGUAGE;

public class CreateLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!LocaleValidation.isValid(request.getParameter(LOCALE))) {
            request.setAttribute(MESSAGE, ErrorConstant.ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        String language = request.getParameter(LOCALE);

        LanguageDAO languageDAO = new LanguageDAOImpl();
        if (languageDAO.addLanguage(language) == DAOConstant.ERROR_ID) {
            request.setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_LANGUAGE);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        response.sendRedirect(LANGUAGE_URL);
    }
}