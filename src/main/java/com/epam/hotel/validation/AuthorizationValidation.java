package com.epam.hotel.validation;

import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.ERROR_NEED_TO_REGISTER;

public class AuthorizationValidation {
    public static Person authorizationGetPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute(PERSON) instanceof Person) {
            return (Person) request.getSession().getAttribute(PERSON);
        } else {
            request.setAttribute(MESSAGE, ERROR_NEED_TO_REGISTER);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return null;
        }
    }

    public static boolean authorizationGetBoolean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(PERSON) instanceof Person) {
            return true;
        } else {
            request.setAttribute(MESSAGE, ERROR_NEED_TO_REGISTER);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return false;
        }
    }
}