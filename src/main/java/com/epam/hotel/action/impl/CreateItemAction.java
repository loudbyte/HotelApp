package com.epam.hotel.action.impl;

import com.epam.hotel.action.Action;
import com.epam.hotel.entity.Cart;
import com.epam.hotel.entity.OrderRoomDetail;
import com.epam.hotel.validation.DateValidation;
import com.epam.hotel.validation.NumericValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.hotel.util.constant.ActionConstant.*;
import static com.epam.hotel.util.constant.ErrorConstant.*;

public class CreateItemAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (NumericValidation.isNumeric(request.getParameter(ROOM_ID))
                && NumericValidation.isNumeric(request.getParameter(DETAIL_ID))) {

            if (DateValidation
                    .isStartEndDatesValid(request.getParameter(START_DATE), request.getParameter(END_DATE))) {

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

                response.sendRedirect(SHOW_CART_JSP);

            } else {
                request.getSession().setAttribute(MESSAGE, ERROR_WRONG_DATE);
                response.sendRedirect(ERROR_JSP);
            }
        } else {
            request.getSession().setAttribute(MESSAGE, ERROR_ITEM_NOT_FOUND);
            response.sendRedirect(ERROR_JSP);
        }
    }
}