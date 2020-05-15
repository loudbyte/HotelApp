package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Person;
import com.epam.hotel.password.HashPassword;
import com.epam.hotel.role.Role;
import com.epam.hotel.validation.EmailValidation;
import com.epam.hotel.validation.PasswordValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.CABINET_URL;
import static com.epam.hotel.action.impl.Constant.ERROR_URL;

public class LoginAction implements Action {

    private String email;
    private String password;
    private HashPassword hashPassword = new HashPassword();
    private PersonDAOImpl personDAO;
    private Person person;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        email = request.getParameter("email");
        password = request.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "Пустые поля");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!new EmailValidation().isEmailValid(email) || !new PasswordValidation().isPasswordValid(password)) {
            request.setAttribute("message", "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        password = hashPassword.getHashPassword(password);
        personDAO = new PersonDAOImpl();
        person = personDAO.getOneByPasswordAndEmail(email, password);

        if (person != null && !person.isAdmin()) {
            session.setAttribute("person", person);
            session.setAttribute("role", Role.CLIENT);
            request.getRequestDispatcher(CABINET_URL).forward(request, response);
        } else if (person != null && person.isAdmin()) {
            session.setAttribute("person", person);
            session.setAttribute("role", Role.ADMIN);
            request.getRequestDispatcher(CABINET_URL).forward(request, response);
        } else {
            request.setAttribute("message", "Пользователя не существует.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}