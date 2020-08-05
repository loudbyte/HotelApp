package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.dao.OrderMainDAO;
import com.epam.hotel.dao.OrderRoomDetailDAO;
import com.epam.hotel.dao.impl.OrderMainDAOImpl;
import com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.OrderMain;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.AuthorizationValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;
import static com.epam.hotel.util.constant.DAOConstant.ERROR_ID;

public class CreateOrderAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Person person = AuthorizationValidation.authorizationGetPerson(request, response);
        if (person != null) {
            if ((request.getSession().getAttribute(CART) instanceof Cart)) {

                Cart cart = (Cart) request.getSession().getAttribute(CART);

                long personId = person.getId();
                LocalDate localDateNow = LocalDate.now();

                OrderMain orderMain = new OrderMain();

                OrderMainDAO orderMainDAO = new OrderMainDAOImpl();

                orderMain.setPersonId(personId);
                orderMain.setOrderStatusId(NEW);
                orderMain.setDate(String.valueOf(localDateNow));
                orderMain.setId(orderMainDAO.create(orderMain));

                if (orderMain.getId() != ERROR_ID) {
                    OrderRoomDetailDAO orderRoomDetailDAO = new OrderRoomDetailDAOImpl();

                    for (OrderRoomDetail detail : cart.getOrderRoomDetailMap().values()) {
                        detail.setOrderMainId(orderMain.getId());
                        if (orderRoomDetailDAO.create(detail) == ERROR_ID) {
                            request.getSession().setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_ORDER_DETAIL);
                            response.sendRedirect(ERROR_JSP);
                            return;
                        }
                    }
                    cart.clearOrderRoomDetailMap();

                    request.getSession().removeAttribute(CART);
                    request.getSession().setAttribute(CART, cart);

                    response.sendRedirect(SHOW_MY_ORDERS_JSP);
                } else {
                    request.getSession().setAttribute(MESSAGE, ERROR_FAILED_TO_CREATE_ORDER);
                    response.sendRedirect(ERROR_JSP);
                }
            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_CARD_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        }
    }
}