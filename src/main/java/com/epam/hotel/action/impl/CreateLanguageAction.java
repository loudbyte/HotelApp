package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.LanguageDAO;
import com.epam.hotel.util.constant.DAOConstant;
import com.epam.hotel.dao.impl.LanguageDAOImpl;
import com.epam.hotel.validation.LocaleValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CreateLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (LocaleValidation.isValid(request.getParameter(LOCALE))) {

            String language = request.getParameter(LOCALE);

            LanguageDAO languageDAO = new LanguageDAOImpl();

            if (languageDAO.addLanguage(language) != DAOConstant.ERROR_ID) {
                response.sendRedirect(LANGUAGE_JSP);
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_LANGUAGE);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
            response.sendRedirect(ERROR_JSP);
        }
    }
}