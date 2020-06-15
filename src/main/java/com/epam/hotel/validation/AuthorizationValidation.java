package com.epam.hotel.validation;

import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;

public class AuthorizationValidation {
    public static Person authorizationGetPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("person") instanceof Person) {
            return (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return null;
        }
    }

    public static boolean authorizationGetBoolean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("person") instanceof Person) {
            return true;
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return false;
        }
    }
}