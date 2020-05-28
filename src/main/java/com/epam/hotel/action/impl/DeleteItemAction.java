package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        long detailId = Long.parseLong(String.valueOf(request.getParameter("order_detail_id")));

        cart.deleteElementByKeyFromOrderRoomDetailMap(detailId);

        request.getSession().removeAttribute("cart");
        request.getSession().setAttribute("cart", cart);

        request.getRequestDispatcher(ActionConstant.SHOW_CART_URL).forward(request, response);

    }
}