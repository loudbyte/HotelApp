package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreatePersonAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long id = -1;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String birthday = request.getParameter(BIRTHDAY);
        String phone = request.getParameter(PHONE);
        String email = request.getParameter(EMAIL);
        String iin = request.getParameter(IIN);
        String password = request.getParameter(PASSWORD);
        boolean isAdmin = false;
        boolean isBan = false;

        if ("on".equals(request.getParameter(ADMIN))) {
            isAdmin = true;
        }

        if ("on".equals(request.getParameter(BAN))) {
            isBan = true;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                || email.isEmpty() || password.isEmpty() || iin.isEmpty()) {
            request.setAttribute(MESSAGE, "Пустые поля");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!EmailValidation.isEmailValid(email) || !PasswordValidation.isPasswordValid(password)
                || !AgeValidation.isAgeValid(birthday) || !NameValidation.isNameValid(firstName)
                || !NameValidation.isNameValid(lastName) || !PhoneValidation.isPhoneValid(phone)
                || !IINValidation.isIINValid(iin)) {
            request.setAttribute(MESSAGE, "Данные введены некорректно.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        PersonDAOImpl personDAO = new PersonDAOImpl();
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

        request.getRequestDispatcher(SHOW_PERSON_ADMIN_LIST_URL).forward(request, response);
    }
}