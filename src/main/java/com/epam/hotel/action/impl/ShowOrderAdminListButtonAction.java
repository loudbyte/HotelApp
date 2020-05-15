package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.PersonDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.hotel.action.impl.Constant.SHOW_ORDER_ADMIN_LIST_URL;

public class ShowOrderAdminListButtonAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        List<OrderMain> allOrders = orderMainDAO.getAll();

        PersonDAOImpl personDAO = new PersonDAOImpl();
        List<Person> personList = personDAO.getAll();

        request.setAttribute("person_list", personList);
        request.setAttribute("orders", allOrders);
        request.getRequestDispatcher(SHOW_ORDER_ADMIN_LIST_URL).forward(request, response);
    }
}
