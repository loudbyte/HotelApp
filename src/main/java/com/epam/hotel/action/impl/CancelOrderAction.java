package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.Constant.*;

public class CancelOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = null;

        if (request.getSession().getAttribute("person") instanceof Person) {
            person = (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегистрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderId = Long.parseLong(request.getParameter("order_id"));
        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();
        OrderMain orderMain = orderMainDAO.getOneById(orderId);
        orderMain.setStatusId(CANCELLED);
        orderMainDAO.updateOneById(orderId, orderMain);

        request.setAttribute("my_orders", new OrderMainDAOImpl().getAllByPersonId(person.getId()));
        request.getRequestDispatcher(SHOW_MY_ORDERS_URL).forward(request, response);
    }
}
