package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.ERROR_URL;

public class EditPersonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PersonDAOImpl personDAO = new PersonDAOImpl();

        long id = Long.parseLong(String.valueOf(request.getParameter("person_id")));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String iin = request.getParameter("iin");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String admin = request.getParameter("admin");
        String ban = request.getParameter("ban");
        boolean isBan = false;
        boolean isAdmin = false;

        if (firstName.isEmpty() || lastName.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                || iin.isEmpty() || email.isEmpty() || password.isEmpty()) {
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

        personDAO.updateOneById(id, person);

        new ShowPersonAdminListAction().execute(request, response);
    }
}
