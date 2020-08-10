package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.PersonDAO;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.Person;
import com.epam.hotel.access.Role;
import com.epam.hotel.password.EncodePassword;
import com.epam.hotel.validation.EmailValidator;
import com.epam.hotel.validation.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class LoginAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        EncodePassword encodePassword = new EncodePassword();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        if (!EMPTY_STRING.equals(email) && !EMPTY_STRING.equals(password)) {
            if (EmailValidator.isEmailValid(email) && PasswordValidator.isPasswordValid(password)) {

                password = encodePassword.getHashPassword(password);
                PersonDAO personDAO = new PersonDAOImpl();
                Person person = personDAO.getOneByPasswordAndEmail(email, password);

                if (person != null) {
                    if (!person.isBan()) {

                        Cart cart = new Cart();

                        if (!person.isAdmin()) {
                            session.setAttribute(PERSON, person);
                            session.setAttribute(ROLE, Role.CLIENT);
                            session.setAttribute(CART, cart);
                            response.sendRedirect(CABINET_JSP);
                        } else if (person.isAdmin()) {
                            session.setAttribute(PERSON, person);
                            session.setAttribute(ROLE, Role.ADMIN);
                            session.setAttribute(CART, cart);
                            response.sendRedirect(CABINET_JSP);
                        }
                    } else {
                        request.getSession().setAttribute(MESSAGE, ERROR_YOU_BANNED);
                        response.sendRedirect(ERROR_JSP);
                    }
                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_WRONG_PASSWORD_OR_EMAIL);
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