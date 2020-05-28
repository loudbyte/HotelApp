package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.ERROR_URL;
import static com.epam.hotel.action.impl.ActionConstant.SHOW_ORDER_ROOM_DETAIL_URL;

public class ShowOrderDetailAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = null;

        if (request.getSession().getAttribute("person") instanceof Person) {
            person = (Person) request.getSession().getAttribute("person");
        } else {
            request.setAttribute("message", "Необходимо зарегестрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long orderMainId = Long.parseLong(request.getParameter("order_main_id"));

        request.setAttribute("order_main_id", orderMainId);
        request.getRequestDispatcher(SHOW_ORDER_ROOM_DETAIL_URL).forward(request, response);
    }
}
