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

public class EditPersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!NumericValidation.isNumeric(request.getParameter(PERSON_ID))) {
            request.setAttribute(MESSAGE, "Пользователя не существует");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        long personId = Long.parseLong(String.valueOf(request.getParameter(PERSON_ID)));

        PersonDAOImpl personDAO = new PersonDAOImpl();

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String birthday = request.getParameter(BIRTHDAY);
        String phone = request.getParameter(PHONE);
        String iin = request.getParameter(IIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String admin = request.getParameter(ADMIN);
        String ban = request.getParameter(BAN);
        boolean isBan = false;
        boolean isAdmin = false;

        if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                || iin.isEmpty() || email.isEmpty() || password.isEmpty()) {
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

        if (ban != null) {
            isBan = true;
        }
        if (admin != null) {
            isAdmin = true;
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

        personDAO.updateOneById(personId, person);

        request.getRequestDispatcher(SHOW_PERSON_ADMIN_LIST_URL).forward(request, response);
    }
}