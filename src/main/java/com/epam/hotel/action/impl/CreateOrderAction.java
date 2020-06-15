package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.AuthorizationValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreateOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = AuthorizationValidation.authorizationGetPerson(request, response);
        if (person == null)
            return;

        if (!(request.getSession().getAttribute(CART) instanceof Cart)) {
            request.setAttribute(MESSAGE, "Корзина не найдена.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        Cart cart = (Cart) request.getSession().getAttribute(CART);

        long personId = person.getId();
        LocalDate localDateNow = LocalDate.now();

        OrderMain orderMain = new OrderMain();

        OrderMainDAOImpl orderMainDAO = new OrderMainDAOImpl();

        orderMain.setPersonId(personId);
        orderMain.setStatusId(NEW);
        orderMain.setDate(String.valueOf(localDateNow));

        orderMain.setId(orderMainDAO.create(orderMain));

        OrderRoomDetailDAOImpl orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

        for (OrderRoomDetail detail : cart.getOrderRoomDetailMap().values()) {
            detail.setOrderMainId(orderMain.getId());
            orderRoomDetailDAO.create(detail);
        }

        cart.clearOrderRoomDetailMap();

        request.getSession().removeAttribute(CART);
        request.getSession().setAttribute(CART, cart);

        request.getRequestDispatcher(SHOW_MY_ORDERS_URL).forward(request, response);
    }
}