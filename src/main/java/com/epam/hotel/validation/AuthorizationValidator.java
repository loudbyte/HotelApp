package com.epam.hotel.validation;

import com.epam.hotel.entity.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_NEED_TO_REGISTER;

public class AuthorizationValidator {
    public static Person authorizationGetPerson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Person person = null;
        if (request.getSession().getAttribute(PERSON) instanceof Person) {
            person = (Person) request.getSession().getAttribute(PERSON);
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_NEED_TO_REGISTER);
            response.sendRedirect(ERROR_JSP);
        }
        return person;
    }

    public static boolean authorizationGetBoolean(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        if (request.getSession().getAttribute(PERSON) instanceof Person) {
            result = true;
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_NEED_TO_REGISTER);
            response.sendRedirect(ERROR_JSP);
        }
        return result;
    }
}