package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.Person;
import com.epam.hotel.entity.role.Role;
import com.epam.hotel.password.EncodePassword;
import com.epam.hotel.validation.EmailValidation;
import com.epam.hotel.validation.PasswordValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;
import static com.epam.hotel.action.impl.ErrorConstant.*;

public class LoginAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        EncodePassword encodePassword = new EncodePassword();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute(MESSAGE, ERROR_EMPTY_FIELDS);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!EmailValidation.isEmailValid(email) || !PasswordValidation.isPasswordValid(password)) {
            request.setAttribute(MESSAGE, ERROR_INVALID_DATA);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        password = encodePassword.getHashPassword(password);
        PersonDAO personDAO = new PersonDAOImpl();
        Person person = personDAO.getOneByPasswordAndEmail(email, password);

        if (person == null) {
            request.setAttribute(MESSAGE, ERROR_USER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (person.isBan()) {
            request.setAttribute(MESSAGE, ERROR_YOU_BANNED);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        Cart cart = new Cart();

        if (person != null && !person.isAdmin()) {
            session.setAttribute(PERSON, person);
            session.setAttribute(ROLE, Role.CLIENT);
            session.setAttribute(CART, cart);
            response.sendRedirect(CABINET_URL);
        } else if (person != null && person.isAdmin()) {
            session.setAttribute(PERSON, person);
            session.setAttribute(ROLE, Role.ADMIN);
            session.setAttribute(CART, cart);
            response.sendRedirect(CABINET_URL);
        } else {
            request.setAttribute(MESSAGE, ERROR_USER_NOT_FOUND);
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}