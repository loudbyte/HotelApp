package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
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

public class LoginAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        EncodePassword encodePassword = new EncodePassword();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute(MESSAGE, "Пустые поля");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!EmailValidation.isEmailValid(email) || !PasswordValidation.isPasswordValid(password)) {
            request.setAttribute(MESSAGE, "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        password = encodePassword.getHashPassword(password);
        PersonDAOImpl personDAO = new PersonDAOImpl();
        Person person = personDAO.getOneByPasswordAndEmail(email, password);

        if (person.isBan()) {
            request.setAttribute(MESSAGE, "К сожалению Вы забанены.");
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
            request.setAttribute(MESSAGE, "Пользователя не существует.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}