package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ActionConstant.LOGIN_URL;

public class RegistrationPersonAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAOImpl personDAO = new PersonDAOImpl();

        long id = -1;
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String iin = request.getParameter("iin");
        String password = request.getParameter("password");
        boolean isAdmin = false;
        boolean isBan = false;

        if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                || email.isEmpty() || password.isEmpty() || iin.isEmpty()) {
            request.setAttribute("message", "Пустые поля");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!new EmailValidation().isEmailValid(email) || !new PasswordValidation().isPasswordValid(password)
                || !new AgeValidation().isAgeValid(birthday) || !new NameValidation().isNameValid(firstName)
                || !new NameValidation().isNameValid(lastName) || !new PhoneValidation().isPhoneValid(phone)
                || !new IINValidation().isIINValid(iin)) {
            request.setAttribute("message", "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthday(birthday);
        person.setPhone(phone);
        person.setEmail(email);
        person.setIin(iin);
        person.setPassword(password);
        person.setAdmin(isAdmin);
        person.setBan(isBan);

        id = personDAO.create(person);

        person.setId(id);

        request.getRequestDispatcher(LOGIN_URL).forward(request, response);
    }
}