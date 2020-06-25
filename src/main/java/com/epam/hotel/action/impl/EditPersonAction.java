package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.validation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class EditPersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (NumericValidation.isNumeric(request.getParameter(PERSON_ID))) {

            long personId = Long.parseLong(String.valueOf(request.getParameter(PERSON_ID)));

            String firstName = request.getParameter(FIRST_NAME);
            String lastName = request.getParameter(LAST_NAME);
            String birthday = request.getParameter(BIRTHDAY);
            String phone = request.getParameter(PHONE);
            String iin = request.getParameter(IIN);
            String email = request.getParameter(EMAIL);
            String password = request.getParameter(PASSWORD);
            boolean isBan = false;
            boolean isAdmin = false;

            if (ON.equals(request.getParameter(ADMIN)))
                isAdmin = true;

            if (ON.equals(request.getParameter(BAN)))
                isBan = true;

            if (!EMPTY_STRING.equals(firstName) && !EMPTY_STRING.equals(lastName) && !EMPTY_STRING.equals(birthday) && !EMPTY_STRING.equals(phone)
                    && !EMPTY_STRING.equals(email) && !EMPTY_STRING.equals(password) && !EMPTY_STRING.equals(iin)) {

                if (EmailValidation.isEmailValid(email) && PasswordValidation.isPasswordValid(password)
                        && AgeValidation.isAgeValid(birthday) && NameValidation.isNameValid(firstName)
                        && NameValidation.isNameValid(lastName) && PhoneValidation.isPhoneValid(phone)
                        && IINValidation.isIINValid(iin)) {

                    PersonDAO personDAO = new PersonDAOImpl();

                    personDAO.updateOneById(personId, CreatePersonAction
                            .definePersonEntity(firstName, lastName, birthday, phone, iin, email, password, isBan, isAdmin));

                    request.getRequestDispatcher(SHOW_PERSON_ADMIN_LIST_JSP).forward(request, response);

                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_INVALID_DATA);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE,ERROR_EMPTY_FIELDS );
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_USER_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}