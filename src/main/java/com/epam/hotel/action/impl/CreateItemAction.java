package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.entity.Person;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.action.impl.ActionConstant.*;

public class CreateItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!(request.getSession().getAttribute(PERSON) instanceof Person)
                || request.getSession().getAttribute(PERSON) == null) {
            request.setAttribute(MESSAGE, "Необходимо зарегистрироваться.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!NumericValidation.isNumeric(request.getParameter(ROOM_ID))
                || !NumericValidation.isNumeric(request.getParameter(DETAIL_ID))) {
            request.setAttribute(MESSAGE, "Что-то пошло не так...");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        if (!DateValidation
                .isStartEndDatesValid(request.getParameter(START_DATE), request.getParameter(END_DATE))) {
            request.setAttribute(MESSAGE, "Неправильно выбраны даты.");
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
            return;
        }

        long roomId = Long.parseLong(request.getParameter(ROOM_ID));
        long detailId = Long.parseLong(request.getParameter(DETAIL_ID));

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);

        OrderRoomDetail orderRoomDetail = new OrderRoomDetail();

        orderRoomDetail.setRoomId(roomId);
        orderRoomDetail.setFacilityPackageId(detailId);

        orderRoomDetail.setStartDate(request.getParameter(START_DATE));
        orderRoomDetail.setEndDate(request.getParameter(END_DATE));

        orderRoomDetail.setId(cart.getOrderRoomDetailCounter());

        cart.addElementByKeyToOrderRoomDetailMap(orderRoomDetail);

        request.getRequestDispatcher(SHOW_CART_URL).forward(request, response);
    }
}