package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.validation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;
import static com.epam.hotel.util.constant.DAOConstant.ERROR_ID;

public class RegistrationPersonAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String birthday = request.getParameter(BIRTHDAY);
        String phone = request.getParameter(PHONE);
        String iin = request.getParameter(IIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        boolean isBan = false;
        boolean isAdmin = false;

        if (!EMPTY_STRING.equals(firstName) && !EMPTY_STRING.equals(lastName) && !EMPTY_STRING.equals(birthday) && !EMPTY_STRING.equals(phone)
                && !EMPTY_STRING.equals(email) && !EMPTY_STRING.equals(password) && !EMPTY_STRING.equals(iin)) {

            if (EmailValidator.isEmailValid(email) && PasswordValidator.isPasswordValid(password)
                    && AgeValidator.isAgeValid(birthday) && NameValidator.isNameValid(firstName)
                    && NameValidator.isNameValid(lastName) && PhoneValidator.isPhoneValid(phone)
                    && IINValidator.isIINValid(iin)) {

                PersonDAO personDAO = new PersonDAOImpl();

                if (personDAO.getOneByEmail(email) == null) {

                    if (personDAO.create(CreatePersonAction
                            .definePersonEntity(firstName, lastName, birthday, phone, iin, email, password, isBan, isAdmin)) != ERROR_ID) {

                        response.sendRedirect(LOGIN_JSP);
                    } else {
                        request.getSession().setAttribute(MESSAGE, ERROR_REGISTRATION_FAILED);
                        response.sendRedirect(ERROR_JSP);
                    }
                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_USER_WITH_EMAIL_ALREADY_EXIST);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
            response.sendRedirect(ERROR_JSP);
        }
    }
}