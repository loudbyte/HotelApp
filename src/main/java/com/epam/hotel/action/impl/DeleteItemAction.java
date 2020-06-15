package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class DeleteItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!(request.getSession().getAttribute(CART) instanceof Cart)) {
            request.setAttribute(MESSAGE, "Корзина не найдена.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }
        if (!NumericValidation.isNumeric(request.getParameter(ORDER_DETAIL_ID))) {
            request.setAttribute(MESSAGE, "Деталь не найдена.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        Cart cart = (Cart) request.getSession().getAttribute(CART);
        long detailId = Long.parseLong(String.valueOf(request.getParameter(ORDER_DETAIL_ID)));

        cart.deleteElementByKeyFromOrderRoomDetailMap(detailId);

        request.getSession().removeAttribute(CART);
        request.getSession().setAttribute(CART, cart);

        request.getRequestDispatcher(ActionConstant.SHOW_CART_URL).forward(request, response);

    }
}