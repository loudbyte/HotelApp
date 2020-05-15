package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.SHOW_PERSON_ADMIN_LIST_URL;

public class ShowPersonAdminListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAOImpl personDAO = new PersonDAOImpl();
        List<Person> personList = personDAO.getAll();
        request.setAttribute("person_list", personList);
        request.getRequestDispatcher(SHOW_PERSON_ADMIN_LIST_URL).forward(request, response);
    }
}
