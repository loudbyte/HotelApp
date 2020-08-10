package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.validation.NumericValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_CARD_NOT_FOUND;
import static com.epam.hotel.util.constant.ErrorConstant.ERROR_ORDER_DETAIL_NOT_FOUND;

public class DeleteItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getSession().getAttribute(CART) instanceof Cart) {
            if (NumericValidator.isNumeric(request.getParameter(ORDER_DETAIL_ID))) {

                Cart cart = (Cart) request.getSession().getAttribute(CART);
                long detailId = Long.parseLong(String.valueOf(request.getParameter(ORDER_DETAIL_ID)));

                cart.deleteElementByKeyFromOrderRoomDetailMap(detailId);

                request.getSession().removeAttribute(CART);
                request.getSession().setAttribute(CART, cart);

                response.sendRedirect(SHOW_CART_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_ORDER_DETAIL_NOT_FOUND);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_CARD_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}